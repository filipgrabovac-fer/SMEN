package com.smen.Controllers;

import com.smen.Models.Language;
import com.smen.Services.LanguageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/language")
public class LanguageController {

    private LanguageService service;

    // Endpoint to get a language by its id
    @GetMapping("/{id}")
    public ResponseEntity<Language> getLanguages(@PathVariable Long id) {
        Optional<Language> language = service.getById(id);

        return language.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to get all languages
    @GetMapping("/")
    public ResponseEntity<List<Language>> getLanguage() {
        List<Language> languages = service.getAll();
        if (languages.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(languages);
    }

    // Endpoint to get a language by user id
    @GetMapping("/user/{userId}")
    public ResponseEntity<Language> getLanguageByUserId(@PathVariable Long userId) {
        Optional<Language> language = service.getLanguageByUserId(userId);
        return language.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to create a new language
    @PostMapping("/new")
    public ResponseEntity<Language> createLanguage(@RequestBody Language language) {
        language.setCreatedAt(LocalDateTime.now());

        Language createdLanguage = service.createLanguage(language);
        return new ResponseEntity<>(createdLanguage, HttpStatus.CREATED);
    }

    // Endpoint to update an existing language
    @PutMapping("/{languageId}")
    public ResponseEntity<Language> updateLanguage(@PathVariable Long languageId, @RequestBody Language languageDetails) {
        Optional<Language> updatedLanguage = service.updateLanguage(languageId, languageDetails);
        return updatedLanguage.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to delete a language by ID
    @DeleteMapping("/{languageId}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable Long languageId) {
        if (service.deleteLanguage(languageId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
