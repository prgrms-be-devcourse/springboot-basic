SHOW DATABASES;

# voucher_mgmt_system 데이터 베이스가 없다면 실행
CREATE DATABASE voucher_mgmt_system;

USE voucher_mgmt_system;

SHOW tables;

# vouchers, customers 테이블이 존재한다면 실행
DROP TABLE vouchers;
DROP TABLE customers;

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
    voucher_id    BINARY(16) NOT NULL,
    voucher_type  ENUM('FIXED','PERCENT') DEFAULT NULL,
    amount        INTEGER DEFAULT NULL,
    percent       INTEGER DEFAULT NULL,
    owner_id      BINARY(16) DEFAULT NULL,
    CONSTRAINT pk_voucher_id PRIMARY KEY (voucher_id),
    CONSTRAINT `fk_owner_id` FOREIGN KEY (owner_id) REFERENCES customers (customer_id),
    CONSTRAINT `chk_amount` CHECK (((amount >= 100) and (amount <= 1000000))),
    CONSTRAINT `chk_percent` CHECK (((percent > 0) and (percent <= 50)))
);


