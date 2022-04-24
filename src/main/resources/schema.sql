create table customer (
                          customer_id BINARY(16) PRIMARY KEY,
                          name varchar(20) NOT NULL,
                          email varchar(50) NOT NULL,
                          created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
                          customer_status_id BINARY(16),
                          CONSTRAINT unq_user_email UNIQUE (email),
                          FOREIGN KEY (customer_status_id) REFERENCES customer_status(customer_status_id)
);

create table customer_status (
                                 customer_status_id BINARY(16) PRIMARY KEY,
                                 status varchar(50) NOT NULL,
                                 created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
                                 CONSTRAINT unq_customer_status UNIQUE (status)
);

create table voucher_type (
                              voucher_type_id BINARY(16) PRIMARY KEY,
                              name varchar(50) NOT NULL,
                              created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
                              CONSTRAINT unq_voucher_type_name UNIQUE (name)
);


create table voucher (
                         voucher_id BINARY(16) PRIMARY KEY,
                         created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
                         voucher_type_id BINARY(16),
                         Value INT NOT NULL,
                         CONSTRAINT unq_voucher_name UNIQUE (name),
                         FOREIGN KEY (voucher_type_id) REFERENCES voucher_type(voucher_type_id)
);
