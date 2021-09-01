CREATE TABLE customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name varchar(20) NOT NULL,
    email varchar(50) NOT NULL,
    last_login_at datetime(6) DEFAULT NULL,
    created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE voucher
(
    voucher_id BINARY(16) PRIMARY KEY,
    voucher_type varchar(20) DEFAULT NULL,
    discount Long NOT NULL,
    created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);

CREATE TABLE customer_voucher
(
    customer_voucher_id BINARY(16) PRIMARY KEY,
    customer_id BINARY(16) not null,
    voucher_id BINARY(16) not null ,
    CONSTRAINT unq_voucher_id UNIQUE (voucher_id),
    created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    foreign key (customer_id) REFERENCES customers(customer_id),
    foreign key (voucher_id) REFERENCES voucher(voucher_id)
);
