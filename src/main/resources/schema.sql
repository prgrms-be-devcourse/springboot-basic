CREATE TABLE vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    type varchar(20) NOT NULL,
    amount int NOT NULL
);

CREATE TABLE members
(
    member_id BINARY(16) PRIMARY KEY,
    name varchar(20) NOT NULL
);