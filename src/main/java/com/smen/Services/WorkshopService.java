package com.smen.Services;

import com.smen.Dto.Workshop.WorkshopDto;
import com.smen.Models.User;
import com.smen.Models.Workshop;
import com.smen.Models.WorkshopSubject;
import com.smen.Repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkshopService extends BaseEntityService<Workshop, Long> {

    private final IWorkshopRepository workshopRepository;
    private final IWorkshopSubjectRepository workshopSubjectRepository;
    private final IRatingRepository ratingRepository;

    public WorkshopService(IWorkshopRepository workshopRepository, IWorkshopSubjectRepository workshopSubjectRepository, IRatingRepository ratingRepository, IUserRepository userRepository) {
        super(workshopRepository);
        this.workshopRepository = workshopRepository;
        this.ratingRepository = ratingRepository;
        this.workshopSubjectRepository = workshopSubjectRepository;
    }

    public Optional<WorkshopDto> getByIdAsDto(Long id) {
        return workshopRepository.findById(id).map(WorkshopDto::map);
    }

    public List<WorkshopDto> getAllWorkshops() {
        return workshopRepository.findAll()
                .stream()
                .map(WorkshopDto::map)
                .collect(Collectors.toList());
    }

    public List<WorkshopDto> getWorkshopsBySubjectId(Long subjectId) {
        List<WorkshopSubject> workshopSubjects = workshopSubjectRepository.findBySubjectId(subjectId);
        return workshopSubjects.stream()
                .map(WorkshopSubject::getWorkshop)
                .distinct()
                .map(WorkshopDto::map)
                .collect(Collectors.toList());
    }

    public List<WorkshopDto> getAvailableWorkshops() {
        return workshopRepository.findByNoOfAvailableSlotsGreaterThan(0)
                .stream()
                .map(WorkshopDto::map)
                .collect(Collectors.toList());
    }

    public List<WorkshopDto> getWorkshopsByTitle(String title) {
        return workshopRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(WorkshopDto::map)
                .collect(Collectors.toList());
    }

    public List<WorkshopDto> getWorkshopsByUserId(Long userId) {
        return workshopRepository.findByUserId(userId)
                .stream()
                .map(WorkshopDto::map)
                .collect(Collectors.toList());
    }

    public List<WorkshopDto> getWorkshopsByStatusId(Long statusId) {
        return workshopRepository.findByWorkshopStatusId(statusId)
                .stream()
                .map(WorkshopDto::map)
                .collect(Collectors.toList());
    }

    public WorkshopDto saveWorkshopDto(Workshop workshop) {
        Workshop savedWorkshop = workshopRepository.save(workshop);
        return WorkshopDto.map(savedWorkshop);
    }

    public Workshop updateWorkshop(Long id, Workshop updatedWorkshop) {

        Optional<Workshop> oldWorkshop = (Optional<Workshop>) workshopRepository.findById(id);
        if (oldWorkshop.isEmpty())
            return null;
        Workshop existingWorkshop = oldWorkshop.get();

            existingWorkshop.setTitle(updatedWorkshop.getTitle());
            existingWorkshop.setDescription(updatedWorkshop.getDescription());
            existingWorkshop.setDuration(updatedWorkshop.getDuration());
            existingWorkshop.setNoOfAvailableSlots(updatedWorkshop.getNoOfAvailableSlots());
            existingWorkshop.setWorkshopStatus(updatedWorkshop.getWorkshopStatus());
            existingWorkshop.setUser(updatedWorkshop.getUser());

            return workshopRepository.save(existingWorkshop);

    }

    public boolean deleteWorkshop(Long id) {
        if (workshopRepository.existsById(id)) {
            workshopRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
