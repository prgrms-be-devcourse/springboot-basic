CREATE TABLE customers
(
    customer_id    BINARY(16)  PRIMARY KEY,
    name           varchar(20) NOT NULL,
    gender         varchar(6)  NOT NULL,
    email          varchar(50) NOT NULL,
    created_at     datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_login_at  datetime(6)          DEFAULT NULL,
    customer_type  varchar(7)  NOT NULL DEFAULT 'GENERAL',
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id      BINARY(16)  PRIMARY KEY,
    discount_value  BIGINT      NOT NULL,
    voucher_type    varchar(7)  NOT NULL,
    owner_id        BINARY(16)  DEFAULT NULL,
    FOREIGN KEY (owner_id) references customers(customer_id)
);