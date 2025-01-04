package com.smen.Services;

import com.smen.Models.Approval;
import com.smen.Repositories.IApprovalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApprovalService extends BaseEntityService<Approval, Long>{
    private final IApprovalRepository approvalRepository;

    public ApprovalService(IApprovalRepository approvalRepository) {
        super(approvalRepository);
        this.approvalRepository = approvalRepository;
    }

    // Method to approve a workshop
    public Approval approveWorkshop(Long approvalId, String comment) {
        Approval approval = approvalRepository.findById(approvalId)
                .orElseThrow(() -> new IllegalArgumentException("Approval not found"));

        approval.setApprovedAt(LocalDateTime.now());
        approval.setComment(comment);
        approval.setUpdatedAt(LocalDateTime.now());

        return approvalRepository.save(approval);
    }

    public List<Approval> getApprovalsByWorkshop(Long workshopId) {
        return approvalRepository.findByWorkshopId(workshopId);
    }

    public List<Approval> getApprovalsByUser(Long userId) {
        return approvalRepository.findByUserId(userId);
    }

    public Approval saveApproval(Approval approval) {
        return approvalRepository.save(approval);
    }
}
