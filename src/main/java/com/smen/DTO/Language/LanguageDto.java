package com.smen.DTO.Language;

import com.smen.Models.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LanguageDto {
    private Long id;
    private String name;

    public static LanguageDto map(Language language) {
        LanguageDto dto = new LanguageDto();
        dto.setId(language.getId());
        dto.setName(language.getName());
        return dto;
    }

    public Language toEntity() {
        Language language = new Language();
        language.setId(id);
        language.setName(name);
        return language;
    }
}
