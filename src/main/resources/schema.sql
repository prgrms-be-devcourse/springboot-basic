DROP TABLE IF EXISTS voucher;

CREATE TABLE voucher (
    id VARCHAR(36),
    type VARCHAR(20) NOT NULL,
    amount int NOT NULL,
    primary key (id)
);
