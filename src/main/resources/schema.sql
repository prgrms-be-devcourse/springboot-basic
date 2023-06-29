DROP TABLE IF EXISTS wallet;
DROP TABLE IF EXISTS voucher;
DROP TABLE IF EXISTS member;

CREATE TABLE voucher (
    id VARCHAR(36),
    type VARCHAR(20) NOT NULL,
    voucher_value int NOT NULL,
    primary key (id)
);

CREATE TABLE member (
    id VARCHAR(36),
    status VARCHAR(10),
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
