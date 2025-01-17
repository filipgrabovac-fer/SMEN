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
    public WorkshopStatus getWorkshopStatusById(Long id) {
        return workshopStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkshopStatus not found for ID: " + id));
    }
    public String getWorkshopStatusNameById(Long workshopStatusId) {
        Optional<WorkshopStatus> workshopStatus = workshopStatusRepository.findById(workshopStatusId);
        return workshopStatus.map(WorkshopStatus::getName).orElse("Unknown Status");
    }
    public Optional<WorkshopStatus> getWorkshopStatusByName(String name) {
        return workshopStatusRepository.findByName(name);
    }

}