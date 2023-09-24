package com.wcf.server.service;

import com.wcf.server.model.Role;
import com.wcf.server.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(Role.ERole name) {
        return roleRepository.findByName(name).orElse(null);
    }
}
