create table IF NOT EXISTS customers_demo
(
    customer_id   BINARY(16) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    birth_date    date        NOT NULL,
    email         varchar(50) NOT NULL,
    black_list    boolean     NOT NULL DEFAULT false,
    last_login_at datetime(6)          DEFAULT NULL,
    created_at    datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

