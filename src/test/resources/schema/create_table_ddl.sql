CREATE TABLE customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name        varchar(30) NOT NULL,
    email       varchar(50) NOT NULL,
    createdAt   datetime,
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id      BINARY(16) PRIMARY KEY,
    discount_amount int         NOT NULL,
    voucher_type    varchar(50) NOT NULL,
    customer_id     BINARY(16) NOT NULL,
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
);