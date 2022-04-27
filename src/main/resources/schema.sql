create table customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name varchar(20) NOT NULL,
    email varchar(50) NOT NULL,
    created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

create table vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    customer_id BINARY(16),
    amount LONG,
    type VARCHAR(50) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);