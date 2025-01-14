package com.smen.Controllers;

import com.smen.Models.MentorRequestStatus;
import com.smen.Models.Role;
import com.smen.Services.MentorRequestStatusService;
import com.smen.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mentor-request-status")
@CrossOrigin("*")
public class MentorRequestStatusController {

    @Autowired
    private final MentorRequestStatusService mentorRequestStatusService;

    public MentorRequestStatusController(MentorRequestStatusService mentorRequestStatusService) {
        this.mentorRequestStatusService = mentorRequestStatusService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<MentorRequestStatus> getMentorRequestStatusByName(@PathVariable String name) {
        Optional<MentorRequestStatus> mentorRequestStatus = mentorRequestStatusService.getMentorRequestStatusByName(name);
        return mentorRequestStatus.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MentorRequestStatus>> getAllMentorRequestStatuses() {
        List<MentorRequestStatus> mentorRequestStatuses = mentorRequestStatusService.getAll();
        return ResponseEntity.ok(mentorRequestStatuses);
    }
}
