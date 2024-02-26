package com.wcf.server.service;

import com.wcf.server.base.jwt.JwtUtils;
import com.wcf.server.base.response.BizException;
import com.wcf.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthService(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    public HashMap<String, String> getToken(String username, String password) {
        User user = userService.findByUsernameAndDeletedIsFalse(username);
        if (user == null || !userService.matchPassword(user, password)) {
            throw new BizException("404", "账户不存在或者密码错误");
        }

        HashMap<String, String> result = new HashMap<>();
        result.put("token", jwtUtils.generateJwtToken(user.getUsername()));
        return result;
    }
}
