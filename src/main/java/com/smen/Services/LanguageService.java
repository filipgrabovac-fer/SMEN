package com.smen.Services;

import com.smen.Models.Language;
import com.smen.Repositories.ILanguageRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LanguageService extends BaseEntityService<Language, Long> {

    private final ILanguageRepository languageRepository;

    public LanguageService(ILanguageRepository languageRepository) {
        super(languageRepository);
        this.languageRepository = languageRepository;
    }

    public Optional<Language> getLanguageByUserId(Long userId) {
        return languageRepository.findByUserId(userId);
    }
}
