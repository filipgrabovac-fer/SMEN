package com.smen.Controllers;

import com.smen.Models.MentorRequest;
import com.smen.Services.MentorRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/mentor-request")
public class MentorRequestController {
    private final MentorRequestService mentorRequestService;

    // Endpoint to get all mentor requests
    @GetMapping
    public ResponseEntity<List<MentorRequest>> getAllMentorRequests() {
        List<MentorRequest> mentorRequests = mentorRequestService.getAllMentorRequests();
        return ResponseEntity.ok(mentorRequests);
    }

    // Endpoint to approve a mentor request
    @PostMapping("/{id}/approve")
    public ResponseEntity<MentorRequest> approveMentorRequest(@PathVariable Long id) {
        MentorRequest approvedRequest = mentorRequestService.approveMentorRequest(id);
        return ResponseEntity.ok(approvedRequest);
    }

    // Endpoint to reject a mentor request
    @PostMapping("/{id}/reject")
    public ResponseEntity<MentorRequest> rejectMentorRequest(@PathVariable Long id) {
        MentorRequest rejectedRequest = mentorRequestService.rejectMentorRequest(id);
        return ResponseEntity.ok(rejectedRequest);
    }
}
