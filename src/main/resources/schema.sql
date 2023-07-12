use order_mgmt;
CREATE TABLE vouchers
(
    voucher_id   BINARY(16) PRIMARY KEY,
    amount       bigint      NOT NULL,
    voucher_type VARCHAR(50) NOT NULL
);

CREATE TABLE my_customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    total_fund  bigint DEFAULT 0
)