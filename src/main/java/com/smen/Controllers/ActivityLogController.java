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
    
    private ActivityLogService activitylogService;

    @GetMapping("/{id}")
    public ResponseEntity<ActivityLog> getActivityLog(@PathVariable Long id) {
        Optional<ActivityLog> activityLog = activitylogService.getById(id);

        return activityLog.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ActivityLog>> getActivityLogs() {
        return ResponseEntity.ok(activitylogService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivityLog(@PathVariable Long id) {
        boolean isDeleted = activitylogService.deleteById(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //obrisali smo endpoint za create
    @GetMapping("/user/{id}")
    public ResponseEntity<List<ActivityLog>> getActivityLogsByUser(@PathVariable Long id) {
        return ResponseEntity.ok(activitylogService.getByUser(id));
    }

    @GetMapping("/workshop/{id}")
    public ResponseEntity<List<ActivityLog>> getActivityLogsByWorkshop(@PathVariable Long id) {
        return ResponseEntity.ok(activitylogService.getByWorkshop(id));
    }
}
