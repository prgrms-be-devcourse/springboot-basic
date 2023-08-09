CREATE TABLE voucher
(
    voucher_id binary(16) default RANDOM_UUID() primary key,
    voucher_type varchar(20) not null,
    discount int not null,
    created_at timestamp default CURRENT_TIMESTAMP()
);

CREATE TABLE customers
(
    customer_id binary(16) default RANDOM_UUID() primary key,
    name varchar(20) not null,
    email varchar(50) not null,
    created_at timestamp default CURRENT_TIMESTAMP(),
    updated_at timestamp default CURRENT_TIMESTAMP()
);


insert into voucher(voucher_type, discount) values ('PERCENT',20);
insert into voucher(voucher_type, discount) values ('FIXED',30000);

insert into customers(name,email) values ('일세한','1sehan@naver.com');
insert into customers(name,email) values ('이세한','2sehan@naver.com');
