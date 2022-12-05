CREATE TABLE if NOT EXISTS customers
(
    customer_id     BINARY(16)      PRIMARY KEY NOT NULL,
    name            VARCHAR(20)     NOT NULL,
    email           VARCHAR(50)     NOT NULL,
    created_at      DATETIME(6)     NOT NULL,
    is_blocked      BOOLEAN         DEFAULT FALSE NOT NULL,
    CONSTRAINT unq_customer_email UNIQUE (email)
);

CREATE TABLE if NOT EXISTS vouchers
(
    voucher_id      BINARY(16)      PRIMARY KEY NOT NULL,
    owner_id        BINARY(16)      DEFAULT NULL,
    amount          BIGINT          NOT NULL,
    type            VARCHAR(20)     NOT NULL,
    created_at      DATETIME(6)     NOT NULL,
    expired_at      DATETIME(6)     NOT NULL,
    used            BOOLEAN         DEFAULT FALSE NOT NULL
);