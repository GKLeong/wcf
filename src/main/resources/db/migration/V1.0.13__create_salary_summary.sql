CREATE TABLE salary_summary
(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    bill_date      DATE          NOT NULL,
    user_id        INT           NOT NULL,
    payment_method VARCHAR(50),
    account_number VARCHAR(50),
    labor_days     SMALLINT UNSIGNED,
    base_salary    DECIMAL(8, 0) NOT NULL DEFAULT 0,
    piece_salary   DECIMAL(8, 0) NOT NULL DEFAULT 0,
    allowance      DECIMAL(8, 0) NOT NULL DEFAULT 0,
    deduction      DECIMAL(8, 0) NOT NULL DEFAULT 0,
    amount         DECIMAL(8, 0) NOT NULL DEFAULT 0,
    paid           DECIMAL(8, 0) NOT NULL DEFAULT 0,
    finish         BOOLEAN,
    notes          VARCHAR(255),
    operator_id    INT,
    operator       VARCHAR(255),
    create_time    TIMESTAMP              DEFAULT CURRENT_TIMESTAMP,
    update_time    TIMESTAMP              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);
