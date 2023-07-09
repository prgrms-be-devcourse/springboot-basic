drop table if exists customers cascade;
create table customers
(
    customer_id    varchar(36) primary key,
    customer_name  varchar(30) not null,
    customer_email varchar(50) not null,
    customer_type  varchar(20) not null
);

create table vouchers
(
    voucher_id varchar(36) primary key,
    voucher_discount_amount int not null,
    voucher_type varchar(20) not null
);

create table wallet
(
    wallet_id varchar(36) primary key,
    customer_id varchar(36) not null unique,
    voucher_id varchar(36) not null,
    foreign key (customer_id) references customers(customer_id)
    on delete cascade on update cascade,
    foreign key (voucher_id) references vouchers(voucher_id)
    on delete cascade on update cascade
);

INSERT INTO vouchers (voucher_id, voucher_discount_amount, voucher_type)
VALUES
    ('3c142393-d36f-4c23-bfa9-c4dd59ef74d5', 1000, 'FIXED'),
    ('3c142393-d36f-4c23-bfa9-c4dd59ef74d1', 2000, 'FIXED'),
    ('3c142393-d36f-4c23-bfa9-c4dd59ef74d2', 32, 'PERCENT'),
    ('3c142393-d36f-4c23-bfa9-c4dd59ef74d3', 50, 'PERCENT'),
    ('3c142393-d36f-4c23-bfa9-c4dd59ef74d4', 70, 'PERCENT');

INSERT INTO customers (customer_id, customer_name, customer_email, customer_type)
VALUES
    ('991505e7-bf6b-4565-8211-92a1a4099e71', 'changhyeon', 'changhyeon.h@kakao.com','BLACKLIST'),
    ('991505e7-bf6b-4565-8211-92a1a4099e72', 'hong', 'hong@kakao.com','BLACKLIST'),
    ('991505e7-bf6b-4565-8211-92a1a4099e73', 'changh', 'chang@kakao.com','NORMAL');
