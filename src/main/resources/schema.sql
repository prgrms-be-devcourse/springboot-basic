DROP TABLE IF EXISTS wallet;
DROP TABLE IF EXISTS voucher;
DROP TABLE IF EXISTS member;

CREATE TABLE voucher
(
    id varchar(40) PRIMARY KEY,
    type ENUM('FIXED', 'PERCENT') NOT NULL,
    amount DOUBLE NOT NULL
);

CREATE TABLE member
(
    id varchar(40) PRIMARY KEY,
    name varchar(10) NOT NULL,
    status ENUM('COMMON', 'BLACK') NOT NULL
);

CREATE TABLE wallet
(
    id varchar(40) PRIMARY KEY,
    member_id varchar(40),
    voucher_id varchar(40),
    FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE ,
    FOREIGN KEY (voucher_id) REFERENCES voucher(id) ON DELETE CASCADE
);