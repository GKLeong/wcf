CREATE TABLE labor_cost
(
    id             INT           NOT NULL AUTO_INCREMENT,
    department_id  INT           NOT NULL,
    action         VARCHAR(255)  NOT NULL,
    price          DECIMAL(6, 4) NOT NULL,
    comments       VARCHAR(255) DEFAULT NULL,
    effective_date DATE,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY unique_department_action_effective_date (department_id, action, effective_date),
    FOREIGN KEY (department_id) REFERENCES departments (id)
);

CREATE TABLE labor_data
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    order_id      INT,
    product_id    INT,
    product_name  VARCHAR(255),
    date          DATE,
    labor_cost_id INT           NOT NULL,
    department_id INT           NOT NULL,
    action        VARCHAR(255)  NOT NULL,
    quantity      DECIMAL(8, 0) NOT NULL,
    frequency     DECIMAL(1, 0) NOT NULL,
    unit_price    DECIMAL(6, 4) NOT NULL,
    amount        DECIMAL(8, 2) NOT NULL,
    notes         TEXT,
    user_id       INT,
    producer      VARCHAR(255),
    card_group    VARCHAR(255),
    card_number   VARCHAR(255),
    archive_date  DATE,
    FOREIGN KEY (department_id) REFERENCES departments (id)
);
