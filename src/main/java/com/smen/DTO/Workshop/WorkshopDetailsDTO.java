package com.smen.DTO.Workshop;

import com.smen.Models.User;
import com.smen.Models.Workshop;
import com.smen.Models.WorkshopStatus;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkshopDetailsDTO {
    private Long id;
    private String title;
    private String description;
    private Integer duration;
    private Integer noOfAvailableSlots;
    private Long ownerId;
    private Long workshopStatusId;

    public static WorkshopDetailsDTO map(Workshop workshop) {
        WorkshopDetailsDTO dto = new WorkshopDetailsDTO();
        dto.setId(workshop.getId());
        dto.setTitle(workshop.getTitle());
        dto.setDescription(workshop.getDescription());
        dto.setDuration(workshop.getDuration());
        dto.setNoOfAvailableSlots(workshop.getNoOfAvailableSlots());
        dto.setOwnerId(workshop.getOwnerId() != null ? workshop.getOwnerId() : null);
        dto.setWorkshopStatusId(workshop.getWorkshopStatusId() != null ? workshop.getWorkshopStatusId() : null);
        return dto;
    }

    public Workshop toEntity( WorkshopStatus workshopStatus) {
        Workshop workshop = new Workshop();
        workshop.setId(this.id);
        workshop.setTitle(this.title);
        workshop.setDescription(this.description);
        workshop.setDuration(this.duration);
        workshop.setNoOfAvailableSlots(this.noOfAvailableSlots);
        workshop.setOwnerId(this.ownerId);
        workshop.setWorkshopStatusId(1L);
        return workshop;
    }
}