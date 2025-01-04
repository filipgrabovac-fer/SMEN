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

    private final SubjectService service;

    // Endpoint to get a subject by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubject(@PathVariable Long id) {
        Optional<Subject> subject = service.getById(id);

        return subject.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to get all subjects
    @GetMapping("/")
    public ResponseEntity<List<Subject>> getSubjects() {
        List<Subject> subjects = service.getAll();
        if (subjects.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(subjects);
    }

    // Endpoint to get a subject by its title
    @GetMapping("/search/title/{title}")
    public ResponseEntity<Subject> getSubjectByTitle(@PathVariable String title) {
        Optional<Subject> subject = service.getSubjectByTitle(title);

        return subject.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to create a subject
    @PostMapping("/new")
    public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) {
        subject.setCreatedAt(LocalDateTime.now());

        Subject createdSubject = service.saveSubject(subject);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubject);
    }

    // Endpoint to update a subject
    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @RequestBody Subject subject) {
        Optional<Subject> existingSubject = service.getById(id);

        if (existingSubject.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        subject.setId(id);
        subject.setUpdatedAt(LocalDateTime.now());
        Subject updatedSubject = service.saveSubject(subject);
        return ResponseEntity.ok(updatedSubject);
    }

    // Endpoint to delete a subject
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        Optional<Subject> subject = service.getById(id);

        if (subject.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }
}
