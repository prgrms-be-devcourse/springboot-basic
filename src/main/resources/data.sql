insert into  customer_status (customer_status_id, status) values (UUID_TO_BIN(UUID()), 'CREATED');
insert into  customer_status (customer_status_id, status) values (UUID_TO_BIN(UUID()), 'BLOCKED');

insert into voucher_type (voucher_type_id, name) values (UUID_TO_BIN(UUID()), 'FIXEDAMOUNTVOUCHER');
insert into voucher_type (voucher_type_id, name) values (UUID_TO_BIN(UUID()), 'PERCENTDISCOUNTVOUCHER');
