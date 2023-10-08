CREATE TABLE labor_cost
(
    id             INT            NOT NULL AUTO_INCREMENT,
    department_id  INT            NOT NULL,
    action         VARCHAR(255)   NOT NULL,
    price          DECIMAL(10, 4) NOT NULL,
    comments       VARCHAR(255) DEFAULT NULL,
    effective_date DATE,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY unique_department_action_effective_date (department_id, action, effective_date),
    FOREIGN KEY (department_id) REFERENCES departments (id)
);