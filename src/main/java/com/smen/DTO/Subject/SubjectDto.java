package com.smen.DTO.Subject;
import com.smen.Models.Subject;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {
    private Long authorId;
    private String title;
    private String tags;
    private String description;

    public static SubjectDto map(Subject subject) {
        SubjectDto dto = new SubjectDto();
        dto.setAuthorId(subject.getAuthorId());
        dto.setTitle(subject.getTitle());
        dto.setTags(subject.getTags());
        dto.setDescription(subject.getDescription());
        return dto;
    }

    public Subject toEntity() {
        Subject subject = new Subject();
        subject.setAuthorId(this.authorId);
        subject.setTitle(this.title);
        subject.setTags(this.tags);
        subject.setDescription(this.description);
        return subject;
    }
}