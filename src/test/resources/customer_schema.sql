CREATE TABLE if not exists customer
(
    customer_id    bigint primary key auto_increment,
    name           varchar(20) not null,
    email          varchar(50) not null,
    last_login_at  datetime             default null,
    created_at     datetime    not null default now(),
    constraint unq_user_email unique (email)
);