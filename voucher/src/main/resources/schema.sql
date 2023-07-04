CREATE TABLE  customers(
                           customer_id     varchar(10) PRIMARY KEY,
                           name            varchar(20) NOT NULL ,
                           email           varchar(50) NOT NULL ,
                           created_at      datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                           CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id varchar(10) PRIMARY KEY,
    type       varchar(30),
    amount     int DEFAULT NULL,
    rate       int DEFAULT NULL
);


