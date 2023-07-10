DROP TABLE IF EXISTS voucher;

CREATE TABLE voucher (
    voucher_id VARCHAR(200) NOT NULL,
    discount_value BIGINT NOT NULL,
    voucher_type VARCHAR(200) NOT NULL,
    PRIMARY KEY (voucher_id)
);