CREATE TABLE customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name varchar(20) NOT NULL,
    email varchar(50) NOT NULL,
    created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_name UNIQUE (name)
);

CREATE TABLE vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    customer_id BINARY(16),
    voucher_type enum('FIXED', 'PERCENT') NOT NULL,
    discount_value BIGINT NOT NULL,
    created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    FOREIGN KEY (customer_id) REFERENCES customers (customer_id) ON UPDATE CASCADE ON DELETE RESTRICT
);

drop table vouchers;
drop table customers;

