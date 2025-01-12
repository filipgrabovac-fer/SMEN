package com.smen.Controllers;

import com.smen.Dto.Language.LanguageDto;
import com.smen.Models.Language;
import com.smen.Services.LanguageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private LanguageService languageService;

    // Endpoint to get a language by its id
    @GetMapping("/{id}")
    public ResponseEntity<LanguageDto> getLanguage(@PathVariable Long id) {
        Optional<LanguageDto> languageDto = languageService.getLanguageById(id);
        return languageDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to get all languages
    @GetMapping
    public ResponseEntity<List<LanguageDto>> getAllLanguages() {
        return ResponseEntity.ok(languageService.getAllLanguages());
    }

    // Endpoint to create a new language
    @PostMapping("/new")
    public ResponseEntity<LanguageDto> createLanguage(@RequestBody LanguageDto languageDto) {
        LanguageDto createdLanguage = languageService.createLanguage(languageDto);
        return new ResponseEntity<>(createdLanguage, HttpStatus.CREATED);
    }

    // Endpoint to update an existing language
    @PutMapping("/{languageId}")
    public ResponseEntity<LanguageDto> updateLanguage(@PathVariable Long languageId, @RequestBody LanguageDto languageDetails) {
        Optional<LanguageDto> updatedLanguage = languageService.updateLanguage(languageId, languageDetails);

        return updatedLanguage.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to delete a language by ID
    @DeleteMapping("/{languageId}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable Long languageId) {
        if (languageService.deleteLanguage(languageId)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
