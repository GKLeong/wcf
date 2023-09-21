package com.wcf.server.service;

import com.alibaba.fastjson.JSONObject;
import com.wcf.server.base.response.BizException;
import com.wcf.server.repository.RoleRepository;
import com.wcf.server.repository.UserRepository;
import com.wcf.server.repository.UserRoleRepository;
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
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserRoleRepository userRoleRepository;

    @Autowired
    private void autowired(UserRepository userRepository,
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
                .orElseThrow(() -> new BizException("用户名不存在: " + username));
    }

    public User findMe() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findByUsername(userDetails.getUsername());
    }

    public boolean matchPassword(User user, String password) {
        return user.matchPassword(password);
    }

    public User add(JSONObject param) {
        User user = new User();
        user.setUsername(param.getString("username"));
        user.setEmail(param.getString("email"));
        user.setPassword(param.getString("password"));
        user.bCryptPassword();
        user.setName(param.getString("name"));
        user.setGender(User.Gender.valueOf(param.getString("gender")));
        user.setAddress(param.getString("address"));
        user.setIdNumber(param.getString("idNumber"));
        user.setHireDate(param.getDate("hireDate"));
        userRepository.save(user);
        return user;
    }

    public void addRole(Long userId, Integer roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new UsernameNotFoundException("角色不存在: " + roleId));
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(role.getId());
        userRole.setRoleName(role.getName());
        userRoleRepository.save(userRole);
    }
}
