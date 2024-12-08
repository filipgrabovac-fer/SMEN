package com.smen.Services;

import com.smen.Models.Subject;
import com.smen.Repositories.IRegistrationRepository;
import com.smen.Repositories.ISubjectRepository;
import com.smen.Repositories.IWorkshopRepository;
import org.springframework.stereotype.Service;

@Service
public class SubjectService extends BaseEntityService<Subject, Long> {

    private final ISubjectRepository subjectRepository;
    private final IWorkshopRepository workshopRepository;
    private final IRegistrationRepository registrationRepository;

    public SubjectService(ISubjectRepository subjectRepository, IWorkshopRepository workshopRepository, IRegistrationRepository registrationRepository) {
        super(subjectRepository);
        this.subjectRepository = subjectRepository;
        this.workshopRepository = workshopRepository;
        this.registrationRepository = registrationRepository;
    }


}
