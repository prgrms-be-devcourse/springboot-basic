-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: order_mgmt
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `customers_demo`
--

DROP TABLE IF EXISTS `customers_demo`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers_demo`
(
    `customer_id`   binary(16)  NOT NULL,
    `name`          varchar(20) NOT NULL,
    `birth_date`    date        NOT NULL,
    `email`         varchar(50) NOT NULL,
    `black_list`    tinyint(1)  NOT NULL DEFAULT '0',
    `last_login_at` datetime(6)          DEFAULT NULL,
    `created_at`    datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`customer_id`),
    UNIQUE KEY `unq_user_email` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers_demo`
--

LOCK TABLES `customers_demo` WRITE;
/*!40000 ALTER TABLE `customers_demo`
    DISABLE KEYS */;
INSERT INTO `customers_demo`
VALUES (_binary 'T\�\�<h�\� B�\0', '가렌', '1940-01-01', '가붕이@gmail.com', 1, NULL, '2022-11-20 03:49:27.000000'),
       (_binary 'C�\rh톂B�\0', '아우솔', '1900-01-01', 'ausol@gmail.com', 0, NULL, '2022-11-19 14:21:45.000000'),
       (_binary '�{Sh톂B�\0', 'park', '1999-02-04', 'park@naver.com', 0, NULL, '2022-11-19 14:08:03.000000'),
       (_binary '\��Mh�\� B�\0', '이즈리얼', '2000-12-01', 'ez@gmail.com', 1, NULL, '2022-11-20 04:00:03.000000'),
       (_binary '�_\0h�\� B�\0', '침착맨', '1983-12-05', '요그@gmail.com', 1, NULL, '2022-11-20 04:01:10.000000');
/*!40000 ALTER TABLE `customers_demo`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2022-11-25 22:02:33
