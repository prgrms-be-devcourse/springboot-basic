CREATE TABLE voucher
(
    voucher_id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    voucher_type     VARCHAR(30) NOT NULL,
    discount_value   INT NOT NULL
);