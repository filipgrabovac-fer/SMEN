package com.smen.Controllers;

import com.smen.Services.ActivityLogService;
import com.smen.DTO.ActivityLog.ActivityLogDto;
import com.smen.DTO.Subject.SubjectDto;
import com.smen.DTO.Subject.SubjectEditDTO;
import com.smen.DTO.Subject.SubjectGetDTO;
import com.smen.Models.Subject;
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
    private ActivityLogService activityLogService;
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
    @PostMapping("/new/user/{userId}")
    public ResponseEntity<SubjectDto> createSubject(@RequestBody SubjectDto subjectDto,@PathVariable Long userId) {
        SubjectDto createdSubject = subjectService.saveSubjectDto(subjectDto);

        ActivityLogDto activityLogDto= new ActivityLogDto("c","subject",userId);
        activityLogService.saveActivityLog(activityLogDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubject);
    }

    // Endpoint to update a subject
    @PutMapping("/{id}/user/{userId}")
    public ResponseEntity<Boolean> updateSubject(
            @PathVariable Long id,
            @RequestBody SubjectEditDTO subjectDto,
            @PathVariable Long userId
    ) {
        Optional<Subject> optionalSubject = subjectService.getById(id);

        if (optionalSubject.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        System.out.println(subjectDto);
        Subject existingSubject = optionalSubject.get();
        existingSubject.setTitle(subjectDto.getTitle());
        existingSubject.setDescription(subjectDto.getDescription());
        subjectService.saveSubject(existingSubject);

        ActivityLogDto activityLogDto= new ActivityLogDto("e","subject",userId);
        activityLogService.saveActivityLog(activityLogDto);

        return ResponseEntity.ok(true);
    }

    // Endpoint to delete a subject
    @DeleteMapping("/{id}/user/{userId}")
    public ResponseEntity<Boolean> deleteSubject(@PathVariable Long id,@PathVariable Long userId) {
        boolean isDeleted = subjectService.deleteById(id);

        if (isDeleted) {
            ActivityLogDto activityLogDto= new ActivityLogDto("d","subject",userId);
            activityLogService.saveActivityLog(activityLogDto);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }
}
