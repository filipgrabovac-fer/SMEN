package com.smen.Controllers;

import com.smen.Models.WorkshopSubject;
import com.smen.Services.WorkshopSubjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/workshop-subject")
public class WorkshopSubjectController {

    private final WorkshopSubjectService service;

    @GetMapping("/workshop/{workshopId}")
    public ResponseEntity<List<WorkshopSubject>> getWorkshopSubjectsByWorkshop(@PathVariable Long workshopId) {
        List<WorkshopSubject> workshopSubjects = service.getByWorkshopId(workshopId);

        if (workshopSubjects.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(workshopSubjects);
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<WorkshopSubject>> getWorkshopSubjectsBySubject(@PathVariable Long subjectId) {
        List<WorkshopSubject> workshopSubjects = service.getBySubjectId(subjectId);

        if (workshopSubjects.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(workshopSubjects);
    }

    @PostMapping("/")
    public ResponseEntity<WorkshopSubject> createWorkshopSubject(@RequestBody WorkshopSubject workshopSubject) {
        WorkshopSubject createdWorkshopSubject = service.saveWorkshopSubject(workshopSubject);
        return ResponseEntity.status(201).body(createdWorkshopSubject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkshopSubject(@PathVariable Long id) {
        service.deleteWorkshopSubject(id);
        return ResponseEntity.noContent().build();
    }
}
