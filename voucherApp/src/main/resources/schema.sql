CREATE TABLE vouchers
(
    voucher_id          BINARY(16) PRIMARY KEY,
    discount_policy      varchar(50) NOT NULL,
    discount_amount     BIGINT NOT NULL,
    UNIQUE KEY unq_policy_id_and_amount (discount_policy, discount_amount)
);

CREATE TABLE customers
(
    customer_id   BINARY(16) PRIMARY KEY,
    voucher_id    BINARY(16) DEFAULT NULL,
    name          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    last_login_at datetime(6)             DEFAULT NULL,
    created_at    datetime(6)    NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email),
    FOREIGN KEY (voucher_id) REFERENCES vouchers(voucher_id)
);