-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: employee
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `productpojo`
--

DROP TABLE IF EXISTS `productpojo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productpojo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `barcode` varchar(255) DEFAULT NULL,
  `brand_category_id` int NOT NULL,
  `mrp` double NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `version` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK5323ynxfutovqw4273eb71cqm` (`barcode`),
  KEY `barcodeIndex` (`barcode`),
  KEY `brand_category_idIndex` (`brand_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productpojo`
--

LOCK TABLES `productpojo` WRITE;
/*!40000 ALTER TABLE `productpojo` DISABLE KEYS */;
INSERT INTO `productpojo` VALUES (1,'a',1,100,'chocolates',NULL,NULL,NULL),(2,'b',2,231,'arogya',NULL,NULL,NULL),(3,'c',3,30,'kitkat',NULL,NULL,NULL),(4,'d',6,10,'green tea',NULL,NULL,NULL),(5,'e',5,200,'bubbly',NULL,NULL,NULL),(6,'f',4,25,'amul',NULL,NULL,NULL),(7,'g',5,125.75,'mousee',NULL,NULL,NULL),(8,'h',7,750,'honey',NULL,NULL,NULL),(9,'i',12,500,'slippers',NULL,NULL,NULL),(10,'j',13,5000,'allen solly',NULL,NULL,NULL),(41,'ax',132,5000,'arex',NULL,NULL,NULL),(44,'v',142,1234,'v','2023-02-21 22:05:24','2023-02-21 22:05:24',NULL);
/*!40000 ALTER TABLE `productpojo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-22  5:44:19
