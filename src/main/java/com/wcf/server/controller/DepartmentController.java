package com.wcf.server.controller;

import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResultBody findAll() {
        return ResultBody.success(departmentService.findAll());
    }

    @PostMapping
    public ResultBody add(@RequestParam String name) {
        return ResultBody.success(departmentService.add(name));
    }

    @PutMapping("/{id}")
    public ResultBody update(@PathVariable Long id,
                             @RequestParam String name) {
        return ResultBody.success(departmentService.update(id, name));
    }
}
