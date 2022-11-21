create table customers
(
    customer_id   binary(16)  not null primary key,
    type          varchar(20)  not null,
    name          varchar(20)   not null unique ,
    email         varchar(50)   not null,
    last_login_at datetime(6)   null,
    constraint unq_user_email
        unique (email)
);
create table vouchers
(
    voucher_id   binary(16)  not null primary key,
    type         varchar(20)  not null,
    discount     int  not null,
    last_login_at datetime(6)   null,
    assigned_customer_id binary(16) null,
    foreign key (assigned_customer_id)
        references customers(customer_id)
        ON DELETE SET NULL,
    used        boolean
);
