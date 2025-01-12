package com.smen.Services;

import com.smen.Dto.Subject.SubjectDto;
import com.smen.Models.Subject;
import com.smen.Repositories.ISubjectRepository;
import com.smen.Repositories.IWorkshopRepository;
import com.smen.Repositories.IRegistrationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectService extends BaseEntityService<Subject, Long> {

    private final ISubjectRepository subjectRepository;

    public SubjectService(ISubjectRepository subjectRepository, IWorkshopRepository workshopRepository, IRegistrationRepository registrationRepository) {
        super(subjectRepository);
        this.subjectRepository = subjectRepository;
    }

    // Get subject by title as DTO
    public Optional<SubjectDto> getSubjectByTitle(String title) {
        return Optional.ofNullable(subjectRepository.findByTitle(title)).map(SubjectDto::map);
    }

    // Create or update a subject
    public Subject saveSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    // Get subject by ID as DTO
    public Optional<SubjectDto> getByIdAsDto(Long id) {
        return subjectRepository.findById(id).map(SubjectDto::map);
    }

    // Get all subjects as DTOs
    public List<SubjectDto> getAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(SubjectDto::map)
                .collect(Collectors.toList());
    }

    // Create or update a subject and return as DTO
    public SubjectDto saveSubjectDto(SubjectDto subjectDto) {
        Subject subject = subjectDto.toEntity();
        Subject savedSubject = subjectRepository.save(subject);
        return SubjectDto.map(savedSubject);
    }
}
