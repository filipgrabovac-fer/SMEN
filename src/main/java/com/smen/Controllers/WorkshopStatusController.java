package com.smen.Controllers;

import com.smen.Services.ActivityLogService;
import com.smen.DTO.ActivityLog.ActivityLogDto;
import com.smen.Models.User;
import com.smen.Models.WorkshopStatus;
import com.smen.Services.WorkshopStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class WorkshopStatusController {

    @Autowired
    private final WorkshopStatusService workshopStatusService;
    private ActivityLogService activityLogService;
    public WorkshopStatusController(WorkshopStatusService workshopStatusService) {
        this.workshopStatusService = workshopStatusService;
    }


    @GetMapping("/{name}")
    public ResponseEntity<WorkshopStatus> getWorkshopStatusByName(@PathVariable String name) {
        return workshopStatusService.getWorkshopStatusByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping
    public ResponseEntity<List<WorkshopStatus>> getAllWorkshopStatuses() {
        List<WorkshopStatus> workshopStatuses = workshopStatusService.getAll();
        return ResponseEntity.ok(workshopStatuses);
    }
}
