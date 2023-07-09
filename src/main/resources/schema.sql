drop table if exists member_table;

create table member_table(
    member_id varchar(60) not null,
    member_status varchar(10) not null,
    name varchar(20) not null,

    primary key (member_id)
);
