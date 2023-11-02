USE voucher_management;

DROP TABLE IF EXISTS customer;
CREATE TABLE IF NOT EXISTS customer (
    customer_id BINARY(16) PRIMARY KEY,
    name VARCHAR(255),
    customer_type ENUM('NORMAL', 'BLACK')
);

DROP TABLE IF EXISTS voucher;
CREATE TABLE IF NOT EXISTS voucher (
    voucher_id BINARY(16) PRIMARY KEY,
    discount BIGINT,
    voucher_type ENUM('FIXED', 'PERCENT'),
    created_at datetime(6)
);

DROP TABLE IF EXISTS wallet;
CREATE TABLE IF NOT EXISTS wallet (
    wallet_id BINARY(16) PRIMARY KEY,
    customer_id BINARY(16),
    voucher_id BINARY(16)
);