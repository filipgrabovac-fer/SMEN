package com.smen.Dto.User;

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
    private String team;
    private Language language;
    private Role role;

    public static UserDto map(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setTeam(user.getTeam());
        dto.setLanguage(user.getLanguage());
        dto.setRole(user.getRole());

        return dto;
    }

    public User toEntity(Language language, Role role) {
        User user = new User();
        user.setId(this.id);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setTeam(this.team);
        user.setLanguage(language);
        user.setRole(role);

        return user;
    }
}
