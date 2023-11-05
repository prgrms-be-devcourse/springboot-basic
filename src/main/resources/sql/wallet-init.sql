DROP table wallets;

CREATE table wallets
(
    id varchar(100) PRIMARY KEY ,
    customer_id varchar(100) not null ,
    voucher_id varchar(100) not null
);