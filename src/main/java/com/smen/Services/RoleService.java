package com.smen.Services;

import com.smen.Models.Role;
import com.smen.Repositories.IRoleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
public class RoleService extends BaseEntityService<Role, Long> {
    private final IRoleRepository roleRepository;

    public RoleService(IRoleRepository roleRepository) {
        super(roleRepository);
        this.roleRepository = roleRepository;
    }

    // Get a role by its name
    public Optional<Role> getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

}
