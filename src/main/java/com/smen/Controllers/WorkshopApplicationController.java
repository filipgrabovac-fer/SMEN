package com.smen.Controllers;

import com.smen.Models.WorkshopApplication;
import com.smen.Services.WorkshopApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/workshop-application")
@CrossOrigin("*")
public class WorkshopApplicationController {

    private final WorkshopApplicationService workshopApplicationService;

    @GetMapping
    public List<WorkshopApplication> getWorkshopApplications(){
        return workshopApplicationService.getWorkshopsAll();
    }

    @PostMapping("workshop/{workshopId}/user/{userId}")
    public ResponseEntity<Boolean> createWorkshopApplication(@PathVariable Long workshopId, @PathVariable Long  userId){
        WorkshopApplication workshopApplication = workshopApplicationService.createWorkshopApplication(userId, workshopId);
        if (workshopApplication == null) return ResponseEntity.unprocessableEntity().build();
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> updateWorkshopApplication(@PathVariable Long id){
        workshopApplicationService.deleteWorkshopApplication(id);
        return ResponseEntity.ok(true);
    }
}
