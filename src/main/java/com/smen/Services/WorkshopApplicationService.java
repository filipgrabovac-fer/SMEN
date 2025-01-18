package com.smen.Services;

import com.smen.Models.WorkshopApplication;
import com.smen.Repositories.IWorkshopApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkshopApplicationService {
    private final IWorkshopApplicationRepository workshopApplicationRepository;

    public WorkshopApplicationService(IWorkshopApplicationRepository  workshopApplicationRepository){
        this.workshopApplicationRepository = workshopApplicationRepository;
    }
    public List<WorkshopApplication> getWorkshopsAll(){
        return workshopApplicationRepository.findAll();
    }

    public WorkshopApplication createWorkshopApplication(Long userId, Long workshopId){
        WorkshopApplication workshopApplication = new WorkshopApplication();
        workshopApplication.setWorkshopId(workshopId);
        workshopApplication.setUserId(userId);
        return workshopApplicationRepository.save(workshopApplication);
    }
    public void deleteWorkshopApplication(Long id){
        workshopApplicationRepository.deleteById(id);
    }
}
