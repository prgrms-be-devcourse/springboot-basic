CREATE TABLE customer
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(20) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    rating       VARCHAR(20) DEFAULT 'normal',
    status       CHAR DEFAULT 'Y',
    created_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE voucher
(
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    discount_type   varchar(10),
    discount_amount BIGINT,
    customer_id     BIGINT,
    status          CHAR DEFAULT 'Y',
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    foreign key (customer_id) references customer (id)
);
