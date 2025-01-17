package com.smen.DTO.Keycloak;

import com.smen.DTO.User.UserDto;
import com.smen.Models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakRegisterDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long roleId;

    public UserDto toEntity(KeycloakRegisterDTO keycloakRegisterDTO){
        UserDto user = new UserDto();
        user.setEmail(keycloakRegisterDTO.getEmail());
        user.setFirstName(keycloakRegisterDTO.getFirstName());
        user.setLastName(keycloakRegisterDTO.getLastName());
        user.setLanguageId(1L);
        user.setRoleId(keycloakRegisterDTO.getRoleId());
        return user;
    }
}
