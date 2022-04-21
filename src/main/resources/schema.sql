drop table customers;

CREATE TABLE customers
(
    customer_id   BINARY(16) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    last_login_at datetime(6)          DEFAULT NULL,
    created_at    datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

insert into customers(customer_id, name, email)
values (UUID_TO_BIN(UUID()), 'tester01', 'test00@email.com');
insert into customers(customer_id, name, email)
values (UUID_TO_BIN(UUID()), 'tester02', 'test01@email.com');
insert into customers(customer_id, name, email)
values (UUID_TO_BIN(UUID()), 'tester03', 'test02@email.com');

select *
from customers;

use order_mgmt;
CREATE TABLE vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    type       varchar(10) not null,
    amount     int         not null
);

select * from vouchers;

insert into vouchers(voucher_id, type, amount)
values (UUID_TO_BIN(UUID()), 'percent', 10);