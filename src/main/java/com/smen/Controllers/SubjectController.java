package com.smen.Controllers;

import com.smen.Models.Subject;
import com.smen.Services.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/subject")
public class SubjectController {
    
    private SubjectService service;

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubject(@PathVariable Long id) {
        Optional<Subject> subject = service.getByid(id);

        return subject.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public ResponseEntity<List<Subject>> getSubjects() {
        List<Subject> subjects = service.getAll();
        if (subjects.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(subjects);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteSubject(@PathVariable Long id) {
        boolean isDeleted = service.deleteById(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Subject deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subject not found");
        }
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('PARTICIPANT')")
    public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) {
        HttpHeaders headers = new HttpHeaders(); //dodavanje poruke bez promjene oblika
        headers.add("Error-Message", "Subject must be between 1 and 5");

        Subject newSubject = service.create(subject);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSubject);
    }

}
