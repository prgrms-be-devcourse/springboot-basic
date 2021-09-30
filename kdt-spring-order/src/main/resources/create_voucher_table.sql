CREATE TABLE vouchers
(
    id BINARY(16) NOT NULL PRIMARY KEY,
    discount_value BIGINT NOT NULL,
    type INT NOT NULL
);