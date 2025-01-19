package com.smen.Controllers;

import com.smen.Services.ActivityLogService;
import com.smen.DTO.ActivityLog.ActivityLogDto;
import com.smen.DTO.MentorRequestDto;
import com.smen.Services.MentorRequestService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/mentor-request")
@CrossOrigin("*")
public class MentorRequestController {

    @Autowired
    private final MentorRequestService mentorRequestService;
    private ActivityLogService activityLogService;
    // Endpoint to get all mentor requests
    @GetMapping
    public ResponseEntity<List<MentorRequestDto>> getAllMentorRequests() {
        return ResponseEntity.ok(mentorRequestService.getAllMentorRequests());
    }

    // Get mentor requests by requester ID
    @GetMapping("/requester/{id}")
    public ResponseEntity<List<MentorRequestDto>> getMentorRequestsByRequesterId(@PathVariable Long id) {
        return ResponseEntity.ok(mentorRequestService.getMentorsByRequesterId(id));
    }

    // Get mentor requests by reviewer ID
    @GetMapping("/reviewer/{id}")
    public ResponseEntity<List<MentorRequestDto>> getMentorRequestsByReviewerId(@PathVariable Long id) {
        return ResponseEntity.ok(mentorRequestService.getMentorsByReviewerId(id));
    }

    // Get mentor requests by status ID
    @GetMapping("/status/{id}")
    public ResponseEntity<List<MentorRequestDto>> getMentorRequestsByStatus(@PathVariable Long id) {
        return ResponseEntity.ok(mentorRequestService.getMentorsByMentorRequestStatusId(id));
    }

    // Endpoint to approve a mentor request
    @PostMapping("/{id}/approve")
    public ResponseEntity<MentorRequestDto> approveMentorRequest(@PathVariable Long id) {
        MentorRequestDto approvedRequest = mentorRequestService.approveMentorRequest(id);

        if (approvedRequest == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        ActivityLogDto activityLogDto= new ActivityLogDto("c","mentor request status",userId);
        activityLogService.saveActivityLog(activityLogDto);
        return ResponseEntity.ok(approvedRequest);
    }

    // Endpoint to reject a mentor request
    @PostMapping("/{id}/reject")
    public ResponseEntity<MentorRequestDto> rejectMentorRequest(@PathVariable Long id) {
        MentorRequestDto rejectedRequest = mentorRequestService.rejectMentorRequest(id);

        if (rejectedRequest == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        ActivityLogDto activityLogDto= new ActivityLogDto("e","mentor request status",userId);
        activityLogService.saveActivityLog(activityLogDto);
        return ResponseEntity.ok(rejectedRequest);
    }

    // Endpoint to create a mentor request
    @PostMapping
    public ResponseEntity<MentorRequestDto> createMentorRequest(@RequestBody MentorRequestDto requestDto) {
        MentorRequestDto newRequest = mentorRequestService.createMentorRequest(requestDto);

        if (newRequest == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        ActivityLogDto activityLogDto= new ActivityLogDto("c","mentor request status",userId);
        activityLogService.saveActivityLog(activityLogDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRequest);
    }

    // Endpoint to delete a mentor request
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMentorRequest(@PathVariable Long id) {
        boolean isDeleted = mentorRequestService.deleteById(id);

        if (isDeleted) {
            ActivityLogDto activityLogDto= new ActivityLogDto("d","mentor request status",userId);
            activityLogService.saveActivityLog(activityLogDto);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}