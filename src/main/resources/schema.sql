DROP TABLE IF EXISTS voucher;

CREATE TABLE voucher
(
    voucher_id     VARCHAR(200) NOT NULL,
    discount_value BIGINT       NOT NULL,
    voucher_type   VARCHAR(200) NOT NULL,
    PRIMARY KEY (voucher_id)
);


DROP TABLE IF EXISTS customer;

create table customer
(
    customer_id    VARCHAR(200) NOT NULL,
    customer_name  VARCHAR(200) NOT NULL,
    customer_email VARCHAR(200) NOT NULL,
    customer_type  VARCHAR(200) NOT NULL,
    created_at     TIMESTAMP    NOT NULL,
    PRIMARY KEY (customer_id)
);
