package com.smen.Dto.Workshop;

import com.smen.Models.User;
import com.smen.Models.Workshop;
import com.smen.Models.WorkshopStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkshopDto {
    private Long id;
    private String title;
    private String description;
    private Integer duration;
    private Integer noOfAvailableSlots;
    private Long ownerId;
    private Long workshopStatusId;

    public static WorkshopDto map(Workshop workshop) {
        WorkshopDto dto = new WorkshopDto();
        dto.setId(workshop.getId());
        dto.setTitle(workshop.getTitle());
        dto.setDescription(workshop.getDescription());
        dto.setDuration(workshop.getDuration());
        dto.setNoOfAvailableSlots(workshop.getNoOfAvailableSlots());
        dto.setOwnerId(workshop.getUser() != null ? workshop.getUser().getId() : null);
        dto.setWorkshopStatusId(workshop.getWorkshopStatus() != null ? workshop.getWorkshopStatus().getId() : null);
        return dto;
    }

    public Workshop toEntity(User owner, WorkshopStatus workshopStatus) {
        Workshop workshop = new Workshop();
        workshop.setId(this.id);
        workshop.setTitle(this.title);
        workshop.setDescription(this.description);
        workshop.setDuration(this.duration);
        workshop.setNoOfAvailableSlots(this.noOfAvailableSlots);
        workshop.setUser(owner);
        workshop.setWorkshopStatus(workshopStatus);
        return workshop;
    }
}