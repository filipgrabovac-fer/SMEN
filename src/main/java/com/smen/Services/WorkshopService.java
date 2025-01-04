package com.smen.Services;

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

    public List<Workshop> getAllWorkshops() {
        return workshopRepository.findAll();
    }

    public Optional<Workshop> getWorkshopById(Long id) {
        return workshopRepository.findById(id);
    }

    public List<Workshop> getWorkshopsByTitle(String title) {
        return workshopRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Workshop> getWorkshopsByUserId(Long userId) {
        return workshopRepository.findByUserId(userId);
    }

    public List<Workshop> getWorkshopsByStatusId(Long statusId) {
        return workshopRepository.findByWorkshopStatusId(statusId);
    }

    public Workshop saveWorkshop(Workshop workshop) {
        return workshopRepository.save(workshop);
    }

    public void deleteWorkshop(Long id) {
        workshopRepository.deleteById(id);
    }

    public Workshop updateWorkshop(Long id, Workshop updatedWorkshop) {
        return workshopRepository.findById(id)
                .map(existingWorkshop -> {
                    existingWorkshop.setTitle(updatedWorkshop.getTitle());
                    existingWorkshop.setDescription(updatedWorkshop.getDescription());
                    existingWorkshop.setDuration(updatedWorkshop.getDuration());
                    existingWorkshop.setNoOfAvailableSlots(updatedWorkshop.getNoOfAvailableSlots());
                    existingWorkshop.setWorkshopStatus(updatedWorkshop.getWorkshopStatus());
                    existingWorkshop.setUser(updatedWorkshop.getUser());
                    return workshopRepository.save(existingWorkshop);
                }).orElseThrow(() -> new RuntimeException("Workshop not found with id " + id));
    }

    public List<Workshop> getWorkshopsBySubjectId(Long subjectId) {
        List<WorkshopSubject> workshopSubjects = workshopSubjectRepository.findBySubjectId(subjectId);
        return workshopSubjects.stream()
                .map(WorkshopSubject::getWorkshop)
                .distinct()
                .collect(Collectors.toList());
    }
}
