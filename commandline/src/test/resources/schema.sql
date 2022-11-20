CREATE TABLE customers
(
    customer_id bigint(16) AUTO_INCREMENT PRIMARY KEY,
    name        varchar(20) NOT NULL,
    email       varchar(50) NOT NULL,
    created_at  datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id      bigint(16) AUTO_INCREMENT PRIMARY KEY,
    type_name       varchar(30) NOT NULL,
    discount_degree integer     NOT NULL
);