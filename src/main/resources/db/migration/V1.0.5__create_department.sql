CREATE TABLE departments
(
    id          INT          NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    create_time TIMESTAMP    NOT NULL DEFAULT current_timestamp,
    update_time TIMESTAMP    NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
    PRIMARY KEY (`id`) USING BTREE
);

ALTER TABLE users
    ADD COLUMN department_id INT DEFAULT NULL AFTER resignation_date;
