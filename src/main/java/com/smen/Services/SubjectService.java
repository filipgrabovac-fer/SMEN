package com.smen.Services;

import com.smen.DTO.Subject.SubjectDto;
import com.smen.DTO.Subject.SubjectGetDTO;
import com.smen.Models.Subject;
import com.smen.Models.User;
import com.smen.Repositories.ISubjectRepository;
import com.smen.Repositories.IUserRepository;
import com.smen.Repositories.IWorkshopRepository;
import com.smen.Repositories.IRegistrationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectService extends BaseEntityService<Subject, Long> {

    private final ISubjectRepository subjectRepository;
    private final IUserRepository userRepository;

    public SubjectService(ISubjectRepository subjectRepository, IWorkshopRepository workshopRepository, IRegistrationRepository registrationRepository, IUserRepository userRepository) {
        super(subjectRepository);
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
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
    public Optional<SubjectGetDTO> getByIdAsDto(Long id) {
        return subjectRepository.findById(id).map(SubjectGetDTO::map);
    }

    // Get all subjects as DTOs
    public List<SubjectGetDTO> getAllSubjects() {

        return subjectRepository.findAll()
                .stream()
                .map(sub -> {
                    SubjectGetDTO subjectGetDTO = new SubjectGetDTO();
                    subjectGetDTO.setId(sub.getId());
                    subjectGetDTO.setTitle(sub.getTitle());
                    subjectGetDTO.setDescription(sub.getDescription());
                    subjectGetDTO.setTags(sub.getTags());
                    if (sub.getAuthorId() == null || userRepository.findById(sub.getAuthorId()).isEmpty()) return null;

                    User user = userRepository.findById(sub.getAuthorId()).get();

                    subjectGetDTO.setAuthor(user.getFirstName() + " " + user.getLastName());
                    subjectGetDTO.setCreatedAt(sub.getCreatedAt().toString());
                    System.out.println(subjectGetDTO);
                    return subjectGetDTO;
                }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    // Create or update a subject and return as DTO
    public SubjectDto saveSubjectDto(SubjectDto subjectDto) {
        Subject subject = subjectDto.toEntity();
        Subject savedSubject = subjectRepository.save(subject);
        return SubjectDto.map(savedSubject);
    }
}
