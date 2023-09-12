package com.wcf.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.wcf.server.model.User;
import com.wcf.server.service.AuthService;
import com.wcf.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @Autowired
    AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("register")
    public User add(@RequestBody User user) {
        user = userService.add(user);
        return user;
    }

    @PostMapping("login")
    public HashMap<String, String> login(@RequestBody JSONObject param) {
        return authService.getToken(param.getString("username"), param.getString("password"));
    }
}
