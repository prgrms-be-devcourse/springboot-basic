use spring_basic;
create table customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name varchar(20) not null ,
    email varchar(50) not null ,
    last_login_at datetime default null,
    created_at datetime not null default  CURRENT_TIMESTAMP(),
    constraint unq_user_email unique (email)
);
