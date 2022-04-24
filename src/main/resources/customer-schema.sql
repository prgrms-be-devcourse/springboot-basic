create table customer_type
(
    customer_type_id   int auto_increment
        primary key,
    customer_type_name varchar(15) null,
    constraint customer_type_customer_type_id_uindex
        unique (customer_type_id)
);

create table customer
(
    customer_id   binary(16)                               not null
        primary key,
    name          varchar(20)                              not null,
    email         varchar(50)                              not null,
    last_login_at datetime(6)                              null,
    created_at    datetime(6) default CURRENT_TIMESTAMP(6) not null,
    customer_type int                                      null,
    constraint unq_user_email
        unique (email),
    constraint customer_type_fk
        foreign key (customer_type) references customer_type (customer_type_id)
);

create table voucher_type
(
    voucher_type_id   int auto_increment
        primary key,
    voucher_type_name varchar(100) null
);

create table voucher
(
    voucher_id     binary(16) not null
        primary key,
    voucher_amount mediumtext null,
    voucher_type   int        not null,
    constraint voucher_voucher_id_uindex
        unique (voucher_id),
    constraint voucher_type_fk
        foreign key (voucher_type) references voucher_type (voucher_type_id)
);

create table wallet
(
    customer_id binary(16) null,
    voucher_id  binary(16) null,
    constraint wallet_customer_fk
        foreign key (customer_id) references customer (customer_id),
    constraint wallet_voucher_fk
        foreign key (voucher_id) references voucher (voucher_id)
);

INSERT INTO customer_type(customer_type_id, customer_type_name) VALUES (1, 'WHITE');
INSERT INTO customer_type(customer_type_id, customer_type_name) VALUES (2, 'BLACK');

INSERT INTO voucher_type(voucher_type_id, voucher_type_name) VALUES (1, 'Fixed Amount Voucher');
INSERT INTO voucher_type(voucher_type_id, voucher_type_name) VALUES (2, 'Percent Discount Voucher');