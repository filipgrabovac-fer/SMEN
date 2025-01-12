package com.smen.Controllers;

import com.smen.Dto.WorkshopSubject.WorkshopSubjectDto;
import com.smen.Models.Subject;
import com.smen.Models.Workshop;
import com.smen.Models.WorkshopSubject;
import com.smen.Services.WorkshopSubjectService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/workshop-subject")
public class WorkshopSubjectController {

    @Autowired
    private final WorkshopSubjectService workshopSubjectService;

    @GetMapping("/workshop/{workshopId}")
    public ResponseEntity<List<WorkshopSubjectDto>> getWorkshopSubjectsByWorkshop(@PathVariable Long workshopId) {
        List<WorkshopSubjectDto> workshopSubjects = workshopSubjectService.getByWorkshopId(workshopId);
        return ResponseEntity.ok(workshopSubjects);
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<WorkshopSubjectDto>> getWorkshopSubjectsBySubject(@PathVariable Long subjectId) {
        List<WorkshopSubjectDto> workshopSubjects = workshopSubjectService.getBySubjectId(subjectId);
        return ResponseEntity.ok(workshopSubjects);
    }

    @PostMapping
    public ResponseEntity<WorkshopSubjectDto> createWorkshopSubject(
            @RequestBody WorkshopSubjectDto workshopSubjectDto,
            @RequestParam Optional<Workshop> workshop,
            @RequestParam Optional<Subject> subject) {

        WorkshopSubject workshopSubject = workshopSubjectDto.toEntity(workshop.orElse(null), subject.orElse(null));
        WorkshopSubjectDto createdWorkshopSubject = workshopSubjectService.saveWorkshopSubject(workshopSubject);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWorkshopSubject);
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
