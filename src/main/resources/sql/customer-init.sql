DROP table customers;

CREATE table customers
(
    id varchar(100) PRIMARY KEY ,
    name varchar(20) not null ,
    email varchar(50) not null
);