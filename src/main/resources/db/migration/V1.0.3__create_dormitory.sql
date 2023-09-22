CREATE TABLE dormitories
(
    `id`                INT AUTO_INCREMENT PRIMARY KEY,
    `room_number`       VARCHAR(20)    NOT NULL,
    `address`           VARCHAR(255)   NOT NULL,
    `water_price`       DECIMAL(10, 2) NOT NULL,
    `electricity_price` DECIMAL(10, 2) NOT NULL,
    `lease_start_date`  DATE           NOT NULL,
    `lease_end_date`    DATE,
    `deleted`           BOOLEAN        NOT NULL,
    `create_time`       TIMESTAMP      NOT NULL DEFAULT current_timestamp,
    `update_time`       TIMESTAMP      NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp
);

CREATE TABLE dormitory_record
(
    `id`           INT AUTO_INCREMENT PRIMARY KEY,
    `date`         DATE           NOT NULL,
    `dormitory_id` INT            NOT NULL,
    `water`        DECIMAL(10, 2) NOT NULL,
    `electricity`  DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (`dormitory_id`) REFERENCES dormitories (id),
    UNIQUE (`dormitory_id`, `date`)
);

CREATE TABLE dormitory_occupancy
(
    `id`             INT AUTO_INCREMENT PRIMARY KEY,
    `user_id`        INT  NOT NULL,
    `dormitory_id`   INT  NOT NULL,
    `check_in_date`  DATE NOT NULL,
    `check_out_date` DATE,
    FOREIGN KEY (`user_id`) REFERENCES users (id),
    FOREIGN KEY (`dormitory_id`) REFERENCES dormitories (id)
);

CREATE TABLE dormitory_expenses
(
    `id`                                INT AUTO_INCREMENT PRIMARY KEY,
    `bill_month`                        DATE           NOT NULL,
    `dormitory_id`                      INT            NOT NULL,
    `occupants`                         INT            NOT NULL,
    `last_month_water_reading`          DECIMAL(10, 2) NOT NULL,
    `current_month_water_reading`       DECIMAL(10, 2) NOT NULL,
    `water_usage`                       DECIMAL(10, 2) NOT NULL,
    `water_price`                       DECIMAL(10, 2) NOT NULL,
    `water_cost`                        DECIMAL(10, 2) NOT NULL,
    `last_month_electricity_reading`    DECIMAL(10, 2) NOT NULL,
    `current_month_electricity_reading` DECIMAL(10, 2) NOT NULL,
    `electricity_usage`                 DECIMAL(10, 2) NOT NULL,
    `electricity_price`                 DECIMAL(10, 2) NOT NULL,
    `electricity_cost`                  DECIMAL(10, 2) NOT NULL,
    `total_cost`                        DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (`dormitory_id`) REFERENCES dormitories (id)
);

CREATE TABLE dormitory_individual_expenses
(
    `id`               INT AUTO_INCREMENT PRIMARY KEY,
    `bill_month`       DATE           NOT NULL,
    `dormitory_id`     INT            NOT NULL,
    `user_id`          INT            NOT NULL,
    `check_in_date`    DATE,
    `check_out_date`   DATE,
    `days_resided`     INT            NOT NULL,
    `subsidy`          DECIMAL(10, 2),
    `dormitory_cost`   DECIMAL(10, 2) NOT NULL,
    `dormitory_due`    DECIMAL(10, 2) NOT NULL,
    `individual_share` DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (`dormitory_id`) REFERENCES dormitories (id),
    FOREIGN KEY (`user_id`) REFERENCES users (id)
);
