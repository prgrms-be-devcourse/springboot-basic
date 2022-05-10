-- create database spring_boot_mission;
--
-- use spring_boot_mission;
--
-- SET time_zone='Asia/Seoul'; /* '+09:00' */

-- DROP TABLE voucher;

CREATE TABLE voucher (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(20) NOT NULL,
    amount BIGINT NOT NULL,

);

