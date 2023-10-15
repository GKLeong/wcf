CREATE TABLE salary_config
(
    id                  INT PRIMARY KEY AUTO_INCREMENT,
    user_id             INT                          NOT NULL,
    name                VARCHAR(255)                 NOT NULL,
    type                ENUM ('increase','decrease') NOT NULL,
    cycle               TINYINT                      NOT NULL,
    cycle_unit          ENUM ('day','month','year')  NOT NULL,
    amount              DECIMAL(7, 0)                NOT NULL,
    is_daily_conversion BOOLEAN,
    is_real_time        BOOLEAN,
    is_effective        BOOLEAN,
    effective_date      DATE,
    notes               VARCHAR(255),
    create_time         TIMESTAMP                    NOT NULL DEFAULT current_timestamp,
    update_time         TIMESTAMP                    NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
    FOREIGN KEY (user_id) REFERENCES users (id)
);
