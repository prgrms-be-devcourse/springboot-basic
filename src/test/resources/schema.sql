use voucher;
drop table voucher;
drop table customer;
CREATE TABLE customer
(
    customer_id BINARY(16),
    customer_type enum('NORMAL', 'BLACK') NOT NULL DEFAULT  'NORMAL',
    name varchar(20) NOT NULL,
    email varchar(50) NOT NULL,
    created_date datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    modified_date datetime(6) DEFAULT NULL,

    PRIMARY KEY (customer_id),
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE voucher
(
    voucher_id BINARY(16),
    voucher_type enum('FIXED', 'PERCENT') NOT NULL,
    discount_value BIGINT NOT NULL,
    customer_id BINARY(16),
    created_date datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    modified_date datetime(6) DEFAULT NULL,

    PRIMARY KEY (voucher_id),
    FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON UPDATE CASCADE ON DELETE RESTRICT
);