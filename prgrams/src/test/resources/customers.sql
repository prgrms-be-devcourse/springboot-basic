-- auto-generated definition
create table customers
(
    customer_id   varchar(36)                               not null
        primary key,
    name          varchar(20)                              not null,
    email         varchar(50)                              not null,
    type          varchar(20)                              not null,
    created_at    datetime(6) default CURRENT_TIMESTAMP(6) not null,
    constraint unq_user_email
        unique (email)
);
