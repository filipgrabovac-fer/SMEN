package com.smen.Services;

import com.smen.Models.WorkshopStatus;
import com.smen.Repositories.IWorkshopStatusRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkshopStatusService extends BaseEntityService<WorkshopStatus, Long> {
    private final IWorkshopStatusRepository workshopStatusRepository;

    public WorkshopStatusService(IWorkshopStatusRepository workshopStatusRepository) {
        super(workshopStatusRepository);
        this.workshopStatusRepository = workshopStatusRepository;
    }

    public Optional<WorkshopStatus> getWorkshopStatusByName(String name) {
        return workshopStatusRepository.findByName(name);
    }

}