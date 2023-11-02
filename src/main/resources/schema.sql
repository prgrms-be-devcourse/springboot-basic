DROP TABLE IF EXISTS wallets;
DROP TABLE IF EXISTS vouchers;
DROP TABLE IF EXISTS customers;

CREATE TABLE vouchers
(
    id             varchar(36) primary key,
    discount_value INT,
    type           varchar(20),
    created_at     datetime default now()
);


CREATE TABLE customers
(
    id         BIGINT primary key auto_increment,
    name       varchar(10),
    email      varchar(40),
    black_list bool
);


CREATE TABLE wallets
(
    id          BIGINT primary key auto_increment,
    customer_id BIGINT,
    voucher_id  VARCHAR(36),
    foreign key (customer_id) REFERENCES customers (id) on delete cascade on update cascade,
    foreign key (voucher_id) REFERENCES vouchers (id) on delete cascade on update cascade
);

