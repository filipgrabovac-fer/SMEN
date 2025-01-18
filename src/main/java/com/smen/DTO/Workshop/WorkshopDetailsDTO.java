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
    private String workshopStatus;
    private String dateOfEvent;
    private Long workshopStatusId;

    public Workshop toEntity( WorkshopStatus workshopStatus) {
        Workshop workshop = new Workshop();
        workshop.setId(this.id);
        workshop.setTitle(this.title);
        workshop.setDescription(this.description);
        workshop.setDuration(this.duration);
        workshop.setNoOfAvailableSlots(this.noOfAvailableSlots);
        workshop.setOwnerId(this.ownerId);
        workshop.setWorkshopStatusId(this.workshopStatusId);
        return workshop;
    }
}