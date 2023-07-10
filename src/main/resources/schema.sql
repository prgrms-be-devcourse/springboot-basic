DROP TABLE IF EXISTS voucher;
DROP TABLE IF EXISTS customer;

CREATE TABLE voucher (
    id VARCHAR(36),
    type VARCHAR(20) NOT NULL,
    amount int NOT NULL,
    primary key (id)
);

CREATE TABLE customer (
     id VARCHAR(36),
     name VARCHAR(20) NOT NULL,
     type VARCHAR(20) NOT NULL,
     primary key (id)
);
