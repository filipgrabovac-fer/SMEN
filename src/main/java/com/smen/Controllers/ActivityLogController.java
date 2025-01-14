package com.smen.Controllers;

import com.smen.DTO.ActivityLog.ActivityLogDto;
import com.smen.Models.User;
import com.smen.Models.Workshop;
import com.smen.Services.ActivityLogService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/activity-log")
public class ActivityLogController {

    @Autowired
    private ActivityLogService activityLogService;

    // Get activity log by ID
    @GetMapping("/{id}")
    public ResponseEntity<ActivityLogDto> getActivityLog(@PathVariable Long id) {
        Optional<ActivityLogDto> activityLogDto = activityLogService.getActivityLogDto(id);

        return activityLogDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all activity logs
    @GetMapping
    public ResponseEntity<List<ActivityLogDto>> getActivityLogs() {
        return ResponseEntity.ok(activityLogService.getAllActivityLogs());
    }

    // Get activity logs by user ID
    @GetMapping("/user/{id}")
    public ResponseEntity<List<ActivityLogDto>> getActivityLogsByUser(@PathVariable Long id) {
        return ResponseEntity.ok(activityLogService.getActivityLogsByUser(id));
    }

    // Get activity logs by workshop ID
    @GetMapping("/workshop/{id}")
    public ResponseEntity<List<ActivityLogDto>> getActivityLogsByWorkshop(@PathVariable Long id) {
        return ResponseEntity.ok(activityLogService.getActivityLogsByWorkshop(id));
    }

    // Create or update an activity log
    @PostMapping
    public ResponseEntity<ActivityLogDto> saveActivityLog(
            @RequestBody ActivityLogDto activityLogDto,
            @RequestParam Optional<User> user,
            @RequestParam Optional<Workshop> workshop) {

        // Handle user and workshop dependencies
        if (user.isEmpty() || workshop.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ActivityLogDto savedActivityLogDto = activityLogService.saveActivityLog(
                activityLogDto, user.get(), workshop.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedActivityLogDto);
    }

    // Delete an activity log
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivityLog(@PathVariable Long id) {
        boolean isDeleted = activityLogService.deleteActivityLog(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
