package com.smen.Services;

import com.smen.Models.Approval;
import com.smen.Models.User;
import com.smen.Repositories.IApprovalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ApprovalService extends BaseEntityService<Approval, Long>{
    private final IApprovalRepository approvalRepository;

    public ApprovalService(IApprovalRepository approvalRepository) {
        super(approvalRepository);
        this.approvalRepository = approvalRepository;
    }

    // Method to approve a workshop
    public Approval approveWorkshop(Long approvalId, String comment) {

        Optional<Approval> oldApproval = (Optional<Approval>) approvalRepository.findById(approvalId);
        if (oldApproval.isEmpty())
            return null;
        Approval approval = oldApproval.get();

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

}
