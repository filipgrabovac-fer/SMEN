package com.smen.Services;

import com.smen.DTO.Workshop.WorkshopDetailsDTO;
import com.smen.DTO.Workshop.WorkshopDto;
import com.smen.DTO.Workshop.WorkshopDto2;
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
    private final WorkshopStatusService workshopStatusService;
    private final IWorkshopStatusRepository workshopStatusRepository;

    public WorkshopService(IWorkshopStatusRepository workshopStatusRepository, IWorkshopRepository workshopRepository, IWorkshopSubjectRepository workshopSubjectRepository, IRatingRepository ratingRepository, IUserRepository userRepository, IRatingRepository ratingRepository1, WorkshopStatusService workshopStatusService) {
        super(workshopRepository);
        this.workshopRepository = workshopRepository;
        this.workshopSubjectRepository = workshopSubjectRepository;
        this.ratingRepository = ratingRepository1;
        this.workshopStatusService = workshopStatusService;
        this.workshopStatusRepository = workshopStatusRepository;
    }

    public Optional<WorkshopDto> getByIdAsDto(Long id) {
        return workshopRepository.findById(id).map(WorkshopDto::map);
    }

    public List<WorkshopDetailsDTO> getAllWorkshops() {

        return workshopRepository.findAll()
                .stream()
                .map(workshop -> {
                    WorkshopDetailsDTO workshopDto = new WorkshopDetailsDTO();
                    workshopDto.setId(workshop.getId());
                    workshopDto.setWorkshopStatus(workshopStatusRepository.findById(workshop.getWorkshopStatusId()).get().getName());
                    workshopDto.setDescription(workshop.getDescription());
                    workshopDto.setTitle(workshop.getTitle());
                    workshopDto.setNoOfAvailableSlots(workshop.getNoOfAvailableSlots());
                    return workshopDto;
                })
                .collect(Collectors.toList());
    }

    public List<WorkshopDetailsDTO> getWorkshopsBySubjectId(Long subjectId) {
        List<WorkshopSubject> workshopSubjects = workshopSubjectRepository.findBySubjectId(subjectId);

        List<Workshop> workshops = workshopSubjects.stream()
                .map(WorkshopSubject -> workshopRepository.findById(WorkshopSubject.getWorkshopId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());


        return workshops.stream()
                .map(workshop -> {
                    WorkshopDetailsDTO workshopDto = new WorkshopDetailsDTO();
                    workshopDto.setId(workshop.getId());
                    workshopDto.setWorkshopStatus(workshopStatusRepository.findById(workshop.getWorkshopStatusId()).get().getName());
                    workshopDto.setDescription(workshop.getDescription());
                    workshopDto.setTitle(workshop.getTitle());
                    workshopDto.setNoOfAvailableSlots(workshop.getNoOfAvailableSlots());
                    workshopDto.setDateOfEvent(workshop.getDateOfEvent().toString());
                    workshopDto.setWorkshopStatusId(workshop.getWorkshopStatusId());
                    return workshopDto;
                })
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
        return workshopRepository.findByOwnerId(userId)
                .stream()
                .map(WorkshopDto::map)
                .collect(Collectors.toList());
    }
    public List<WorkshopDto2> getWorkshopsByUserId2(Long userId) {
        return workshopRepository.findByOwnerId(userId)
                .stream()
                .map(workshop -> WorkshopDto2.map(workshop, workshopStatusService))  // Pass workshopStatusService
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
            existingWorkshop.setWorkshopStatusId(updatedWorkshop.getWorkshopStatusId());
            existingWorkshop.setOwnerId(updatedWorkshop.getOwnerId());

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
