CREATE TABLE IF NOT EXISTS customers (
    customer_id UUID PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    last_login_at TIMESTAMP,
    created_at TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS vouchers (
    voucher_id UUID PRIMARY KEY,
    name VARCHAR(255),
    voucher_value BIGINT,
    voucher_type VARCHAR(255)
    );


