CREATE TABLE customers
(
    id   BINARY(16) PRIMARY KEY,
    name varchar(20) NOT NULL,
    CONSTRAINT name UNIQUE (name)
);

CREATE TABLE vouchers
(
    id             BINARY(16) PRIMARY KEY,
    type           enum ('FIXED', 'PERCENT') NOT NULL,
    discount_value varchar(50)               NOT NULL,
    created_at     datetime(6) DEFAULT CURRENT_TIMESTAMP(6)
);

CREATE TABLE voucher_customer_own_info
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    customer_id BINARY(16) NOT NULL REFERENCES customers (id) ON DELETE CASCADE,
    voucher_id  BINARY(16) NOT NULL REFERENCES vouchers (id) ON DELETE CASCADE
);