package com.smen.Controllers;


import com.smen.Models.User;
import com.smen.Models.WorkshopStatus;
import com.smen.Services.WorkshopStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class WorkshopStatusController {
    private final WorkshopStatusService workshopStatusService;

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
