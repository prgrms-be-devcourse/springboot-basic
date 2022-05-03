DROP VIEW  IF EXISTS wallets;

DROP TABLE IF EXISTS customers, vouchers;

CREATE TABLE customers
(
    customer_id BINARY(16) PRIMARY KEY,
    customer_type VARCHAR(20) NOT NULL,
    name VARCHAR(20) NOT NULL,
    created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    modified_at DATETIME(6) DEFAULT NULL
);

CREATE TABLE vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    voucher_type VARCHAR(20) NOT NULL,
    discount_info BIGINT(10) NOT NULL,
    created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    modified_at DATETIME(6) DEFAULT NULL,
    customer_id BINARY(16),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE VIEW wallets as
SELECT c.customer_id, customer_type, name, v.voucher_id, v.voucher_type, v.discount_info, v.created_at
FROM customers c,
     vouchers v
WHERE c.customer_id = v.customer_id;