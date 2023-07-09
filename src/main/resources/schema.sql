DROP TABLE IF EXISTS voucher;

CREATE TABLE voucher (
    voucher_id BINARY(16) NOT NULL,
    value BIGINT NOT NULL,
    voucher_type VARCHAR(255) NOT NULL,
    PRIMARY KEY (voucher_id)
);