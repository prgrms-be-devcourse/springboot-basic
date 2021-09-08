CREATE TABLE customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name varchar(20) NOT NULL,
    email varchar(50) NOT NULL,
    last_login_at datetime DEFAULT NULL,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    black tinyint(1) DEFAULT 0,
    CONSTRAINT unq_user_email UNIQUE (email)
);

create table vouchers
(
    voucher_id binary(16) not null
        primary key,
    type       int        not null,
    amount     int        not null,
    created_at datetime   null,
    expired_at datetime   null
);
