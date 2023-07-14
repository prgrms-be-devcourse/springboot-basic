DROP TABLE IF EXISTS voucher;

CREATE TABLE voucher (
    id VARCHAR(36) PRIMARY KEY,
    type VARCHAR(5) NOT NULL,
    discount_amount INTEGER NOT NULL
);