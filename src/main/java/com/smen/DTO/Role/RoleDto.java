package com.smen.DTO.Role;

import com.smen.Models.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Long id;
    private String name;
    private String description;

    public static RoleDto map(Role role) {
        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());
        return dto;
    }

    public Role toEntity() {
        Role role = new Role();
        role.setId(this.id);
        role.setName(this.name);
        role.setDescription(this.description);
        return role;
    }
}