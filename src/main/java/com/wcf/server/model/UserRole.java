package com.wcf.server.model;

import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "user_roles")
public class UserRole {
    private Long userId;
    private Integer roleId;
    private Role.ERole roleName;

    public UserRole(Long userId, Integer roleId, Role.ERole roleName) {
        this.userId = userId;
        this.roleId = roleId;
        this.roleName = roleName;
    }
}
