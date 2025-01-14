package com.smen.DTO.Workshop;

import com.smen.Models.Workshop;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkshopCreateDTO {
    private String title;
    private String description;
    private Long subjectId;
    private Long userId;

    public Workshop toEntity() {
        Workshop workshop = new Workshop();
        workshop.setTitle(this.title);
        workshop.setDescription(this.description);
        workshop.setDuration(10000);
        workshop.setNoOfAvailableSlots(10);
        workshop.setOwnerId(this.userId);
        workshop.setWorkshopStatusId(1L);
        return workshop;
    }
}