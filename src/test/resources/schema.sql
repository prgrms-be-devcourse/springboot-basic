CREATE TABLE voucher
(
    voucher_id binary(16) default RANDOM_UUID() primary key,
    voucher_type varchar(20) not null,
    discount int not null,
    created_at timestamp default CURRENT_TIMESTAMP()
);

insert into voucher(voucher_type, discount) values ('PERCENT',20);
insert into voucher(voucher_type, discount) values ('FIXED',30000);
