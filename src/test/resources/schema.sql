create table if not exists customers
(
    customer_id   binary(16)                               not null
    primary key,
    name          varchar(20)                              not null,
    email         varchar(50)                              not null,
    is_blocked    boolean                                  default false not null
);

CREATE TABLE if NOT EXISTS vouchers
(
    voucher_id      BINARY(16)      PRIMARY KEY NOT NULL,
    amount          BIGINT          NOT NULL,
    type            VARCHAR(20)     NOT NULL,
    expired_at      DATETIME(6)     NOT NULL,
    used            BOOLEAN         DEFAULT FALSE NOT NULL
);