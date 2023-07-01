drop table if exists voucher;
create table voucher (
    voucher_id uuid primary key,
    voucher_type varchar(20) not null,
    amount int not null
);

select * from voucher;