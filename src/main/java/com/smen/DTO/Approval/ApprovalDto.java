package com.smen.DTO.Approval;

import com.smen.Models.Approval;
import com.smen.Models.User;
import com.smen.Models.Workshop;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalDto {
    private Long id;
    private Long workshopId;
    private Long approvedById;
    private LocalDateTime approvedAt;
    private String comment;

    public static ApprovalDto map(Approval approval) {
        ApprovalDto dto = new ApprovalDto();
        dto.setId(approval.getId());
        dto.setWorkshopId(approval.getWorkshop().getId());
        dto.setApprovedById(approval.getUser().getId());
        dto.setApprovedAt(approval.getApprovedAt());
        dto.setComment(approval.getComment());
        return dto;
    }

    public Approval toEntity(Workshop workshop, User user) {
        Approval approval = new Approval();
        approval.setId(this.id);
        approval.setWorkshop(workshop);
        approval.setUser(user);
        approval.setApprovedAt(this.approvedAt);
        approval.setComment(this.comment);
        return approval;
    }
}

