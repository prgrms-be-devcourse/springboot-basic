CREATE TABLE customers (
    customer_id     BINARY(16) PRIMARY KEY,
    name            VARCHAR(30) NOT NULL,
    customer_type   VARCHAR(10) NOT NULL default 'NORMAL'
);

CREATE TABLE vouchers (
    voucher_id      BINARY(16) PRIMARY KEY,
    created_at      DATETIME NOT NULL,
    discount_value  DECIMAL NOT NULL,
    voucher_type    VARCHAR(10) NOT NULL,
    customer_id     BINARY(16),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
)
