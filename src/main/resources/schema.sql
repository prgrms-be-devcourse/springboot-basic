USE voucher_mgmt_system;

SHOW tables;

DROP TABLE vouchers;
DROP TABLE customers;

CREATE TABLE `customers` (
     `customer_id`  binary(16) NOT NULL,
     `name`         varchar(20) NOT NULL,
     `email`        varchar(50) NOT NULL,
     `created_at`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
     PRIMARY KEY (`customer_id`),
     UNIQUE KEY `unq_user_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO customers(customer_id, name, email) VALUES (UUID_TO_BIN(UUID()), 'test00', 'test00@gmail.com');
INSERT INTO customers(customer_id, name, email) VALUES (UUID_TO_BIN(UUID()), 'test01', 'test01@gmail.com');
INSERT INTO customers(customer_id, name, email) VALUES (UUID_TO_BIN(UUID()), 'test02', 'test02@gmail.com');

CREATE TABLE `vouchers` (
    `voucher_id`    binary(16) NOT NULL,
    `voucher_type`  enum('FIXED','PERCENT') DEFAULT NULL,
    `amount`        int DEFAULT NULL,
    `percent`       int DEFAULT NULL,
    `owner_id`      binary(16) DEFAULT NULL,
    PRIMARY KEY (`voucher_id`),
    KEY `fk_owner_id` (`owner_id`),
    CONSTRAINT `fk_owner_id` FOREIGN KEY (`owner_id`) REFERENCES `customers` (`customer_id`),
    CONSTRAINT `chk_amount` CHECK (((`amount` >= 100) and (`amount` <= 1000000))),
    CONSTRAINT `chk_percent` CHECK (((`percent` > 0) and (`percent` <= 50)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


