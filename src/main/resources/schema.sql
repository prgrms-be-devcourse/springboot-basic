drop table if exists customer;
drop table if exists voucher;

CREATE TABLE customer
(
    customer_id char(36) PRIMARY KEY,
    name        varchar(20) NOT NULL,
    email       varchar(50) NOT NULL,
    CONSTRAINT unq_user_email UNIQUE (email)
);


CREATE TABLE voucher
(
    voucher_id        char(36) PRIMARY KEY,
    voucher_type      varchar(20) NOT NULL,
    amount_or_percent int         NOT NULL
);
