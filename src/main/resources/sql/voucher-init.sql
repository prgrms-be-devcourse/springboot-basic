DROP table vouchers;

CREATE table vouchers
(
    id varchar(100) PRIMARY KEY ,
    name varchar(20) not null ,
    amount integer not null ,
    voucher_type varchar(10) not null
);