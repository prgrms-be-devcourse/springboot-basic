CREATE TABLE vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    name       varchar(25) NOT NULL,
    discount   long        NOT NULL
);

CREATE TABLE customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name        varchar(20) NOT NULL,
    email       varchar(50) NOT NULL
);
