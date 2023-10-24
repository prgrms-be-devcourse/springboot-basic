create table if not exists voucher(
                        id BINARY(16) not null primary key ,
                        amount int not null,
                        voucher_type varchar(25) not null
);

create table if not exists customer(
                         id BINARY(16) not null ,
                         name varchar(25) not null ,
                         email varchar(25) unique not null,
                         isBlack boolean not null
);
create table if not exists wallet(
                       id int not null primary key auto_increment,
                       customer_email varchar(25) not null ,
                       voucher_id BINARY(16) not null ,
                       foreign key (customer_email) references customer(email),
                       foreign key (voucher_id) references voucher(id)
);
