package com.smen.Controllers;

import com.smen.Dto.Workshop.WorkshopDto;
import com.smen.Models.User;
import com.smen.Models.Workshop;
import com.smen.Models.WorkshopStatus;
import com.smen.Services.WorkshopService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/workshop")
public class WorkshopController {

    @Autowired
    private final WorkshopService workshopService;

    @GetMapping
    public ResponseEntity<List<WorkshopDto>> getAllWorkshops() {
        return ResponseEntity.ok(workshopService.getAllWorkshops());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkshopDto> getWorkshopById(@PathVariable Long id) {
        Optional<WorkshopDto> workshopDto = workshopService.getByIdAsDto(id);

        return workshopDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<WorkshopDto>> searchWorkshopsByTitle(@RequestParam String title) {
        return ResponseEntity.ok(workshopService.getWorkshopsByTitle(title));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkshopDto>> getWorkshopsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(workshopService.getWorkshopsByUserId(userId));
    }

    @GetMapping("/status/{statusId}")
    public ResponseEntity<List<WorkshopDto>> getWorkshopsByStatus(@PathVariable Long statusId) {
        return ResponseEntity.ok(workshopService.getWorkshopsByStatusId(statusId));
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<WorkshopDto>> getWorkshopsBySubject(@PathVariable Long subjectId) {
        return ResponseEntity.ok(workshopService.getWorkshopsBySubjectId(subjectId));
    }

    @GetMapping("/available-slots")
    public ResponseEntity<List<WorkshopDto>> getAvailableWorkshops() {
        return ResponseEntity.ok(workshopService.getAvailableWorkshops());
    }

    @PostMapping
    public ResponseEntity<WorkshopDto> createWorkshop(
            @RequestBody WorkshopDto workshopDto,
            @RequestParam Optional<User> owner,
            @RequestParam Optional<WorkshopStatus> workshopStatus) {
        Workshop workshop = workshopDto.toEntity(owner.orElseGet(() -> null),
                workshopStatus.orElseGet(() -> null));
        WorkshopDto createdWorkshopDto = workshopService.saveWorkshopDto(workshop);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWorkshopDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkshopDto> updateWorkshop(
            @PathVariable Long id,
            @RequestBody WorkshopDto workshopDto,
            @RequestParam Optional<User> owner,
            @RequestParam Optional<WorkshopStatus> workshopStatus) {
        Optional<WorkshopDto> existingWorkshop = workshopService.getByIdAsDto(id);

        if (existingWorkshop.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Workshop workshop = workshopDto.toEntity(owner.orElseGet(() -> null),
                workshopStatus.orElseGet(() -> null));
        workshop.setId(id);
        WorkshopDto updatedWorkshopDto = workshopService.saveWorkshopDto(workshop);
        return ResponseEntity.ok(updatedWorkshopDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkshop(@PathVariable Long id) {
        boolean isDeleted = workshopService.deleteWorkshop(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
