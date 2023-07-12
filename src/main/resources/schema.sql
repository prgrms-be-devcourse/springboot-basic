DROP TABLE IF EXISTS wallet;
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

CREATE TABLE wallet
(
    id          VARCHAR(36) PRIMARY KEY,
    customer_id VARCHAR(36) NOT NULL,
    voucher_id  VARCHAR(36) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer (id),
    FOREIGN KEY (voucher_id) REFERENCES voucher (id)
)