CREATE DATABASE IF NOT EXISTS vouchers;

USE vouchers;

CREATE TABLE customers (
                           customer_id BINARY(16),
                           customer_name VARCHAR(30),
                           customer_type VARCHAR(30),
                           CONSTRAINT customer_pk PRIMARY KEY(customer_id)
);

CREATE TABLE vouchers (
                          voucher_id BINARY(16),
                          discount_value BIGINT,
                          voucher_type VARCHAR(30),
                          customer_id BINARY(16),
                          CONSTRAINT voucher_pk PRIMARY KEY(voucher_id),
                          CONSTRAINT voucher_fk FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
);

CREATE DATABASE IF NOT EXISTS vouchers_test;

USE vouchers_test;

CREATE TABLE customers (
                           customer_id BINARY(16),
                           customer_name VARCHAR(30),
                           customer_type VARCHAR(30),
                           CONSTRAINT customer_pk PRIMARY KEY(customer_id)
);

CREATE TABLE vouchers (
                          voucher_id BINARY(16),
                          discount_value BIGINT,
                          voucher_type VARCHAR(30),
                          customer_id BINARY(16),
                          CONSTRAINT voucher_pk PRIMARY KEY(voucher_id),
                          CONSTRAINT voucher_fk FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
);