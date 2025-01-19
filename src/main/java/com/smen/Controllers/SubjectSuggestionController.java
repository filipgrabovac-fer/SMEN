package com.smen.Controllers;

import com.smen.Services.ActivityLogService;
import com.smen.DTO.ActivityLog.ActivityLogDto;
import com.smen.DTO.SubjectSuggestion.SubjectSuggestionDto;
import com.smen.Models.User;
import com.smen.Services.SubjectSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subject-suggestions")
@CrossOrigin("*")
public class SubjectSuggestionController {

    private final SubjectSuggestionService service;

    @Autowired
    public SubjectSuggestionController(SubjectSuggestionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SubjectSuggestionDto>> getAllSubjectSuggestions() {
        List<SubjectSuggestionDto> suggestions = service.getAllSubjectSuggestions();
        return ResponseEntity.ok(suggestions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectSuggestionDto> getSubjectSuggestionById(@PathVariable Long id) {
        Optional<SubjectSuggestionDto> suggestion = service.getSubjectSuggestionById(id);

        return suggestion.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SubjectSuggestionDto> createSubjectSuggestion(@RequestBody SubjectSuggestionDto dto) {
        User user = getCurrentUser();
        SubjectSuggestionDto createdSuggestion = service.createSubjectSuggestion(dto, user);

        return ResponseEntity.status(201).body(createdSuggestion);
    }

    private User getCurrentUser() {
        User user = new User();
        user.setId(1L);
        return user;
    }
}