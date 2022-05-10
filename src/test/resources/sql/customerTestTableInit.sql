create table customers
(
    id         int auto_increment primary key,
    name       varchar(20)                              not null,
    created_at datetime(6) default CURRENT_TIMESTAMP(6) null,
    updated_at datetime(6) default CURRENT_TIMESTAMP(6) null
);