package com.smen.Controllers;

import com.smen.Models.Workshop;
import com.smen.Services.WorkshopService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/workshop")
public class WorkshopController {

    private final WorkshopService workshopService;

    @GetMapping
    public ResponseEntity<List<Workshop>> getAllWorkshops() {
        return ResponseEntity.ok(workshopService.getAllWorkshops());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workshop> getWorkshopById(@PathVariable Long id) {
        return workshopService.getWorkshopById(id)
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

    @PostMapping
    public ResponseEntity<Workshop> createWorkshop(@RequestBody Workshop workshop) {
        return ResponseEntity.ok(workshopService.saveWorkshop(workshop));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Workshop> updateWorkshop(@PathVariable Long id, @RequestBody Workshop workshop) {
        return ResponseEntity.ok(workshopService.updateWorkshop(id, workshop));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkshop(@PathVariable Long id) {
        workshopService.deleteWorkshop(id);
        return ResponseEntity.noContent().build();
    }
}
