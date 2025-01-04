package com.smen.Services;

import com.smen.Models.Language;
import com.smen.Repositories.ILanguageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LanguageService extends BaseEntityService<Language, Long> {

    private final ILanguageRepository languageRepository;

    public LanguageService(ILanguageRepository languageRepository) {
        super(languageRepository);
        this.languageRepository = languageRepository;
    }

    // Fetch language by user ID
    public Optional<Language> getLanguageByUserId(Long userId) {
        return languageRepository.findByUserId(userId);
    }

    // Create a new language
    public Language createLanguage(Language language) {
        return languageRepository.save(language);
    }

    // Update language by ID
    public Optional<Language> updateLanguage(Long languageId, Language languageDetails) {
        Optional<Language> existingLanguage = languageRepository.findById(languageId);
        if (existingLanguage.isPresent()) {
            Language language = existingLanguage.get();
            language.setLanguage(languageDetails.getLanguage());
            language.setUpdatedAt(LocalDateTime.now());
            return Optional.of(languageRepository.save(language));
        }
        return Optional.empty();
    }

    // Delete language by ID
    public boolean deleteLanguage(Long languageId) {
        if (languageRepository.existsById(languageId)) {
            languageRepository.deleteById(languageId);
            return true;
        }
        return false;
    }
}
