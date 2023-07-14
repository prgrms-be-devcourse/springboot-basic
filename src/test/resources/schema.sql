DROP TABLE IF EXISTS wallet;
DROP TABLE IF EXISTS voucher;
DROP TABLE IF EXISTS customer;

CREATE TABLE voucher (
    id CHAR(36),
    type VARCHAR(20) NOT NULL,
    discount_value int NOT NULL,
    created_at timestamp default current_timestamp,
    expired_at timestamp NOT NULL,
    primary key (id)
);

CREATE TABLE customer (
    id CHAR(36),
    name VARCHAR(10),
    created_at timestamp default current_timestamp,
    modified_at timestamp,
    primary key (id)
);

CREATE TABLE wallet (
    id CHAR(36),
    voucher_id VARCHAR(36),
    customer_id VARCHAR(36),
    foreign key (voucher_id) references voucher (id)
        on delete cascade on update cascade,
    foreign key (customer_id) references customer (id)
        on delete cascade on update cascade
);
