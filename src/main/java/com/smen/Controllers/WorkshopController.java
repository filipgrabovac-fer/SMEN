package com.smen.Controllers;

import com.smen.Services.ActivityLogService;
import com.smen.DTO.ActivityLog.ActivityLogDto;
import com.smen.DTO.Workshop.WorkshopCreateDTO;
import com.smen.DTO.Workshop.WorkshopDto2;
import com.smen.DTO.Workshop.WorkshopDetailsDTO;
import com.smen.DTO.Workshop.WorkshopDto;
import com.smen.Models.User;
import com.smen.Models.Workshop;
import com.smen.Models.WorkshopStatus;
import com.smen.Models.WorkshopSubject;
import com.smen.Services.WorkshopService;
import com.smen.Services.WorkshopSubjectService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/workshop")
@CrossOrigin("*")
public class WorkshopController {

    @Autowired
    private final WorkshopService workshopService;
    @Autowired
    private WorkshopSubjectService workshopSubjectService;
    private ActivityLogService activityLogService;
    @GetMapping
    public ResponseEntity<List<WorkshopDetailsDTO>> getAllWorkshops() {
        return ResponseEntity.ok(workshopService.getAllWorkshops(1L));
    }

    @GetMapping("/{id}/user/{userId}")
    public ResponseEntity<WorkshopDetailsDTO> getWorkshopById(@PathVariable Long id, @PathVariable Long userId) {
        return ResponseEntity.ok(workshopService.getByDetailedDto(id, userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<WorkshopDto>> searchWorkshopsByTitle(@RequestParam String title) {
        return ResponseEntity.ok(workshopService.getWorkshopsByTitle(title));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkshopDto2>> getWorkshopsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(workshopService.getWorkshopsByUserId2(userId));
    }

    @GetMapping("/status/{statusId}")
    public ResponseEntity<List<WorkshopDto>> getWorkshopsByStatus(@PathVariable Long statusId) {
        return ResponseEntity.ok(workshopService.getWorkshopsByStatusId(statusId));
    }

    @GetMapping("/subject/{subjectId}/user/{userId}")
    public ResponseEntity<List<WorkshopDetailsDTO>> getWorkshopsBySubject(@PathVariable Long subjectId, @PathVariable Long userId) {
        return ResponseEntity.ok(workshopService.getWorkshopsBySubjectId(subjectId, userId));
    }

    @GetMapping("/available-slots")
    public ResponseEntity<List<WorkshopDto>> getAvailableWorkshops() {
        return ResponseEntity.ok(workshopService.getAvailableWorkshops());
    }

    @PostMapping
    public ResponseEntity<Boolean> createWorkshop(
            @RequestBody WorkshopCreateDTO workshopDto ){
        Workshop workshop = workshopDto.toEntity();
        WorkshopDto createdWorkshopDto = workshopService.saveWorkshopDto(workshop);
        WorkshopSubject workshopSubject = new WorkshopSubject();

        workshopSubject.setWorkshopId(createdWorkshopDto.getId());
        workshopSubject.setSubjectId(workshopDto.getSubjectId());
        workshopSubjectService.saveWorkshopSubject(workshopSubject);
        ActivityLogDto activityLogDto= new ActivityLogDto("c","workshop",userId);
        activityLogService.saveActivityLog(activityLogDto);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkshopDto> updateWorkshop(
            @PathVariable Long id,
            @RequestBody WorkshopDto workshopDto) {

        if (workshopService.getById(id).isEmpty()) return ResponseEntity.badRequest().build();

        Workshop existingWorkshop = workshopService.getById(id).get();
        existingWorkshop.setTitle(workshopDto.getTitle());
        existingWorkshop.setDescription(workshopDto.getDescription());
        existingWorkshop.setWorkshopStatusId(workshopDto.getWorkshopStatusId());
        existingWorkshop.setNoOfAvailableSlots(workshopDto.getNoOfAvailableSlots());
        WorkshopDto updatedWorkshopDto = workshopService.saveWorkshopDto(existingWorkshop);
        ActivityLogDto activityLogDto= new ActivityLogDto("e","workshop",userId);
        activityLogService.saveActivityLog(activityLogDto);
        return ResponseEntity.ok(updatedWorkshopDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteWorkshop(@PathVariable Long id) {
        boolean isDeleted = workshopService.deleteWorkshop(id);
        if (isDeleted) {
            ActivityLogDto activityLogDto= new ActivityLogDto("d","workshop",userId);
            activityLogService.saveActivityLog(activityLogDto);
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
