package com.smen.Services;

import com.smen.Models.Workshop;
import com.smen.Repositories.IRatingRepository;
import com.smen.Repositories.IUserRepository;
import com.smen.Repositories.IWorkshopRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkshopService extends BaseEntityService<Workshop, Long> {

    private final IWorkshopRepository workshopRepository;
    private final IRatingRepository ratingRepository;

    public WorkshopService(IWorkshopRepository workshopRepository, IRatingRepository ratingRepository, IUserRepository userRepository) {
        super(workshopRepository);
        this.workshopRepository = workshopRepository;
        this.ratingRepository = ratingRepository;
    }

    public Workshop updateWorkshop(Long id, Workshop newWorkshop) {
        Optional<Workshop> oldWorkshop = (Optional<Workshop>) workshopRepository.findById(id);
        if (oldWorkshop.isEmpty())
            return null;
        Workshop updatedWorkshop = oldWorkshop.get();

        updatedWorkshop.setTitle(newWorkshop.getTitle());
        updatedWorkshop.setDescription(newWorkshop.getDescription());
        updatedWorkshop.setDuration(newWorkshop.getDuration());
        updatedWorkshop.setNoOfAvailableSlots(newWorkshop.getNoOfAvailableSlots());

        return updatedWorkshop;
    }

    //sve radionice nekog korisnika
    public List<Workshop> getByUser(Long userId) {
        return workshopRepository.findByUserId(userId);

    }

    //sve radionice na neku temu
    public List<Workshop> getBySubject(Long subjectId) {
        return workshopRepository.findBySubjectId(subjectId);

    }



}
