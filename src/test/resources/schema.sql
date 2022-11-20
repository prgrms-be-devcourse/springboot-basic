create table if not exists customers
(
    customer_id   binary(16)                               not null
    primary key,
    name          varchar(20)                              not null,
    email         varchar(50)                              not null,
    is_blocked    boolean                                  default false not null
);
