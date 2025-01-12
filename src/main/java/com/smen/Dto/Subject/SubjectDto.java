package com.smen.Dto.Subject;
import com.smen.Models.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {
    private Long id;
    private String title;
    private String tags;
    private String description;

    public static SubjectDto map(Subject subject) {
        SubjectDto dto = new SubjectDto();
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