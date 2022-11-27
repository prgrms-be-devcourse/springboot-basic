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
    customer_id         BIGINT    not null,
    voucher_id          BIGINT    not null,
    FOREIGN KEY (customer_id) REFERENCES CUSTOMER (customer_id),
    FOREIGN KEY (voucher_id) REFERENCES VOUCHER (voucher_id)
);

