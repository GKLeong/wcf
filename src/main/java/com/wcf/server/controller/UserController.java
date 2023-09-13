package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
//    @PreAuthorize("hasRole('ADMIN')")
    public ResultBody findAll() {
        return ResultBody.success(userService.findAll());
    }

    @GetMapping("me")
    public ResultBody findMe() {
        return ResultBody.success(userService.findMe());
    }
}
