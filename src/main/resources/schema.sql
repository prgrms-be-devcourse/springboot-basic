create table customers
(
    customer_id   binary(16) primary key,
    name          varchar(20) not null,
    email         varchar(50) not null,
    last_login_at datetime             default null,
    created_at    datetime    not null default current_timestamp,
    constraint unq_user_email unique (email)
);