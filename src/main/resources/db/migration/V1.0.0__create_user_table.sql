CREATE TABLE `users`
(
    `id`               INT                              NOT NULL AUTO_INCREMENT,
    `username`         VARCHAR(255) UNIQUE              NOT NULL COMMENT '用户名',
    `email`            VARCHAR(255)                     NULL COMMENT '邮箱',
    `password`         VARCHAR(255)                     NOT NULL COMMENT '密码',
    `name`             VARCHAR(255)                     NOT NULL COMMENT '姓名',
    `gender`           ENUM ('Male', 'Female', 'Other') NOT NULL COMMENT '性别',
    `address`          VARCHAR(255)                     NULL COMMENT '住址',
    `id_number`        VARCHAR(20)                      NULL COMMENT '身份证',
    `hire_date`        DATE                             NULL COMMENT '入职时间',
    `resignation_date` DATE                             NULL COMMENT '离职时间',
    `deleted`          BOOL                             NOT NULL DEFAULT 0,
    `create_time`      TIMESTAMP                        NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`      TIMESTAMP                        NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `index_username` (`username`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1;

CREATE TABLE `roles`
(
    `id`   INT(11)     NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(20) NOT NULL COMMENT '角色',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1;

INSERT INTO `roles` (`name`)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');

CREATE TABLE `user_roles`
(
    `user_id`   INT(11)     NOT NULL COMMENT '用户ID',
    `role_id`   INT(11)     NOT NULL COMMENT '角色ID',
    `role_name` VARCHAR(20) NOT NULL COMMENT '角色',
    KEY `index_user_id` (`user_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1;
