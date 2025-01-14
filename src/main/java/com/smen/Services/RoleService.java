package com.smen.Services;

import com.smen.DTO.Role.RoleDto;
import com.smen.Models.Role;
import com.smen.Repositories.IRoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService extends BaseEntityService<Role, Long> {
    private final IRoleRepository roleRepository;

    public RoleService(IRoleRepository roleRepository) {
        super(roleRepository);
        this.roleRepository = roleRepository;
    }

    // Get a role by its name as a DTO
    public Optional<RoleDto> getRoleByName(String name) {
        return roleRepository.findByName(name).map(RoleDto::map);
    }

    // Get all roles as DTOs
    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(RoleDto::map)
                .collect(Collectors.toList());
    }
}
