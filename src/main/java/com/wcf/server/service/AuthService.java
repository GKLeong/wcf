package com.wcf.server.service;

import com.wcf.server.base.jwt.JwtUtils;
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
        User user = userService.findByUsername(username);
        if (user == null || !userService.matchPassword(user, password)) return null;

        HashMap<String, String> result = new HashMap<String, String>();
        result.put("token", jwtUtils.generateJwtToken(user.getUsername()));
        return result;
    }
}
