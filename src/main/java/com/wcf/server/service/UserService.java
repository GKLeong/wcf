package com.wcf.server.service;

import com.wcf.server.base.response.BizException;
import com.wcf.server.base.response.CommonEnum;
import com.wcf.server.repository.UserRepository;
import com.wcf.server.model.Role;
import com.wcf.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleService userRoleService;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
    }

    public List<User> findAllByDeletedIsFalse() {
        return userRepository.findAllByDeletedIsFalse();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByIdAndDeletedIsFalse(long id) {
        return userRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new BizException("用户id不存在: " + id));
    }

    public User findByUsernameAndDeletedIsFalse(String username) {
        return userRepository.findByUsernameAndDeletedIsFalse(username)
                .orElseThrow(() -> new BizException("用户名不存在: " + username));
    }

    public User findMe() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findByUsernameAndDeletedIsFalse(userDetails.getUsername());
    }

    public boolean matchPassword(User user, String password) {
        return user.matchPassword(password);
    }

    public User add(String username, String email, String password, String name, String gender, String address, String idNumber, Date hireDate) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.bCryptPassword();
        user.setName(name);
        user.setGender(User.Gender.valueOf(gender));
        user.setAddress(address);
        user.setIdNumber(idNumber);
        user.setHireDate(hireDate);
        user = userRepository.save(user);

        userRoleService.joinRole(user, Role.ERole.ROLE_USER);
        return user;
    }

    public void update(Long id, String email, String password, String name, String gender, String address, String idNumber, Date hireDate, Date resignationDate) {
        User user = findByIdAndDeletedIsFalse(id);
        user.setEmail(email);
        if (password != null && !password.isBlank()) {
            user.setPassword(password);
            user.bCryptPassword();
        }
        user.setName(name);
        user.setGender(User.Gender.valueOf(gender));
        user.setAddress(address);
        user.setIdNumber(idNumber);
        user.setHireDate(hireDate);
        user.setResignationDate(resignationDate);
        userRepository.save(user);
    }

    public void addFirstAdmin() {
        if (userRoleService.haveAdminUser()) throw new BizException(CommonEnum.NOT_FOUND_API);
        User user = new User();
        user.setUsername("wcf");
        user.setPassword("wcf6888888");
        user.bCryptPassword();
        user.setName("wcf");
        user.setGender(User.Gender.Other);
        user = userRepository.save(user);
        userRoleService.joinRole(user, Role.ERole.ROLE_ADMIN);
    }

    public void setDepartment(Long userId, Long departmentId) {
        User user = findByIdAndDeletedIsFalse(userId);
        user.setDepartmentId(departmentId);
        userRepository.save(user);
    }
}
