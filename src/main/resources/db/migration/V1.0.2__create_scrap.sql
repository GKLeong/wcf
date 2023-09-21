CREATE TABLE scrap
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    date_recorded  DATE,
    package_number SMALLINT UNSIGNED,
    weight_kg      DECIMAL(10, 3),
    total_package  TINYINT UNSIGNED   DEFAULT 1,
    comments       VARCHAR(255)       DEFAULT NULL,
    archive        BOOL               DEFAULT FALSE,
    create_time    TIMESTAMP NOT NULL DEFAULT current_timestamp,
    update_time    TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp
);

CREATE TABLE scrap_statistics
(
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    start_date           DATE      NOT NULL,
    end_date             DATE      NOT NULL,
    total_weight_kg      DECIMAL(10, 3),
    total_package        SMALLINT UNSIGNED,
    package_weight       DECIMAL(10, 3),
    total_package_weight DECIMAL(10, 3),
    net_weight_kg        DECIMAL(10, 3),
    unit_price           DECIMAL(10, 4),
    total_price          DECIMAL(10, 4),
    comments             VARCHAR(255)       DEFAULT NULL,
    create_time          TIMESTAMP NOT NULL DEFAULT current_timestamp,
    update_time          TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp
);
