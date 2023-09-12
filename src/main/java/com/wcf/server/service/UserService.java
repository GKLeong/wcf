package com.wcf.server.service;

import com.wcf.server.dao.RoleRepository;
import com.wcf.server.dao.UserRepository;
import com.wcf.server.dao.UserRoleRepository;
import com.wcf.server.model.Role;
import com.wcf.server.model.User;
import com.wcf.server.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    UserService(UserRepository userRepository,
                RoleRepository roleRepository,
                UserRoleRepository userRoleRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(long id) {
        return userRepository.findById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户名不存在: " + username));
    }

    public User findMe() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findByUsername(userDetails.getUsername());
    }

    public boolean matchPassword(User user, String password) {
        return user.matchPassword(password);
    }

    public User add(User user) {
        user.bCryptPassword();
        user.setRoles(null);
        user.setDeleted(false);
        userRepository.save(user);
        return user;
    }

    public void addRole(Long userId, Integer roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new UsernameNotFoundException("角色不存在: " + roleId));
        UserRole userRole = new UserRole(userId, role.getId(), role.getName());
        userRoleRepository.save(userRole);
    }
}
