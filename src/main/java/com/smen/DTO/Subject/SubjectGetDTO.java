package com.smen.DTO.Subject;
import com.smen.Models.Subject;
import lombok.*;

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

    public static SubjectGetDTO map(Subject subject) {
        SubjectGetDTO dto = new SubjectGetDTO();
        dto.setId(subject.getId());
        dto.setTitle(subject.getTitle());
        dto.setTags(subject.getTags());
        dto.setDescription(subject.getDescription());
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