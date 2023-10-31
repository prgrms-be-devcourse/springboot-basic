CREATE DATABASE IF NOT EXISTS voucher_mgmt_system;

USE voucher_mgmt_system;

DROP TABLE IF EXISTS vouchers;
DROP TABLE IF EXISTS customers;

CREATE TABLE customers (
     customer_id  BINARY(16) NOT NULL,
     name         VARCHAR(20) NOT NULL,
     email        VARCHAR(50) NOT NULL,
     created_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
     CONSTRAINT pk_customer_id PRIMARY KEY (customer_id),
     CONSTRAINT unq_customer_email UNIQUE KEY (email)
);

# 테스트 데이터 삽입
INSERT INTO customers(customer_id, name, email) VALUES (UUID_TO_BIN(UUID()), '정의진', '정의진@gmail.com');
INSERT INTO customers(customer_id, name, email) VALUES (UUID_TO_BIN(UUID()), '김현우', '김현우@gmail.com');
INSERT INTO customers(customer_id, name, email) VALUES (UUID_TO_BIN(UUID()), '이세희', '이세희@gmail.com');

CREATE TABLE `vouchers` (
    voucher_id      BIGINT NOT NULL,
    voucher_type    ENUM('FIXED','PERCENT') NOT NULL,
    discount_value  INTEGER NOT NULL,
    code            BINARY(16) NOT NULL,
    name            VARCHAR(50) DEFAULT NULL,
    CONSTRAINT pk_voucher_id PRIMARY KEY (voucher_id),
    CONSTRAINT unq_code UNIQUE KEY (code)
);


