package com.smen.Services;

import com.smen.Dto.WorkshopSubject.WorkshopSubjectDto;
import com.smen.Models.WorkshopSubject;
import com.smen.Repositories.IWorkshopSubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkshopSubjectService extends BaseEntityService<WorkshopSubject, Long> {

    private final IWorkshopSubjectRepository workshopSubjectRepository;

    public WorkshopSubjectService(IWorkshopSubjectRepository workshopSubjectRepository) {
        super(workshopSubjectRepository);
        this.workshopSubjectRepository = workshopSubjectRepository;
    }

    // Get all WorkshopSubjects by Workshop ID
    public List<WorkshopSubjectDto> getByWorkshopId(Long workshopId) {
        return workshopSubjectRepository.findByWorkshopId(workshopId)
                .stream()
                .map(WorkshopSubjectDto::map)
                .collect(Collectors.toList());
    }

    // Get all WorkshopSubjects by Subject ID
    public List<WorkshopSubjectDto> getBySubjectId(Long subjectId) {
        return workshopSubjectRepository.findBySubjectId(subjectId)
                .stream()
                .map(WorkshopSubjectDto::map)
                .collect(Collectors.toList());
    }

    // Create or update WorkshopSubject
    public WorkshopSubjectDto saveWorkshopSubject(WorkshopSubject workshopSubject) {
        WorkshopSubject savedWorkshopSubject = workshopSubjectRepository.save(workshopSubject);
        return WorkshopSubjectDto.map(savedWorkshopSubject);
    }

    // Delete workshop subject by ID
    public boolean deleteById(Long id) {
        if (workshopSubjectRepository.existsById(id)) {
            workshopSubjectRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
