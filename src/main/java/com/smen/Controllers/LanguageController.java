package com.smen.Controllers;

import com.smen.Models.Language;
import com.smen.Services.LanguageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/language")
public class LanguageController {

    private LanguageService service;

    @GetMapping("/{id}")
    public ResponseEntity<Language> getLanguage(@PathVariable Long id) {
        Optional<Language> language = service.getByid(id);

        return language.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public ResponseEntity<List<Language>> getLanguage() {
        List<Language> languages = service.getAll();
        if (languages.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(languages);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.user.id")
    public ResponseEntity<String> deleteLanguage(@PathVariable Long id) {
        boolean isDeleted = service.deleteById(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Language deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Language not found");
        }
    }


    @PostMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Language> createLanguage(@RequestBody Language language) {
        Language newLanguage = service.create(language);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLanguage);
    }

    //jezik za nekog korisnika
    @GetMapping("/user/{userId}")
    public ResponseEntity<Language> getLanguageByUserId(@PathVariable Long userId) {
        Optional<Language> language = service.getLanguageByUserId(userId);
        return language.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
