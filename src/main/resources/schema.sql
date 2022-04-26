CREATE TABLE customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name varchar(20) NOT NULL,
    email varchar(50) NOT NULL,
    last_login_at datetime(6) DEFAULT NULL,
    created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    voucher_type ENUM('FIXED_AMOUNT', 'PERCENT_DISCOUNT'),
    discount_amount INT NOT NULL,
    voucher_owner BINARY(16) DEFAULT NULL,
    is_issued BOOLEAN DEFAULT FALSE,
    created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    issued_at datetime(6) DEFAULT NULL,
    CONSTRAINT fk_customers foreign key (voucher_owner) references customers (customer_id)
);