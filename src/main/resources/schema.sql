CREATE DATABASE IF NOT EXISTS voucher;

use voucher;

CREATE TABLE IF NOT EXISTS vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    name       varchar(25) NOT NULL,
    discount   long        NOT NULL
)DEFAULT CHARACTER SET UTF8;

CREATE TABLE IF NOT EXISTS customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name        varchar(20) NOT NULL,
    email       varchar(50) NOT NULL
)DEFAULT CHARACTER SET UTF8;
