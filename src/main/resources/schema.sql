create table spring_basic.customers
(
    customer_id bigint auto_increment
        primary key,
    name        varchar(20)                              not null,
    email       varchar(50)                              not null,
    created_at  datetime(6) default CURRENT_TIMESTAMP(6) not null,
    constraint unq_user_email
        unique (email)
);