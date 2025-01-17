package com.smen.DTO.Workshop;

import com.smen.Models.Workshop;
import com.smen.Services.WorkshopStatusService;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkshopDto2 {
    private Long id;
    private String title;
    private String description;
    private Integer duration;
    private Integer noOfAvailableSlots;
    private Long ownerId;
    private String workshopStatusName;

    // Make map method non-static to access instance variables
    public static WorkshopDto2 map(Workshop workshop, WorkshopStatusService workshopStatusService) {
        WorkshopDto2 dto = new WorkshopDto2();
        dto.setId(workshop.getId());
        dto.setTitle(workshop.getTitle());
        dto.setDescription(workshop.getDescription());
        dto.setDuration(workshop.getDuration());
        dto.setNoOfAvailableSlots(workshop.getNoOfAvailableSlots());
        dto.setOwnerId(workshop.getOwnerId());

        // Use workshopStatusService to fetch the name based on workshopStatusId
        if (workshopStatusService != null) {
            String statusName = workshopStatusService.getWorkshopStatusNameById(workshop.getWorkshopStatusId());
            dto.setWorkshopStatusName(statusName);  // Only set the name of the status
        }

        return dto;
    }
    public Workshop toEntity() {
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
