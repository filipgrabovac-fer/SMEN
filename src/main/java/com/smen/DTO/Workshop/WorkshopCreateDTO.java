package com.smen.DTO.Workshop;

import com.smen.Models.Workshop;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
    private String dateOfEvent;
    private Integer noOfAvailableSlots;

    public Workshop toEntity() {
        Workshop workshop = new Workshop();
        workshop.setTitle(this.title);
        workshop.setDescription(this.description);
        workshop.setDuration(10000);
        workshop.setNoOfAvailableSlots(this.noOfAvailableSlots);
        workshop.setOwnerId(this.userId);
        workshop.setWorkshopStatusId(1L);
        workshop.setDateOfEvent(LocalDate.parse(this.dateOfEvent));
        return workshop;
    }
}