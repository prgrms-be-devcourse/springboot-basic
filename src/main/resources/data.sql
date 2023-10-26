create table vouchers(
    id long,
    discount int,
    discount_type varchar(30),
    created_at datetime not null default current_timestamp,
    updated_at datetime not null default current_timestamp on update current_timestamp,
    primary key (id)
);

create table customers(
    id long,
    name varchar(50) not null,
    email varchar(50) not null,
    is_blocked bool not null default false,
    created_at datetime not null default current_timestamp,
    updated_at datetime not null default current_timestamp on update current_timestamp,
    primary key (id)
)
