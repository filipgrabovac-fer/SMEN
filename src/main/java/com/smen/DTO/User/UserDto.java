package com.smen.DTO.User;

import com.smen.Models.Language;
import com.smen.Models.Role;
import com.smen.Models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long languageId;
    private Long roleId;
    private String username;

    public static UserDto map(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setLanguageId(user.getLanguageId());
        dto.setRoleId(user.getRoleId());
        dto.setUsername(user.getUsername());
        return dto;
    }

    public User toEntity(Language language, Role role) {
        User user = new User();
        user.setId(this.id);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setLanguageId(language.getId());
        user.setRoleId(role.getId());
        user.setUsername(this.username);
        return user;
    }
}
