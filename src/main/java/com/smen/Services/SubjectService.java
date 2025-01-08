package com.smen.Services;

import com.smen.Models.Subject;
import com.smen.Repositories.ISubjectRepository;
import com.smen.Repositories.IWorkshopRepository;
import com.smen.Repositories.IRegistrationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService extends BaseEntityService<Subject, Long> {

    private final ISubjectRepository subjectRepository;

    public SubjectService(ISubjectRepository subjectRepository, IWorkshopRepository workshopRepository, IRegistrationRepository registrationRepository) {
        super(subjectRepository);
        this.subjectRepository = subjectRepository;
    }

    // Get subject by title
    public Optional<Subject> getSubjectByTitle(String title) {
        return Optional.ofNullable(subjectRepository.findByTitle(title));
    }

    // Create or update a subject
    public Subject saveSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

}
