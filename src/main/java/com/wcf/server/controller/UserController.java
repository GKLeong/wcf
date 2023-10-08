package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.UserService;
import com.wcf.server.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
//    @PreAuthorize("hasRole('ADMIN')")
    public ResultBody findAll() {
        return ResultBody.success(userService.findAllByDeletedIsFalse());
    }

    @GetMapping("me")
    public ResultBody findMe() {
        return ResultBody.success(userService.findMe());
    }

    @PutMapping("/{id}")
    public ResultBody update(@PathVariable Long id,
                             @RequestParam(value = "email", required = false) String email,
                             @RequestParam(value = "password", required = false) String password,
                             @RequestParam("name") String name,
                             @RequestParam("gender") String gender,
                             @RequestParam(value = "address", required = false) String address,
                             @RequestParam(value = "idNumber", required = false) String idNumber,
                             @RequestParam(value = "hireDate", required = false) String hireDate,
                             @RequestParam(value = "resignationDate", required = false) String resignationDate) {
        userService.update(id, email, password, name, gender, address, idNumber, DateUtils.dateFormat(hireDate), DateUtils.dateFormat(resignationDate));
        return ResultBody.success();
    }

    @PutMapping("/{id}/department")
    public ResultBody updateDepartment(@PathVariable Long id,
                                       @RequestParam Long departmentId) {
        userService.setDepartment(id, departmentId);
        return ResultBody.success();
    }
}
