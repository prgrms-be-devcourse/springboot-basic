CREATE TABLE customers
(
    customer_id   BINARY(16) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    last_login_at datetime             DEFAULT NULL,
    created_at    datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    CONSTRAINT unq_user_email UNIQUE (email)
);


create table vouchers
(
    voucher_id     BINARY(16) PRIMARY KEY,
    voucher_type   varchar(20) NOT NULL,
    discount_value bigint      NOT NULL,
    created_at     datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP()
);


CREATE TABLE IF NOT EXISTS `mission_order`.`customer_vouchers` (
    `id` BINARY(16) NOT NULL,
    `customers_customer_id` BINARY(16) NOT NULL,
    `vouchers_voucher_id` BINARY(16) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_customer_vouchers_customers_idx` (`customers_customer_id` ASC) VISIBLE,
    INDEX `fk_customer_vouchers_vouchers1_idx` (`vouchers_voucher_id` ASC) VISIBLE,
    CONSTRAINT `fk_customer_vouchers_customers`
    FOREIGN KEY (`customers_customer_id`)
    REFERENCES `mission_order`.`customers` (`customer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_customer_vouchers_vouchers1`
    FOREIGN KEY (`vouchers_voucher_id`)
    REFERENCES `mission_order`.`vouchers` (`voucher_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;
