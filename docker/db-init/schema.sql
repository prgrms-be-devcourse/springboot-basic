DROP TABLE IF EXISTS customers;
CREATE TABLE customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name        varchar(20) NOT NULL,
    email       varchar(50) NOT NULL,
    create_at   datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6),
    CONSTRAINT unq_user_email UNIQUE (email)
);



DROP TABLE IF EXISTS vouchers;
CREATE TABLE vouchers
(
    voucher_id        BINARY(16) PRIMARY KEY,
    amount            bigint NOT NULL,
    discount          enum('FIXED', 'PERCENT') NOT NULL,
    registration_date datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6),
    expiration_date   datetime(6) NOT NULL
);

