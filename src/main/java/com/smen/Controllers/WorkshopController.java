package com.smen.Controllers;

import com.smen.Models.Workshop;
import com.smen.Services.WorkshopService;
import lombok.AllArgsConstructor;
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

    private WorkshopService service;

    @GetMapping("/{id}")
    public ResponseEntity<Workshop> getWorkshop(@PathVariable Long id) {
        Optional<Workshop> workshop = service.getByid(id);

        return workshop.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public ResponseEntity<List<Workshop>> getWorkshop() {
        List<Workshop> workshops = service.getAll();
        if (workshops.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(workshops);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MENTOR')")
    public ResponseEntity<String> deleteWorkshop(@PathVariable Long id) {
        boolean isDeleted = service.deleteById(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Workshop deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Workshop not found");
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MENTOR')")
    public ResponseEntity<Workshop> updateWorkshop(@PathVariable Long id, @RequestBody Workshop workshop) {
        Workshop updatedWorkshop = service.updateWorkshop(id, workshop);
        if (updatedWorkshop == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(updatedWorkshop);
    }

    @PostMapping("/new")
    @PreAuthorize("!hasRole('PARTICIPANT')")
    public ResponseEntity<Workshop> createWorkshop(@RequestBody Workshop workshop) {
        Workshop newWorkshop = service.create(workshop);
        return ResponseEntity.status(HttpStatus.CREATED).body(newWorkshop);
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MENTOR'")
    public ResponseEntity<List<Workshop>> getWorkshopsByUser(@PathVariable Long id) {
        List<Workshop> workshops = service.getByUser(id);
        if (workshops.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(workshops);
    }

    @GetMapping("/subject/{id}")
    public ResponseEntity<List<Workshop>> getWorkshopsBySubject(@PathVariable Long id) {
        List<Workshop> workshops = service.getBySubject(id);
        if (workshops.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(workshops);
    }

}
