create table vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    value bigint NOT NULL,
    type VARCHAR(30) NOT NULL,
    created_at datetime(6) not null,
    updated_at datetime(6) DEFAULT null
);