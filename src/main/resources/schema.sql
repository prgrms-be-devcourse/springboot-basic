create database spb_basic;
use spb_basic;

DROP TABLE CUSTOMER_VOUCHER;
DROP TABLE CUSTOMER;
DROP TABLE VOUCHER;

CREATE TABLE CUSTOMER
(
    customer_id     BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    customer_status varchar(20) not null
);

CREATE TABLE VOUCHER
(
    voucher_id             BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    voucher_type           VARCHAR(20) not null,
    voucher_discount_value INT         not null
);

CREATE TABLE CUSTOMER_VOUCHER
(
    customer_voucher_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    customer_id         BIGINT not null,
    voucher_id          BIGINT not null,
    FOREIGN KEY (customer_id) REFERENCES spb_basic.CUSTOMER (customer_id),
    FOREIGN KEY (voucher_id) REFERENCES spb_basic.VOUCHER (voucher_id)
);

ALTER TABLE CUSTOMER_VOUCHER ADD UNIQUE (customer_id, voucher_id);

INSERT INTO CUSTOMER(customer_status) VALUES ("normal");
INSERT INTO VOUCHER(voucher_type, voucher_discount_value) VALUES ("fixed", 100);
INSERT INTO VOUCHER(voucher_type, voucher_discount_value) VALUES ("fixed", 100);
INSERT INTO VOUCHER(voucher_type, voucher_discount_value) VALUES ("fixed", 100);
INSERT INTO CUSTOMER_VOUCHER(customer_id, voucher_id) VALUES (1, 1);
INSERT INTO CUSTOMER_VOUCHER(customer_id, voucher_id) VALUES (1, 2);
INSERT INTO CUSTOMER_VOUCHER(customer_id, voucher_id) VALUES (1, 3);