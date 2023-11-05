DROP TABLE IF EXISTS customer;
CREATE TABLE customer
(
    customer_id varchar(36) PRIMARY KEY,
    name varchar(20) NOT NULL,
    type varchar(1) NOT NULL
);

DROP TABLE IF EXISTS voucher;
CREATE TABLE voucher(
    voucher_id varchar(36) PRIMARY KEY,
    amount bigint NOT NULL,
    type varchar(10) NOT NULL
);


DROP TABLE IF EXISTS wallet;
CREATE TABLE wallet(
    customer_id varchar(36),
    voucher_id varchar(36),
    PRIMARY KEY (customer_id, voucher_id)
);



