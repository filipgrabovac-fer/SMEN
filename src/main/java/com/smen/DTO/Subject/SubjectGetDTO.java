package com.smen.DTO.Subject;
import com.smen.Models.Subject;
import com.smen.Models.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectGetDTO {
    private Long id;
    private String title;
    private String description;
    private String tags;
    private String author;
    private String createdAt;


    public static SubjectGetDTO map(Subject subject) {
        SubjectGetDTO dto = new SubjectGetDTO();
        dto.setId(subject.getId());
        dto.setTitle(subject.getTitle());
        dto.setTags(subject.getTags());
        dto.setDescription(subject.getDescription());
        dto.setCreatedAt(subject.getCreatedAt().toString());
        return dto;
    }

    public Subject toEntity() {
        Subject subject = new Subject();
        subject.setId(this.id);
        subject.setTitle(this.title);
        subject.setTags(this.tags);
        subject.setDescription(this.description);
        return subject;
    }
}