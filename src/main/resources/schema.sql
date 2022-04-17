CREATE TABLE customer
(
    id            BINARY(16) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    type          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    last_login_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE voucher
(
    id         BINARY(16) PRIMARY KEY,
    name       varchar(20) NOT NULL,
    type       varchar(20) NOT NULL,
    figure     int         NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
);