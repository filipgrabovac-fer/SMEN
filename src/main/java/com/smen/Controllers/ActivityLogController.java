package com.smen.Controllers;

import com.smen.Models.ActivityLog;
import com.smen.Services.ActivityLogService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/activity-log")
public class ActivityLogController {
    
    private ActivityLogService service;

    @GetMapping("/{id}")
    public ResponseEntity<ActivityLog> getActivityLog(@PathVariable Long id) {
        Optional<ActivityLog> activityLog = service.getById(id);

        return activityLog.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public ResponseEntity<List<ActivityLog>> getActivityLogs() {
        List<ActivityLog> activityLogs = service.getAll();
        if (activityLogs.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(activityLogs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteActivityLog(@PathVariable Long id) {
        boolean isDeleted = service.deleteById(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("ActivityLog deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ActivityLog not found");
        }
    }

    //obrisali smo endpoint za create
    @GetMapping("/user/{id}")
    public ResponseEntity<List<ActivityLog>> getActivityLogsByUser(@PathVariable Long id) {
        List<ActivityLog> activityLogs = service.getByUser(id);
        if (activityLogs.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(activityLogs);
    }
}
