DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS voucher;

CREATE TABLE customer
(
    id     VARCHAR(36) PRIMARY KEY,
    status VARCHAR(32) DEFAULT 'normal'
);

CREATE TABLE voucher
(
    id     varchar(36) PRIMARY KEY,
    amount DECIMAL default 0,
    type   VARCHAR(32) not null
);