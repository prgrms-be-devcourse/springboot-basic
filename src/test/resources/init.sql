CREATE TABLE vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    discount_information INT(20) NOT NULL,
    voucher_type varchar(20) NOT NULL,
    owner_id BINARY(16) DEFAULT NULL
);

CREATE TABLE customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name varchar(20) NOT NULL,
    email varchar(50) NOT NULL,
    CONSTRAINT unq_user_email UNIQUE (email)
);