CREATE TABLE customers
(
    id    BINARY(16) PRIMARY KEY,
    name  varchar(20) NOT NULL,
    black boolean     not null,
    CONSTRAINT name UNIQUE (name)
);

CREATE TABLE vouchers
(
    id             BINARY(16) PRIMARY KEY,
    type           enum ('FIXED', 'PERCENT') NOT NULL,
    discount_value varchar(50)               NOT NULL,
    created_at     datetime(6) DEFAULT CURRENT_TIMESTAMP(6)
);

CREATE TABLE ownership
(
    voucher_id  BINARY(16) PRIMARY KEY NOT NULL REFERENCES vouchers (id) ON DELETE CASCADE,
    customer_id BINARY(16)             NOT NULL REFERENCES customers (id) ON DELETE CASCADE
);