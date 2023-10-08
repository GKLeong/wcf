package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.AuthService;
import com.wcf.server.service.UserService;
import com.wcf.server.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("register")
    // public ResultBody add(@RequestBody JSONObject param) {
    public ResultBody add(@RequestParam("username") String username,
                          @RequestParam(value = "email", required = false) String email,
                          @RequestParam("password") String password,
                          @RequestParam("name") String name,
                          @RequestParam(value = "gender", required = false, defaultValue = "Other") String gender,
                          @RequestParam(value = "address", required = false) String address,
                          @RequestParam(value = "idNumber", required = false) String idNumber,
                          @RequestParam(value = "hireDate", required = false) String hireDate) {
        return ResultBody.success(userService.add(username, email, password, name, gender, address, idNumber, DateUtils.dateFormat(hireDate)));
    }

    @PostMapping("login")
    public ResultBody login(@RequestParam("username") String username,
                            @RequestParam("password") String password) {
        return ResultBody.success(authService.getToken(username, password));
    }

    @PostMapping("addFirstAdmin")
    public ResultBody addFirstAdmin() {
        userService.addFirstAdmin();
        return ResultBody.success();
    }
}
