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
-- Table structure for table `brandpojo`
--

DROP TABLE IF EXISTS `brandpojo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brandpojo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `brand` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `version` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr55qhm8xe3il1y5j33r77gxvl` (`brand`,`category`),
  KEY `brandIndex` (`brand`),
  KEY `categoryIndex` (`category`)
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brandpojo`
--

LOCK TABLES `brandpojo` WRITE;
/*!40000 ALTER TABLE `brandpojo` DISABLE KEYS */;
INSERT INTO `brandpojo` VALUES (1,'cadbury','dairy milk',NULL,'2023-02-21 16:01:10',NULL),(2,'cadbury','dairy',NULL,NULL,NULL),(3,'nestle','dairy',NULL,'2023-02-21 22:04:54',NULL),(4,'nestle','chocolate',NULL,NULL,NULL),(5,'cadbury','silk',NULL,NULL,NULL),(6,'nestle','tea',NULL,NULL,NULL),(7,'dabur','health',NULL,NULL,NULL),(12,'trends','footwear',NULL,NULL,NULL),(13,'trends','clothing',NULL,NULL,NULL),(14,'puma','sneakerss',NULL,NULL,NULL),(15,'trends','pants',NULL,NULL,NULL),(132,'armani','t-shirt',NULL,NULL,NULL),(137,'mrf','cricket_bat',NULL,NULL,NULL),(138,'c','cc',NULL,NULL,NULL),(139,'d','dss','2023-02-21 15:55:17','2023-02-21 15:56:07',2),(140,'c','cccc','2023-02-21 15:58:31','2023-02-21 16:01:15',1),(141,'ds','ds','2023-02-21 16:01:18','2023-02-21 16:05:40',NULL),(142,'v','v','2023-02-21 22:05:00','2023-02-21 22:05:00',NULL),(143,'g','g',NULL,NULL,NULL);
/*!40000 ALTER TABLE `brandpojo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-22  5:44:20
