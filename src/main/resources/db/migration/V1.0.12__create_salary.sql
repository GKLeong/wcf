CREATE TABLE salary
(
    id                  INT PRIMARY KEY AUTO_INCREMENT,
    bill_date           DATE                         NOT NULL,
    user_id             INT                          NOT NULL,
    salary_config_id    INT,
    name                VARCHAR(255)                 NOT NULL,
    type                ENUM ('increase','decrease') NOT NULL,
    cycle               TINYINT,
    cycle_unit          ENUM ('day','month','year'),
    is_daily_conversion BOOLEAN,
    labor_days          SMALLINT UNSIGNED,
    is_real_time        BOOLEAN,
    amount              DECIMAL(7, 0)                NOT NULL,
    notes               VARCHAR(255),
    operator_id         INT,
    operator            VARCHAR(255)                 NOT NULL,
    create_time         TIMESTAMP                    NOT NULL DEFAULT current_timestamp,
    update_time         TIMESTAMP                    NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (salary_config_id) REFERENCES salary_config (id)
);
