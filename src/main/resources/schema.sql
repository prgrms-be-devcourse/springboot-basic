drop table if exists vouchers;
use vouchers;
create table vouchers
(
    voucher_id varchar(50) primary key,
    discount int not null,
    voucher_type varchar(30) not null,
    voucher_createdAt TIMESTAMP not null
)
