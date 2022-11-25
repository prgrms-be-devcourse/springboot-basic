create table customer
(
    customer_id VARCHAR(36) PRIMARY KEY ,
    customer_name VARCHAR(20) NOT NULL ,
    email varchar(50) NOT NULL ,
    customer_created_at datetime NOT NULL ,
    customer_type varchar(20) NOT NULL default 'NORMAL',
    constraint unq_user_email unique (email)
);

create table voucher
(
    voucher_id VARCHAR(36) PRIMARY KEY ,
    voucher_value DOUBLE NOT NULL ,
    voucher_created_at datetime NOT NULL ,
    voucher_type varchar(20) NOT NULL
);

create table  wallet
(
    wallet_id varchar(36) PRIMARY KEY ,
    voucher_id VARCHAR(36) NOT NULL ,
    customer_id VARCHAR(36) NOT NULL ,
    wallet_created_at datetime(6) NOT NULL ,
    FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
    FOREIGN KEY (voucher_id) REFERENCES voucher (voucher_id)
)

