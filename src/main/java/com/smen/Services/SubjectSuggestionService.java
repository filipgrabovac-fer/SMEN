package com.smen.Services;

import com.smen.Dto.SubjectSuggestion.SubjectSuggestionDto;
import com.smen.Models.SubjectSuggestion;
import com.smen.Models.User;
import com.smen.Repositories.ISubjectSuggestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectSuggestionService {

    private final ISubjectSuggestionRepository repository;

    public SubjectSuggestionService(ISubjectSuggestionRepository repository) {
        this.repository = repository;
    }

    public SubjectSuggestionDto createSubjectSuggestion(SubjectSuggestionDto dto, User user) {
        SubjectSuggestion entity = dto.toEntity(user);
        SubjectSuggestion savedEntity = repository.save(entity);
        return SubjectSuggestionDto.fromEntity(savedEntity);
    }

    public List<SubjectSuggestionDto> getAllSubjectSuggestions() {
        List<SubjectSuggestion> suggestions = repository.findAll(); // Fetch all records
        return suggestions.stream()
                .map(SubjectSuggestionDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<SubjectSuggestionDto> getSubjectSuggestionById(Long id) {
        return repository.findById(id)
                .map(SubjectSuggestionDto::fromEntity);
    }
}