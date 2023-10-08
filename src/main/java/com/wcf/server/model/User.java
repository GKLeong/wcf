package com.wcf.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wcf.server.utils.PasswordUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 20)
    private String username;
    @Size(max = 50)
    @Email
    private String email;
    @NotBlank
    @Size(min = 6)
    @JsonIgnore
    private String password;
    @NotBlank
    @Size(min = 2)
    private String name;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String address;
    private String idNumber;
    private Date hireDate;
    private Date resignationDate;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    private Long departmentId;
    @Transient
    private String department;
    @JsonIgnore
    private Boolean deleted = false;
    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;
    @Column(name = "update_time", insertable = false, updatable = false)
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public enum Gender {
        Male("男"), Female("女"), Other("其他");

        private final String name;

        Gender(String gender) {
            this.name = gender;
        }

        public String getName() {
            return name;
        }
    }

    public void bCryptPassword() {
        setPassword(new PasswordUtils().passwordEncoder().encode(this.password));
    }

    public boolean matchPassword(String password) {
        return new PasswordUtils().passwordEncoder().matches(password, this.password);
    }

}
