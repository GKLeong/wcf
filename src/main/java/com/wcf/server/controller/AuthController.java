package com.wcf.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.wcf.server.model.User;
import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.AuthService;
import com.wcf.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private UserService userService;
    private AuthService authService;

    @Autowired
    private void autowired(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("register")
    public ResultBody add(@RequestBody User user) {
        return ResultBody.success(userService.add(user));
    }

    @PostMapping("login")
    public ResultBody login(@RequestBody JSONObject param) {
        return ResultBody.success(authService.getToken(param.getString("username"), param.getString("password")));
    }
}
