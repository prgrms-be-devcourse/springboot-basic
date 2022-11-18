CREATE TABLE voucher
(
    voucher_id  varchar(50) PRIMARY KEY,
    type       varchar(30),
    amount     INTEGER,
    customer_id varchar(50)
);