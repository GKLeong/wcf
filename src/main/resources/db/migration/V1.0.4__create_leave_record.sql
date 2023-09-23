CREATE TABLE leave_records
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT       NOT NULL,
    start_date  DATE      NOT NULL,
    end_date    DATE,
    duration    INT       NOT NULL,
    reason      TEXT,
    create_time TIMESTAMP NOT NULL DEFAULT current_timestamp,
    update_time TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
    FOREIGN KEY (user_id) REFERENCES users (id)
);