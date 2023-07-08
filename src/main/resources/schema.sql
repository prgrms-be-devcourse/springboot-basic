DROP TABLE IF EXISTS wallet;
DROP TABLE IF EXISTS voucher;
DROP TABLE IF EXISTS member;

CREATE TABLE voucher (
    id VARCHAR(36),
    type VARCHAR(20) NOT NULL,
    discount_value int NOT NULL,
    created_at timestamp default current_timestamp,
    expired_at timestamp NOT NULL,
    primary key (id)
);

CREATE TABLE customer (
    id VARCHAR(36),
    name VARCHAR(10),
    created_at timestamp default current_timestamp,
    modified_at timestamp NOT NULL,
    primary key (id)
);

CREATE TABLE wallet (
    id VARCHAR(36),
    voucher_id VARCHAR(36),
    member_id VARCHAR(36),
    foreign key (voucher_id) references voucher (id)
        on delete cascade on update cascade,
    foreign key (member_id) references member (id)
        on delete cascade on update cascade
);
