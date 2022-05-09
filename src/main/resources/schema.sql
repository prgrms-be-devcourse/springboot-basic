-- auto-generated definition
create table vouchers
(
    voucher_id binary(16)  not null
        primary key,
    value      bigint      not null,
    created_at datetime(6) not null,
    DTYPE      varchar(30) not null
);

-- auto-generated definition
create table customers
(
    customer_id binary(16)                         not null
        primary key,
    name        varchar(20)                        not null,
    email       varchar(50)                        not null,
    voucher_id  binary(16)                         null,
    created_at  datetime default CURRENT_TIMESTAMP not null,
    constraint email
        unique (email),
    constraint customers_ibfk_1
        foreign key (voucher_id) references vouchers (voucher_id)
);

create index voucher_id
    on customers (voucher_id);


-- customers insert
insert into customers(customer_id, name, email)
    values(UUID_TO_BIN(UUID()),"test-user1","test-user1@gmail.com");
insert into customers(customer_id, name, email)
    values(UUID_TO_BIN(UUID()),"test-user2","test-user2@gmail.com");
insert into customers(customer_id, name, email)
    values(UUID_TO_BIN(UUID()),"test-user3","test-user3@gmail.com");
insert into customers(customer_id, name, email)
    values(UUID_TO_BIN(UUID()),"test-user4","test-user4@gmail.com");
insert into customers(customer_id, name, email)
    values(UUID_TO_BIN(UUID()),"test-user5","test-user5@gmail.com");


