create table vouchers(
                         voucher_id                  BINARY(16) PRIMARY KEY,
                         voucher_type                varchar(10),
                         discount_amount             bigint NOT NULL,
                         is_used                     boolean     default false,
                         expiration_date             datetime,
                         created_at                  datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP()
);