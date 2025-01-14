package com.smen.DTO.SubjectSuggestion;

import com.smen.Models.SubjectSuggestion;
import com.smen.Models.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectSuggestionDto {
    private Long id;
    private String title;
    private String description;
    private Long userId;

    public static SubjectSuggestionDto fromEntity(com.smen.Models.SubjectSuggestion entity) {
        SubjectSuggestionDto dto = new SubjectSuggestionDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setUserId(entity.getUser().getId());
        return dto;
    }

    public SubjectSuggestion toEntity(User user) {
        SubjectSuggestion subjectSuggestion = new SubjectSuggestion();
        subjectSuggestion.setId(this.id);
        subjectSuggestion.setTitle(this.title);
        subjectSuggestion.setDescription(this.description);
        subjectSuggestion.setUser(user);
        return subjectSuggestion;
    }
}
