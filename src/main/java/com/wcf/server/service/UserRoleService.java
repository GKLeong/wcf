package com.wcf.server.service;

import com.wcf.server.base.response.BizException;
import com.wcf.server.base.response.CommonEnum;
import com.wcf.server.model.Role;
import com.wcf.server.model.User;
import com.wcf.server.repository.UserRepository;
import com.wcf.server.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserRoleService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(UserRepository userRepository,
                           RoleService roleService,
                           UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userRoleRepository = userRoleRepository;
    }

    public void joinRole(User user, Role.ERole name) {
        Set<Role> newRoles = new HashSet<>(user.getRoles());
        Role role = roleService.findByName(name);
        if (role == null) return;

        newRoles.add(role);
        user.setRoles(newRoles);
        userRepository.save(user);
    }

    public boolean haveAdminUser() {
        Role role = roleService.findByName(Role.ERole.ROLE_ADMIN);
        if (role == null) throw new BizException(CommonEnum.NOT_FOUND_ADMIN_ROLE);

        return userRoleRepository.countByRoleId(role.getId()) > 0;
    }
}
