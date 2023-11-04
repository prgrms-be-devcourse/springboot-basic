DROP DATABASE IF EXISTS voucher_mgmt_system;
DROP DATABASE IF EXISTS test_voucher_mgmt_system;

CREATE DATABASE voucher_mgmt_system;
CREATE DATABASE test_voucher_mgmt_system;

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

CREATE TABLE `vouchers` (
                            voucher_id      BIGINT NOT NULL,
                            voucher_type    ENUM('FIXED','PERCENT') NOT NULL,
                            discount_value  INTEGER NOT NULL,
                            code            BINARY(16) NOT NULL,
                            name            VARCHAR(50) DEFAULT NULL,
                            CONSTRAINT pk_voucher_id PRIMARY KEY (voucher_id),
                            CONSTRAINT unq_code UNIQUE KEY (code)
);

USE test_voucher_mgmt_system;

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

CREATE TABLE `vouchers` (
    voucher_id      BIGINT NOT NULL,
    voucher_type    ENUM('FIXED','PERCENT') NOT NULL,
    discount_value  INTEGER NOT NULL,
    code            BINARY(16) NOT NULL,
    name            VARCHAR(50) DEFAULT NULL,
    CONSTRAINT pk_voucher_id PRIMARY KEY (voucher_id),
    CONSTRAINT unq_code UNIQUE KEY (code)
);
