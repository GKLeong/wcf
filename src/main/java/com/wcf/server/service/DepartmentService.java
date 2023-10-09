package com.wcf.server.service;

import com.wcf.server.base.response.BizException;
import com.wcf.server.model.Department;
import com.wcf.server.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public Department findById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new BizException("部门id不存在: " + id));
    }

    public Department findByName(String name) {
        return departmentRepository.findByName(name)
                .orElseThrow(() -> new BizException("部门不存在: " + name));
    }

    public Department add(String name) {
        Department department = new Department();
        department.setName(name);
        return departmentRepository.save(department);
    }

    public Department update(Long id, String name) {
        Department department = findById(id);
        department.setName(name);
        return departmentRepository.save(department);
    }
}
