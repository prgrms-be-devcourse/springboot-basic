create table customer
(
    customer_id VARCHAR(36) PRIMARY KEY ,
    customer_name varbinary(20) NOT NULL ,
    email varchar(50) NOT NULL ,
    created_at datetime NOT NULL default current_timestamp(),
    customer_type varchar(20) NOT NULL default 'NORMAL',
    constraint unq_user_email unique (email)
);

create table voucher
(
    voucher_id VARCHAR(36) PRIMARY KEY ,
    voucher_value DOUBLE NOT NULL ,
    created_at datetime NOT NULL default current_timestamp(),
    voucher_type varchar(20) NOT NULL,
    customer_id VARCHAR(16),
    FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
);
