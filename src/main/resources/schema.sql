CREATE TABLE IF NOT EXISTS customers (
    customer_id BINARY(16) PRIMARY KEY,
    name VARCHAR(26) NOT NULL,
    email VARCHAR(56) NOT NULL ,
    last_login_at DATETIME(6) DEFAULT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    is_blacked BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS vouchers (
    voucher_id BINARY(16) PRIMARY KEY,
    discount_degree bigint NOT NULL,
    voucher_type varchar(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS customers_vouchers ( -- 이 이름이 맞는지? 이건 wallet이라기 보다는 그냥 고객-바우처 매핑 테이블인데?
    customer_id BINARY(16) NOT NULL,
    voucher_id BINARY(16) NOT NULL,
    FOREIGN KEY (customer_id) references customers(customer_id),
    FOREIGN KEY (voucher_id) references vouchers(voucher_id)
);
