package com.smen.Controllers;

import com.smen.Models.Workshop;
import com.smen.Services.WorkshopService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/workshop")
public class WorkshopController {

    private final WorkshopService workshopService;

    @GetMapping
    public ResponseEntity<List<Workshop>> getAllWorkshops() {
        return ResponseEntity.ok(workshopService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workshop> getWorkshopById(@PathVariable Long id) {
        return workshopService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Workshop>> searchWorkshopsByTitle(@RequestParam String title) {
        return ResponseEntity.ok(workshopService.getWorkshopsByTitle(title));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Workshop>> getWorkshopsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(workshopService.getWorkshopsByUserId(userId));
    }

    @GetMapping("/status/{statusId}")
    public ResponseEntity<List<Workshop>> getWorkshopsByStatus(@PathVariable Long statusId) {
        return ResponseEntity.ok(workshopService.getWorkshopsByStatusId(statusId));
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<Workshop>> getWorkshopsBySubject(@PathVariable Long subjectId) {
        return ResponseEntity.ok(workshopService.getWorkshopsBySubjectId(subjectId));
    }

    @GetMapping("/available-slots")
    public ResponseEntity<List<Workshop>> getAvailableWorkshops() {
        return ResponseEntity.ok(workshopService.getAvailableWorkshops());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Workshop> updateWorkshop(@PathVariable Long id, @RequestBody Workshop workshop) {
        Workshop updatedWorkshop = workshopService.updateWorkshop(id, workshop);
        if (updatedWorkshop == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(updatedWorkshop);
    }

    @PostMapping
    public ResponseEntity<Workshop> createWorkshop(@RequestBody Workshop workshop) {
        Workshop newWorkshop = workshopService.create(workshop);
        return ResponseEntity.status(HttpStatus.CREATED).body(newWorkshop);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkshop(@PathVariable Long id) {
        boolean isDeleted = workshopService.deleteById(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
