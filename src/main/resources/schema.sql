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
    customer_id BINARY(16),
    type ENUM('fixed', 'percent') NOT NULL,
    amount LONG,
    created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);