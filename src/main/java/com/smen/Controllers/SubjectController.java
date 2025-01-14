package com.smen.Controllers;

import com.smen.DTO.Subject.SubjectDto;
import com.smen.DTO.Subject.SubjectGetDTO;
import com.smen.Services.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/subject")
@CrossOrigin("*")
public class SubjectController {

    @Autowired
    private final SubjectService subjectService;

    // Endpoint to get a subject by its ID
    @GetMapping("/{id}")
    public ResponseEntity<SubjectGetDTO> getSubject(@PathVariable Long id) {
        Optional<SubjectGetDTO> subjectDto = subjectService.getByIdAsDto(id);

        return subjectDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to get all subjects
    @GetMapping
    public ResponseEntity<List<SubjectGetDTO>> getSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    // Endpoint to get a subject by its title
    @GetMapping("/search")
    public ResponseEntity<SubjectDto> getSubjectByTitle(@RequestParam String title) {
        Optional<SubjectDto> subjectDto = subjectService.getSubjectByTitle(title);

        return subjectDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to create a subject
    @PostMapping("/new")
    public ResponseEntity<SubjectDto> createSubject(@RequestBody SubjectDto subjectDto) {
        SubjectDto createdSubject = subjectService.saveSubjectDto(subjectDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubject);
    }

    // Endpoint to update a subject
    @PutMapping("/{id}")
    public ResponseEntity<SubjectDto> updateSubject(
            @PathVariable Long id,
            @RequestBody SubjectDto subjectDto
    ) {
        Optional<SubjectGetDTO> existingSubject = subjectService.getByIdAsDto(id);

        if (existingSubject.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        SubjectDto updatedSubject = subjectService.saveSubjectDto(subjectDto);
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
