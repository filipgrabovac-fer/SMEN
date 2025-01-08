package com.smen.Controllers;

import com.smen.Models.WorkshopSubject;
import com.smen.Services.WorkshopSubjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/workshop-subject")
public class WorkshopSubjectController {

    private final WorkshopSubjectService workshopSubjectService;

    @GetMapping("/workshop/{workshopId}")
    public ResponseEntity<List<WorkshopSubject>> getWorkshopSubjectsByWorkshop(@PathVariable Long workshopId) {
        List<WorkshopSubject> workshopSubjects = workshopSubjectService.getByWorkshopId(workshopId);

        return ResponseEntity.ok(workshopSubjects);
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<WorkshopSubject>> getWorkshopSubjectsBySubject(@PathVariable Long subjectId) {
        List<WorkshopSubject> workshopSubjects = workshopSubjectService.getBySubjectId(subjectId);

        return ResponseEntity.ok(workshopSubjects);
    }

    @PostMapping
    public ResponseEntity<WorkshopSubject> createWorkshopSubject(@RequestBody WorkshopSubject workshopSubject) {
        WorkshopSubject createdWorkshopSubject = workshopSubjectService.saveWorkshopSubject(workshopSubject);
        return ResponseEntity.status(201).body(createdWorkshopSubject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkshopSubject(@PathVariable Long id) {
        boolean isDeleted = workshopSubjectService.deleteById(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
