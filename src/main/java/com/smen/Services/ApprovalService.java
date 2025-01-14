package com.smen.Services;

import com.smen.DTO.Approval.ApprovalDto;
import com.smen.Models.Approval;
import com.smen.Models.User;
import com.smen.Models.Workshop;
import com.smen.Repositories.IApprovalRepository;
import com.smen.Repositories.IUserRepository;
import com.smen.Repositories.IWorkshopRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApprovalService extends BaseEntityService<Approval, Long>{
    private final IApprovalRepository approvalRepository;
    private final IUserRepository userRepository;
    private final IWorkshopRepository workshopRepository;

    public ApprovalService(IApprovalRepository approvalRepository, IUserRepository userRepository, IWorkshopRepository workshopRepository) {
        super(approvalRepository);
        this.approvalRepository = approvalRepository;
        this.userRepository = userRepository;
        this.workshopRepository = workshopRepository;
    }

    // Method to approve a workshop
    public ApprovalDto approveWorkshop(Long approvalId, String comment) {
        Optional<Approval> oldApproval = approvalRepository.findById(approvalId);
        if (oldApproval.isEmpty()) {
            return null;
        }

        Approval approval = oldApproval.get();
        approval.setApprovedAt(LocalDateTime.now());
        approval.setComment(comment);

        Approval updatedApproval = approvalRepository.save(approval);

        return ApprovalDto.map(updatedApproval);
    }

    public ApprovalDto createApproval(ApprovalDto approvalDto) {
        Optional<Workshop> workshop = workshopRepository.findById(approvalDto.getWorkshopId());
        Optional<User> user = userRepository.findById(approvalDto.getApprovedById());

        if (workshop.isEmpty() || user.isEmpty()) {
            return null;
        }

        Approval approval = approvalDto.toEntity(workshop.get(), user.get());

        return ApprovalDto.map(approvalRepository.save(approval));
    }

    public List<ApprovalDto> getApprovalsByWorkshop(Long workshopId) {
        return approvalRepository.findByWorkshopId(workshopId)
                .stream()
                .map(ApprovalDto::map)
                .collect(Collectors.toList());
    }

    public List<ApprovalDto> getApprovalsByUser(Long userId) {
        return approvalRepository.findByUserId(userId)
                .stream()
                .map(ApprovalDto::map)
                .collect(Collectors.toList());
    }
}
