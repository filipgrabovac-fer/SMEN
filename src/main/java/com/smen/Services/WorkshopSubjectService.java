package com.smen.Services;

import com.smen.Models.WorkshopSubject;
import com.smen.Repositories.IWorkshopSubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkshopSubjectService extends BaseEntityService<WorkshopSubject, Long> {

    private final IWorkshopSubjectRepository workshopSubjectRepository;

    public WorkshopSubjectService(IWorkshopSubjectRepository workshopSubjectRepository) {
        super(workshopSubjectRepository);
        this.workshopSubjectRepository = workshopSubjectRepository;
    }

    // Get all WorkshopSubjects by Workshop ID
    public List<WorkshopSubject> getByWorkshopId(Long workshopId) {
        return workshopSubjectRepository.findByWorkshopId(workshopId);
    }

    // Get all WorkshopSubjects by Subject ID
    public List<WorkshopSubject> getBySubjectId(Long subjectId) {
        return workshopSubjectRepository.findBySubjectId(subjectId);
    }

    // Create or update WorkshopSubject
    public WorkshopSubject saveWorkshopSubject(WorkshopSubject workshopSubject) {
        return workshopSubjectRepository.save(workshopSubject);
    }

}
