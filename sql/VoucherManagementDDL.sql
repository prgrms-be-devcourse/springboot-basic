CREATE DATABASE `voucher-management`;

USE `voucher-management`;

CREATE TABLE `voucher`
(
    `voucher_id`     CHAR(36)         NOT NULL,
    `discount_type`  TINYINT UNSIGNED NOT NULL,
    `discount_value` BIGINT,
    `created_at`     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`voucher_id`)
);

CREATE TABLE `user`
(
    `user_id`    CHAR(36)     NOT NULL,
    `username`   VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`)
);

CREATE TABLE `assignment`
(
    `voucher_id`      CHAR(36) NOT NULL,
    `user_id`         CHAR(36) NOT NULL,
    `assigned_time`   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `unassigned_time` TIMESTAMP,
    PRIMARY KEY (`voucher_id`, `user_id`),
    FOREIGN KEY (`voucher_id`) REFERENCES `voucher` (`voucher_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
);