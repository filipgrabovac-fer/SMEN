package com.smen.DTO.WorkshopSubject;

import com.smen.Models.Subject;
import com.smen.Models.Workshop;
import com.smen.Models.WorkshopSubject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkshopSubjectDto {
    private Long id;
    private Long workshopId;
    private Long subjectId;

    public static WorkshopSubjectDto map(WorkshopSubject workshopSubject) {
        WorkshopSubjectDto dto = new WorkshopSubjectDto();
        dto.setId(workshopSubject.getId());
        dto.setWorkshopId(workshopSubject.getWorkshopId());
        dto.setSubjectId(workshopSubject.getSubjectId());
        return dto;
    }

    public WorkshopSubject toEntity(Workshop workshop, Subject subject) {
        WorkshopSubject workshopSubject = new WorkshopSubject();
        workshopSubject.setId(this.id);
        workshopSubject.setWorkshopId(workshop.getId());
        workshopSubject.setSubjectId(subject.getId());
        return workshopSubject;
    }
}