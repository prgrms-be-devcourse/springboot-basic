# customer 테이블 만들기
SET foreign_key_checks = 0;

drop table if exists customers_demo CASCADE;
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
drop table if exists voucher_type CASCADE;
create table if not exists voucher_type
(
    id           TINYINT AUTO_INCREMENT PRIMARY KEY,
    voucher_type VARCHAR(20) NOT NULL
);
INSERT INTO voucher_type (voucher_type)
values ('FIXED');
INSERT INTO voucher_type (voucher_type)
values ('PERCENT');

# Voucher 테이블 만들기
drop table if exists vouchers_demo CASCADE;
create table if not exists vouchers_demo
(
    voucher_id   BINARY(16) PRIMARY KEY,
    customer_id  binary(16) DEFAULT NULL,
    voucher_type TINYINT NOT NULL,
    value        BIGINT  NOT NULL,
    FOREIGN KEY (voucher_type) REFERENCES voucher_type (id) ON UPDATE CASCADE
);
# Walet테이블 만들기

SET foreign_key_checks = 1;

##전체 테이블 삭제