package com.smen.Controllers;

import com.smen.DTO.Workshop.WorkshopCreateDTO;
import com.smen.DTO.Workshop.WorkshopDto2;
import com.smen.DTO.Workshop.WorkshopDetailsDTO;
import com.smen.DTO.Workshop.WorkshopDto;
import com.smen.Models.User;
import com.smen.Models.Workshop;
import com.smen.Models.WorkshopStatus;
import com.smen.Models.WorkshopSubject;
import com.smen.Services.WorkshopService;
import com.smen.Services.WorkshopSubjectService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/workshop")
@CrossOrigin("*")
public class WorkshopController {

    @Autowired
    private final WorkshopService workshopService;
    @Autowired
    private WorkshopSubjectService workshopSubjectService;

    @GetMapping
    public ResponseEntity<List<WorkshopDetailsDTO>> getAllWorkshops() {
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
    public ResponseEntity<List<WorkshopDto2>> getWorkshopsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(workshopService.getWorkshopsByUserId2(userId));
    }

    @GetMapping("/status/{statusId}")
    public ResponseEntity<List<WorkshopDto>> getWorkshopsByStatus(@PathVariable Long statusId) {
        return ResponseEntity.ok(workshopService.getWorkshopsByStatusId(statusId));
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<WorkshopDetailsDTO>> getWorkshopsBySubject(@PathVariable Long subjectId) {
        return ResponseEntity.ok(workshopService.getWorkshopsBySubjectId(subjectId));
    }

    @GetMapping("/available-slots")
    public ResponseEntity<List<WorkshopDto>> getAvailableWorkshops() {
        return ResponseEntity.ok(workshopService.getAvailableWorkshops());
    }

    @PostMapping
    public ResponseEntity<Boolean> createWorkshop(
            @RequestBody WorkshopCreateDTO workshopDto ){
        Workshop workshop = workshopDto.toEntity();
        WorkshopDto createdWorkshopDto = workshopService.saveWorkshopDto(workshop);
        WorkshopSubject workshopSubject = new WorkshopSubject();

        workshopSubject.setWorkshopId(createdWorkshopDto.getId());
        workshopSubject.setSubjectId(workshopDto.getSubjectId());
        workshopSubjectService.saveWorkshopSubject(workshopSubject);

        return ResponseEntity.ok(true);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkshopDto> updateWorkshop(
            @PathVariable Long id,
            @RequestBody WorkshopDto workshopDto,
            @RequestParam Optional<WorkshopStatus> workshopStatus) {
        Optional<WorkshopDto> existingWorkshop = workshopService.getByIdAsDto(id);

        if (existingWorkshop.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Workshop workshop = workshopDto.toEntity();
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
