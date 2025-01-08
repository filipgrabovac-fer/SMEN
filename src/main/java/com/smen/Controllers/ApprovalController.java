package com.smen.Controllers;

import com.smen.Models.Approval;
import com.smen.Models.User;
import com.smen.Services.ApprovalService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/approval")
@AllArgsConstructor
public class ApprovalController {
    private final ApprovalService approvalService;

    @GetMapping("/workshop/{workshopId}")
    public ResponseEntity<List<Approval>> getApprovalsByWorkshop(@PathVariable Long workshopId) {
        return ResponseEntity.ok(approvalService.getApprovalsByWorkshop(workshopId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Approval>> getApprovalsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(approvalService.getApprovalsByUser(userId));
    }

    @PostMapping("/new")
    public ResponseEntity<Approval> createApproval(@RequestBody Approval approval) {
        return ResponseEntity.status(HttpStatus.CREATED).body(approvalService.create(approval));
    }

    @PutMapping("/{approvalId}/approve")
    public ResponseEntity<Approval> approveWorkshop(
            @PathVariable Long approvalId,
            @RequestParam String comment) {

        Approval updatedApproval = approvalService.approveWorkshop(approvalId, comment);
        if (updatedApproval == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(updatedApproval);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApproval(@PathVariable Long id) {
        boolean isDeleted = approvalService.deleteById(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
