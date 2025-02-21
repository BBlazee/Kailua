CREATE DATABASE  IF NOT EXISTS `kailua` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `kailua`;
-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: kailua
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `car`
--

DROP TABLE IF EXISTS `car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `car` (
  `registration_number` varchar(10) NOT NULL,
  `brand` varchar(50) NOT NULL,
  `model` varchar(50) NOT NULL,
  `fuel_type` varchar(20) NOT NULL,
  `registration_year` int NOT NULL,
  `registration_month` int NOT NULL,
  `odometer` int NOT NULL,
  `category` enum('Luxury','Family','Sport') NOT NULL,
  PRIMARY KEY (`registration_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car`
--

LOCK TABLES `car` WRITE;
/*!40000 ALTER TABLE `car` DISABLE KEYS */;
INSERT INTO `car` VALUES ('ABC123','Mercedes','S-Class','Diesel',2019,5,25000,'Luxury'),('BLZ420','Toyota','AE86 Sprinter Trueno','Petrol',1987,4,200000,'Sport'),('DEF456','Toyota','Sienna','Petrol',2018,3,50000,'Family'),('GHI789','Porsche','911 Carrera','Petrol',2020,7,15000,'Sport'),('KGM526','Mazda','RX-7 91','Petrol',1992,9,127000,'Sport'),('KLM241','Mitsubishi','Lancer EVO 04','Petrol',2005,7,53000,'Sport');
/*!40000 ALTER TABLE `car` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `driver_license_number` varchar(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `address` varchar(200) NOT NULL,
  `zip` varchar(10) NOT NULL,
  `city` varchar(50) NOT NULL,
  `mobile_phone` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `driver_since` date NOT NULL,
  PRIMARY KEY (`driver_license_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('DLIC001','Alice Johnson','123 Main St','96701','Kailua','808-555-1234','808-555-5678','alice@example.com','2010-06-15'),('DLIC002','Bob Smith','456 Oak Ave','96702','Kailua','808-555-2345','808-555-6789','bob@example.com','2012-09-20'),('DLIC003','Carol Lee','789 Pine Rd','96703','Kailua','808-555-3456','808-555-7890','carol@example.com','2015-11-05'),('DLIC004','Anton Becker','Bobakken 4','2720','Copenhagen','30956585','','anton.becker85@gmail.com','2017-03-06');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rental`
--

DROP TABLE IF EXISTS `rental`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rental` (
  `rental_id` int NOT NULL AUTO_INCREMENT,
  `customer_license` varchar(20) NOT NULL,
  `registration_number` varchar(10) NOT NULL,
  `rental_start` datetime NOT NULL,
  `rental_end` datetime NOT NULL,
  `max_km` int NOT NULL,
  `start_odometer` int NOT NULL,
  PRIMARY KEY (`rental_id`),
  KEY `customer_license` (`customer_license`),
  KEY `registration_number` (`registration_number`),
  CONSTRAINT `rental_ibfk_1` FOREIGN KEY (`customer_license`) REFERENCES `customer` (`driver_license_number`),
  CONSTRAINT `rental_ibfk_2` FOREIGN KEY (`registration_number`) REFERENCES `car` (`registration_number`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rental`
--

LOCK TABLES `rental` WRITE;
/*!40000 ALTER TABLE `rental` DISABLE KEYS */;
INSERT INTO `rental` VALUES (1,'DLIC001','ABC123','2025-03-01 09:00:00','2025-03-05 17:00:00',1000,25000),(2,'DLIC002','DEF456','2025-03-02 10:00:00','2025-03-06 18:00:00',800,50000),(3,'DLIC003','GHI789','2025-03-03 08:30:00','2025-03-07 16:30:00',600,15000),(4,'DLIC004','BLZ420','2025-02-21 12:30:00','2025-03-21 12:30:00',5000,200000);
/*!40000 ALTER TABLE `rental` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-21  7:27:38
