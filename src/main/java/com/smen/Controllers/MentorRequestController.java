package com.smen.Controllers;

import com.smen.Models.MentorRequest;
import com.smen.Models.Workshop;
import com.smen.Services.MentorRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMentorRequest(@PathVariable Long id) {
        boolean isDeleted = mentorRequestService.deleteById(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/requester/{id}")
    public ResponseEntity<List<MentorRequest>> getMentorRequestsByRequesterId(Long id) {
        return ResponseEntity.ok(mentorRequestService.getMentorsByRequesterId(id));
    }

    @GetMapping("/reviewer/{id}")
    public ResponseEntity<List<MentorRequest>> getMentorRequestsByReviewerId(Long id) {
        return ResponseEntity.ok(mentorRequestService.getMentorsByReviewerId(id));
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<List<MentorRequest>> getMentorRequestsByStatusId(Long id) {
        return ResponseEntity.ok(mentorRequestService.getMentorsByMentorRequestStatusId(id));
    }

    // Endpoint to approve a mentor request
    @PostMapping("/{id}/approve")
    public ResponseEntity<MentorRequest> approveMentorRequest(@PathVariable Long id) {
        MentorRequest approvedRequest = mentorRequestService.approveMentorRequest(id);
        if (approvedRequest == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(approvedRequest);
    }

    // Endpoint to reject a mentor request
    @PostMapping("/{id}/reject")
    public ResponseEntity<MentorRequest> rejectMentorRequest(@PathVariable Long id) {
        MentorRequest rejectedRequest = mentorRequestService.rejectMentorRequest(id);
        if (rejectedRequest == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(rejectedRequest);
    }

    @PostMapping
    public ResponseEntity<MentorRequest> createMentorRequest(@RequestBody MentorRequest mentorRequest) {
        MentorRequest newMentorRequest = mentorRequestService.create(mentorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMentorRequest);
    }
}
