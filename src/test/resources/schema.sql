drop table if exists customers CASCADE;
CREATE TABLE customers
(
    customer_id varchar(36) PRIMARY KEY,
    customer_name varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    created_at    datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

drop table if exists vouchers CASCADE;
CREATE TABLE vouchers
(
    voucher_id      varchar(36) PRIMARY KEY,
    voucher_type    varchar(20) NOT NULL,
    discount_amount int NOT NULL
);
--