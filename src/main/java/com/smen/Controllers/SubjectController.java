package com.smen.Controllers;

import com.smen.Models.Subject;
import com.smen.Services.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/subject")
public class SubjectController {

    private final SubjectService subjectService;

    // Endpoint to get a subject by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubject(@PathVariable Long id) {
        Optional<Subject> subject = subjectService.getById(id);

        return subject.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to get all subjects
    @GetMapping
    public ResponseEntity<List<Subject>> getSubjects() {
        return ResponseEntity.ok(subjectService.getAll());
    }

    // Endpoint to get a subject by its title
    @GetMapping("/search")
    public ResponseEntity<Subject> getSubjectByTitle(@PathVariable String title) {
        Optional<Subject> subject = subjectService.getSubjectByTitle(title);

        return subject.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to create a subject
    @PostMapping("/new")
    public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) {
        subject.setCreatedAt(LocalDateTime.now());

        Subject createdSubject = subjectService.saveSubject(subject);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubject);
    }

    // Endpoint to update a subject
    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @RequestBody Subject subject) {
        Optional<Subject> existingSubject = subjectService.getById(id);

        if (existingSubject.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        subject.setId(id);
        subject.setUpdatedAt(LocalDateTime.now());
        Subject updatedSubject = subjectService.saveSubject(subject);
        return ResponseEntity.ok(updatedSubject);
    }

    // Endpoint to delete a subject
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable Long id) {
        boolean isDeleted = subjectService.deleteById(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Subject deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subject not found");
        }
    }
}
