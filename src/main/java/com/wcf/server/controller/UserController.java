package com.wcf.server.controller;

import com.wcf.server.model.User;
import com.wcf.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("me")
    public User findMe(){
        return userService.findMe();
    }
}
