CREATE TABLE if not exists customers
(
    customer_id    varchar(36) PRIMARY KEY,
    name           varchar(20) NOT NULL,
    email          varchar(50) NOT NULL,
    last_login_at  datetime             DEFAULT NULL,
    created_at     datetime    NOT NULL DEFAULT now(),
    CONSTRAINT unq_user_email UNIQUE (email)
);