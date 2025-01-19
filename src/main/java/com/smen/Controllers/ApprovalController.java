package com.smen.Controllers;

import com.smen.Services.ActivityLogService;
import com.smen.DTO.ActivityLog.ActivityLogDto;
import com.smen.DTO.Approval.ApprovalDto;
import com.smen.Services.ApprovalService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/approval")
@AllArgsConstructor
@CrossOrigin("*")
public class ApprovalController {

    @Autowired
    private final ApprovalService approvalService;

    @GetMapping("/workshop/{workshopId}")
    public ResponseEntity<List<ApprovalDto>> getApprovalsByWorkshop(@PathVariable Long workshopId) {
        return ResponseEntity.ok(approvalService.getApprovalsByWorkshop(workshopId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ApprovalDto>> getApprovalsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(approvalService.getApprovalsByUser(userId));
    }

    @PostMapping("/new")
    public ResponseEntity<ApprovalDto> createApproval(@RequestBody ApprovalDto approvalDto) {
        ApprovalDto createdApproval = approvalService.createApproval(approvalDto);

        if (createdApproval == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdApproval);
        }
    }

    @PutMapping("/{approvalId}/approve")
    public ResponseEntity<ApprovalDto> approveWorkshop(
            @PathVariable Long approvalId,
            @RequestParam String comment) {

        ApprovalDto updatedApproval = approvalService.approveWorkshop(approvalId, comment);

        if (updatedApproval == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(updatedApproval);
        }
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
