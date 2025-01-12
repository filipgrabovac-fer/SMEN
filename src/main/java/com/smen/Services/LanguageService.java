package com.smen.Services;

import com.smen.Dto.Language.LanguageDto;
import com.smen.Models.Language;
import com.smen.Repositories.ILanguageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LanguageService extends BaseEntityService<Language, Long> {

    private final ILanguageRepository languageRepository;

    public LanguageService(ILanguageRepository languageRepository) {
        super(languageRepository);
        this.languageRepository = languageRepository;
    }

    public Optional<LanguageDto> getLanguageById(Long id) {
        return languageRepository.findById(id).map(LanguageDto::map);
    }

    public List<LanguageDto> getAllLanguages() {
        return languageRepository.findAll()
                .stream()
                .map(LanguageDto::map)
                .collect(Collectors.toList());
    }

    public LanguageDto createLanguage(LanguageDto languageDto) {
        Language language = languageDto.toEntity();
        language.setCreatedAt(LocalDateTime.now());
        return LanguageDto.map(languageRepository.save(language));
    }

    public Optional<LanguageDto> updateLanguage(Long languageId, LanguageDto languageDetails) {
        Optional<Language> existingLanguage = languageRepository.findById(languageId);

        if (existingLanguage.isPresent()) {
            Language language = existingLanguage.get();
            language.setName(languageDetails.getName());
            language.setUpdatedAt(LocalDateTime.now());
            return Optional.of(LanguageDto.map(languageRepository.save(language)));
        }

        return Optional.empty();
    }

    public boolean deleteLanguage(Long languageId) {
        return deleteById(languageId);
    }
}
