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
-- Table structure for table `orderspojo`
--

DROP TABLE IF EXISTS `orderspojo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderspojo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `time` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `version` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderspojo`
--

LOCK TABLES `orderspojo` WRITE;
/*!40000 ALTER TABLE `orderspojo` DISABLE KEYS */;
INSERT INTO `orderspojo` VALUES (1,'2023-01-17 17:59:48','Invoiced and read by scheduler',NULL,NULL,NULL),(2,'2023-01-17 18:00:06','Invoiced and read by scheduler',NULL,NULL,NULL),(3,'2023-01-17 18:00:56','Invoiced and read by scheduler',NULL,NULL,NULL),(4,'2023-01-17 18:42:13','Invoiced and read by scheduler',NULL,NULL,NULL),(5,'2023-01-18 12:07:17','Invoiced and read by scheduler',NULL,NULL,NULL),(6,'2023-01-18 17:28:40','Invoiced and read by scheduler',NULL,NULL,NULL),(9,'2023-01-19 14:45:19','Invoiced and read by scheduler',NULL,NULL,NULL),(10,'2023-01-23 11:03:15','Invoiced and read by scheduler',NULL,NULL,NULL),(15,'2023-01-23 17:33:06','Invoiced and read by scheduler',NULL,NULL,NULL),(27,'2023-01-23 17:46:25','Invoiced and read by scheduler',NULL,NULL,NULL),(28,'2023-01-23 17:46:57','Invoiced and read by scheduler',NULL,NULL,NULL),(29,'2023-01-23 17:47:36','Invoiced and read by scheduler',NULL,NULL,NULL),(31,'2023-01-23 17:55:12','Invoiced and read by scheduler',NULL,NULL,NULL),(32,'2023-01-23 18:26:55','Invoiced and read by scheduler',NULL,NULL,NULL),(34,'2023-01-24 15:51:08','Invoiced and read by scheduler',NULL,NULL,NULL),(36,'2023-01-24 18:00:50','Invoiced and read by scheduler',NULL,NULL,NULL),(37,'2023-01-24 20:38:18','Invoiced and read by scheduler',NULL,NULL,NULL),(40,'2023-01-24 20:46:43','Invoiced and read by scheduler',NULL,NULL,NULL),(41,'2023-01-30 11:04:32','Invoiced and read by scheduler',NULL,NULL,NULL),(42,'2023-01-30 11:04:44','Invoiced and read by scheduler',NULL,NULL,NULL),(43,'2023-01-30 20:39:52','Invoiced and read by scheduler',NULL,NULL,NULL),(44,'2023-01-30 20:39:53','Invoiced and read by scheduler',NULL,NULL,NULL),(45,'2023-01-30 20:39:53','Invoiced and read by scheduler',NULL,NULL,NULL),(46,'2023-01-30 20:45:22','Invoiced and read by scheduler',NULL,NULL,NULL),(49,'2023-02-04 10:37:55','Invoiced and read by scheduler',NULL,NULL,NULL),(50,'2023-02-08 11:12:38','Invoiced and read by scheduler',NULL,NULL,NULL),(54,'2023-02-08 19:40:44','Invoiced and read by scheduler',NULL,NULL,NULL),(55,'2023-02-08 22:03:37','Invoiced and read by scheduler',NULL,NULL,NULL),(59,'2023-02-08 22:40:13','Invoiced and read by scheduler',NULL,NULL,NULL),(60,'2023-02-08 22:45:48','Invoiced and read by scheduler',NULL,NULL,NULL),(61,'2023-02-08 22:46:19','Invoiced and read by scheduler',NULL,NULL,NULL),(62,'2023-02-08 22:48:07','Invoiced and read by scheduler',NULL,NULL,NULL),(66,'2023-02-08 22:51:28','Invoiced and read by scheduler',NULL,NULL,NULL),(67,'2023-02-08 22:53:20','Invoiced and read by scheduler',NULL,NULL,NULL),(70,'2023-02-08 22:56:45','Invoiced and read by scheduler',NULL,NULL,NULL),(73,'2023-02-09 09:50:54','Invoiced and read by scheduler',NULL,NULL,NULL),(84,'2023-02-09 10:10:48','Invoiced and read by scheduler',NULL,NULL,NULL),(90,'2023-02-13 12:33:52','Invoiced and read by scheduler',NULL,NULL,NULL),(94,'2023-02-16 11:46:48','Invoiced and read by scheduler',NULL,NULL,NULL),(95,'2023-02-16 13:22:07','Invoiced and read by scheduler',NULL,NULL,NULL),(96,'2023-02-16 13:22:28','Invoiced and read by scheduler',NULL,NULL,NULL),(97,'2023-02-16 13:35:04','Invoiced and read by scheduler',NULL,NULL,NULL),(98,'2023-02-16 13:44:03','Invoiced and read by scheduler',NULL,NULL,NULL),(99,'2023-02-16 13:44:48','Invoiced and read by scheduler',NULL,NULL,NULL),(100,'2023-02-16 13:45:00','Invoiced and read by scheduler',NULL,NULL,NULL),(101,'2023-02-16 13:45:20','Invoiced and read by scheduler',NULL,NULL,NULL),(102,'2023-02-16 13:46:02','Invoiced and read by scheduler',NULL,NULL,NULL),(103,'2023-02-17 17:55:51','Invoiced and read by scheduler',NULL,NULL,NULL),(106,'2023-02-17 18:23:46','Invoiced and read by scheduler',NULL,NULL,NULL),(107,'2023-02-17 22:06:54','Not Invoiced',NULL,NULL,NULL),(108,'2023-02-17 22:07:30','Invoiced and read by scheduler',NULL,'2023-02-21 16:57:05',NULL),(109,'2023-02-17 22:07:40','Not Invoiced',NULL,NULL,NULL),(110,'2023-02-17 22:08:06','Invoiced and read by scheduler',NULL,NULL,NULL),(111,'2023-02-17 22:08:26','Not Invoiced',NULL,NULL,NULL),(112,'2023-02-17 22:09:30','Not Invoiced',NULL,NULL,NULL),(113,'2023-02-17 22:09:53','Not Invoiced',NULL,NULL,NULL),(114,'2023-02-17 22:10:10','Not Invoiced',NULL,NULL,NULL),(115,'2023-02-17 22:10:23','Invoiced and read by scheduler',NULL,'2023-02-21 16:56:05',NULL),(116,'2023-02-17 22:10:35','Not Invoiced',NULL,NULL,NULL),(117,'2023-02-17 22:11:01','Not Invoiced',NULL,NULL,NULL),(118,'2023-02-17 22:13:10','Not Invoiced',NULL,NULL,NULL),(119,'2023-02-17 22:13:39','Not Invoiced',NULL,NULL,NULL),(120,'2023-02-17 22:13:55','Not Invoiced',NULL,NULL,NULL),(122,'2023-02-17 22:14:20','Not Invoiced',NULL,NULL,NULL),(123,'2023-02-17 22:43:26','Not Invoiced',NULL,NULL,NULL),(124,'2023-02-17 22:43:43','Invoiced and read by scheduler',NULL,'2023-02-21 16:05:57',NULL),(125,'2023-02-17 22:43:56','Not Invoiced',NULL,NULL,NULL),(126,'2023-02-17 22:44:10','Not Invoiced',NULL,NULL,NULL),(133,'2023-02-17 23:20:40','Invoiced and read by scheduler',NULL,NULL,NULL),(139,'2023-02-20 14:21:17','Invoiced and read by scheduler',NULL,NULL,NULL),(140,'2023-02-20 15:09:36','Not Invoiced',NULL,NULL,NULL),(141,'2023-02-20 15:25:28','Not Invoiced',NULL,NULL,NULL),(143,'2023-02-20 15:48:34','Invoiced and read by scheduler',NULL,NULL,NULL),(144,'2023-02-20 16:03:27','Invoiced and read by scheduler',NULL,NULL,NULL),(145,'2023-02-20 20:11:07','Invoiced and read by scheduler',NULL,NULL,NULL),(147,'2023-02-21 11:04:54','Invoiced and read by scheduler',NULL,NULL,NULL),(148,'2023-02-21 11:08:31','Not Invoiced',NULL,NULL,NULL),(149,'2023-02-21 11:11:29','Not Invoiced',NULL,NULL,NULL),(150,'2023-02-21 11:12:29','Invoiced and read by scheduler',NULL,NULL,NULL),(151,'2023-02-21 11:13:40','Invoiced and read by scheduler',NULL,'2023-02-21 16:01:58',NULL),(152,'2023-02-21 11:13:54','Not Invoiced',NULL,NULL,NULL),(153,'2023-02-21 11:14:28','Invoiced and read by scheduler',NULL,'2023-02-21 16:33:05',NULL),(154,'2023-02-21 11:14:41','Invoiced and read by scheduler',NULL,'2023-02-21 16:00:58',NULL),(158,'2023-02-21 16:01:54','Invoiced and read by scheduler','2023-02-21 16:01:54','2023-02-21 16:57:05',NULL),(159,'2023-02-21 16:10:55','Not Invoiced','2023-02-21 16:10:55','2023-02-21 16:10:55',NULL),(160,'2023-02-21 16:13:00','Not Invoiced','2023-02-21 16:13:00','2023-02-21 16:13:00',NULL),(162,'2023-02-21 22:05:47','Invoiced and read by scheduler','2023-02-21 22:05:47','2023-02-21 22:05:47',NULL);
/*!40000 ALTER TABLE `orderspojo` ENABLE KEYS */;
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
