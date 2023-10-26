CREATE TABLE customers (
    customer_id     BINARY(16) PRIMARY KEY,
    name            VARCHAR(20) NOT NULL,
    customer_type   VARCHAR(10) NOT NULL default "NORMAL"
);

CREATE TABLE vouchers (
    voucher_id      BINARY(16) PRIMARY KEY,
    discount_value  DECIMAL NOT NULL,
    voucher_type    VARCHAR(10) NOT NULL
)
