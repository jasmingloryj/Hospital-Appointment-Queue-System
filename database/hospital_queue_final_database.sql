-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: hospital_queue_v2
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `appointments`
--

DROP TABLE IF EXISTS `appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointments` (
  `appointment_id` int NOT NULL AUTO_INCREMENT,
  `patient_id` int NOT NULL,
  `doctor_id` int NOT NULL,
  `appointment_date` date NOT NULL,
  `appointment_time` time NOT NULL,
  `symptoms` varchar(500) NOT NULL,
  `status` enum('BOOKED','WAITING','IN_CONSULTATION','COMPLETED','CANCELLED') NOT NULL DEFAULT 'BOOKED',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`appointment_id`),
  KEY `patient_id` (`patient_id`),
  KEY `doctor_id` (`doctor_id`),
  CONSTRAINT `appointments_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`patient_id`),
  CONSTRAINT `appointments_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`doctor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointments`
--

LOCK TABLES `appointments` WRITE;
/*!40000 ALTER TABLE `appointments` DISABLE KEYS */;
INSERT INTO `appointments` VALUES (42,7,1,'2026-11-12','09:00:00','General consultation','BOOKED','2026-11-11 15:18:24'),(43,8,1,'2026-11-12','09:15:00','General consultation','BOOKED','2026-11-11 15:18:24'),(44,9,1,'2026-11-12','09:30:00','General consultation','BOOKED','2026-11-11 15:18:24'),(45,10,1,'2026-11-12','09:45:00','General consultation','BOOKED','2026-11-11 15:18:24'),(46,11,1,'2026-11-12','10:00:00','General consultation','BOOKED','2026-11-11 15:18:24'),(47,12,1,'2026-11-12','10:15:00','General consultation','BOOKED','2026-11-11 15:18:24'),(48,13,1,'2026-11-12','10:30:00','General consultation','BOOKED','2026-11-11 15:18:24'),(49,14,1,'2026-11-12','10:45:00','General consultation','BOOKED','2026-11-11 15:18:24'),(50,15,1,'2026-11-12','11:00:00','General consultation','BOOKED','2026-11-11 15:18:24'),(51,16,1,'2026-11-12','11:15:00','General consultation','BOOKED','2026-11-11 15:18:24'),(52,17,1,'2026-11-12','11:30:00','General consultation','BOOKED','2026-11-11 15:18:24'),(53,18,1,'2026-11-12','11:45:00','General consultation','BOOKED','2026-11-11 15:18:24'),(54,19,1,'2026-11-12','12:00:00','General consultation','BOOKED','2026-11-11 15:18:24'),(55,20,1,'2026-11-12','12:15:00','General consultation','BOOKED','2026-11-11 15:18:24'),(56,21,1,'2026-11-12','12:30:00','General consultation','BOOKED','2026-11-11 15:18:24'),(57,22,1,'2026-11-12','12:45:00','General consultation','BOOKED','2026-11-11 15:18:24'),(58,23,1,'2026-11-12','13:00:00','General consultation','BOOKED','2026-11-11 15:18:24'),(59,24,1,'2026-11-12','13:15:00','General consultation','BOOKED','2026-11-11 15:18:24'),(60,25,1,'2026-11-12','13:30:00','General consultation','BOOKED','2026-11-11 15:18:24'),(61,26,1,'2026-11-12','13:45:00','General consultation','BOOKED','2026-11-11 15:18:24'),(62,27,1,'2026-11-12','14:00:00','General consultation','BOOKED','2026-11-11 15:18:24'),(63,28,1,'2026-11-12','14:15:00','General consultation','BOOKED','2026-11-11 15:18:24'),(64,29,1,'2026-11-12','14:30:00','General consultation','BOOKED','2026-11-11 15:18:24'),(65,30,1,'2026-11-12','14:45:00','General consultation','BOOKED','2026-11-11 15:18:24'),(66,31,1,'2026-11-12','15:00:00','General consultation','BOOKED','2026-11-11 15:18:24'),(67,32,2,'2026-11-12','10:00:00','General consultation','BOOKED','2026-11-11 15:18:24'),(68,33,2,'2026-11-12','10:15:00','General consultation','BOOKED','2026-11-11 15:18:24'),(69,34,2,'2026-11-12','10:30:00','General consultation','BOOKED','2026-11-11 15:18:24'),(70,35,2,'2026-11-12','10:45:00','General consultation','BOOKED','2026-11-11 15:18:24'),(71,36,2,'2026-11-12','11:00:00','General consultation','BOOKED','2026-11-11 15:18:24'),(72,37,2,'2026-11-12','11:15:00','General consultation','BOOKED','2026-11-11 15:18:24'),(73,38,2,'2026-11-12','11:30:00','General consultation','BOOKED','2026-11-11 15:18:24'),(74,39,2,'2026-11-12','11:45:00','General consultation','BOOKED','2026-11-11 15:18:24'),(75,40,2,'2026-11-12','12:00:00','General consultation','BOOKED','2026-11-11 15:18:24'),(76,41,2,'2026-11-12','12:15:00','General consultation','BOOKED','2026-11-11 15:18:24'),(77,42,2,'2026-11-12','12:30:00','General consultation','BOOKED','2026-11-11 15:18:24'),(78,43,2,'2026-11-12','12:45:00','General consultation','BOOKED','2026-11-11 15:18:24'),(79,44,2,'2026-11-12','13:00:00','General consultation','BOOKED','2026-11-11 15:18:24'),(80,45,2,'2026-11-12','13:15:00','General consultation','BOOKED','2026-11-11 15:18:24'),(81,46,2,'2026-11-12','13:30:00','General consultation','BOOKED','2026-11-11 15:18:24'),(82,47,2,'2026-11-12','13:45:00','General consultation','BOOKED','2026-11-11 15:18:24'),(83,48,2,'2026-11-12','14:00:00','General consultation','BOOKED','2026-11-11 15:18:24'),(84,49,2,'2026-11-12','14:15:00','General consultation','BOOKED','2026-11-11 15:18:24'),(85,50,2,'2026-11-12','14:30:00','General consultation','BOOKED','2026-11-11 15:18:24'),(86,51,2,'2026-11-12','14:45:00','General consultation','BOOKED','2026-11-11 15:18:24'),(87,52,2,'2026-11-12','15:00:00','General consultation','BOOKED','2026-11-11 15:18:24'),(88,53,2,'2026-11-12','15:15:00','General consultation','BOOKED','2026-11-11 15:18:24'),(89,54,2,'2026-11-12','15:30:00','General consultation','BOOKED','2026-11-11 15:18:24'),(90,55,2,'2026-11-12','15:45:00','General consultation','BOOKED','2026-11-11 15:18:24'),(91,56,2,'2026-11-12','16:00:00','General consultation','BOOKED','2026-11-11 15:18:24');
/*!40000 ALTER TABLE `appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consultations`
--

DROP TABLE IF EXISTS `consultations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consultations` (
  `consultation_id` int NOT NULL AUTO_INCREMENT,
  `appointment_id` int NOT NULL,
  `doctor_id` int NOT NULL,
  `diagnosis` varchar(500) NOT NULL,
  `advice` varchar(1000) DEFAULT NULL,
  `follow_up_required` tinyint(1) NOT NULL DEFAULT '0',
  `follow_up_date` date DEFAULT NULL,
  `consultation_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`consultation_id`),
  UNIQUE KEY `appointment_id` (`appointment_id`),
  KEY `doctor_id` (`doctor_id`),
  CONSTRAINT `consultations_ibfk_1` FOREIGN KEY (`appointment_id`) REFERENCES `appointments` (`appointment_id`),
  CONSTRAINT `consultations_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`doctor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consultations`
--

LOCK TABLES `consultations` WRITE;
/*!40000 ALTER TABLE `consultations` DISABLE KEYS */;
/*!40000 ALTER TABLE `consultations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `departments`
--

DROP TABLE IF EXISTS `departments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `departments` (
  `department_id` int NOT NULL AUTO_INCREMENT,
  `department_name` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`department_id`),
  UNIQUE KEY `department_name` (`department_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `departments`
--

LOCK TABLES `departments` WRITE;
/*!40000 ALTER TABLE `departments` DISABLE KEYS */;
INSERT INTO `departments` VALUES (1,'General Medicine','Primary care and general medical consultation'),(2,'Cardiology','Diagnosis and treatment of heart-related conditions'),(3,'Neurology','Diagnosis and treatment of nervous system disorders'),(4,'Orthopedics','Treatment of bones, joints, and musculoskeletal conditions'),(5,'Pediatrics','Medical care for infants, children, and adolescents');
/*!40000 ALTER TABLE `departments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctors`
--

DROP TABLE IF EXISTS `doctors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctors` (
  `doctor_id` int NOT NULL,
  `department_id` int NOT NULL,
  `specialization` varchar(100) DEFAULT NULL,
  `availability` varchar(50) NOT NULL,
  PRIMARY KEY (`doctor_id`),
  KEY `department_id` (`department_id`),
  CONSTRAINT `doctors_ibfk_1` FOREIGN KEY (`doctor_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `doctors_ibfk_2` FOREIGN KEY (`department_id`) REFERENCES `departments` (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctors`
--

LOCK TABLES `doctors` WRITE;
/*!40000 ALTER TABLE `doctors` DISABLE KEYS */;
INSERT INTO `doctors` VALUES (1,1,'General Physician','09:00-17:00'),(2,2,'Cardiologist','10:00-18:00'),(3,3,'Neurologist','09:00-16:00'),(4,4,'Orthopedic Specialist','10:00-17:00'),(5,5,'Pediatrician','09:00-15:00');
/*!40000 ALTER TABLE `doctors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patients` (
  `patient_id` int NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `blood_group` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`patient_id`),
  CONSTRAINT `patients_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES (7,'1994-08-09','Male','Residential Area 8','AB-'),(8,'2000-04-27','Female','Residential Area 9','A+'),(9,'1997-10-16','Male','Residential Area 10','A-'),(10,'1998-03-12','Female','Residential Area 1','B+'),(11,'1995-07-24','Male','Residential Area 2','B-'),(12,'2001-11-05','Female','Residential Area 3','O+'),(13,'1992-01-18','Male','Residential Area 4','O-'),(14,'1999-09-30','Female','Residential Area 5','AB+'),(15,'1996-05-14','Male','Residential Area 6','AB-'),(16,'2002-12-21','Female','Residential Area 7','A+'),(17,'1994-08-09','Male','Residential Area 8','A-'),(18,'2000-04-27','Female','Residential Area 9','B+'),(19,'1997-10-16','Male','Residential Area 10','B-'),(20,'1998-03-12','Female','Residential Area 1','O+'),(21,'1995-07-24','Male','Residential Area 2','O-'),(22,'2001-11-05','Female','Residential Area 3','AB+'),(23,'1992-01-18','Male','Residential Area 4','AB-'),(24,'1999-09-30','Female','Residential Area 5','A+'),(25,'1996-05-14','Male','Residential Area 6','A-'),(26,'2002-12-21','Female','Residential Area 7','B+'),(27,'1994-08-09','Male','Residential Area 8','B-'),(28,'2000-04-27','Female','Residential Area 9','O+'),(29,'1997-10-16','Male','Residential Area 10','O-'),(30,'1998-03-12','Female','Residential Area 1','AB+'),(31,'1995-07-24','Male','Residential Area 2','AB-'),(32,'2001-11-05','Female','Residential Area 3','A+'),(33,'1992-01-18','Male','Residential Area 4','A-'),(34,'1999-09-30','Female','Residential Area 5','B+'),(35,'1996-05-14','Male','Residential Area 6','B-'),(36,'2002-12-21','Female','Residential Area 7','O+'),(37,'1994-08-09','Male','Residential Area 8','O-'),(38,'2000-04-27','Female','Residential Area 9','AB+'),(39,'1997-10-16','Male','Residential Area 10','AB-'),(40,'1998-03-12','Female','Residential Area 1','A+'),(41,'1995-07-24','Male','Residential Area 2','A-'),(42,'2001-11-05','Female','Residential Area 3','B+'),(43,'1992-01-18','Male','Residential Area 4','B-'),(44,'1999-09-30','Female','Residential Area 5','O+'),(45,'1996-05-14','Male','Residential Area 6','O-'),(46,'2002-12-21','Female','Residential Area 7','AB+'),(47,'1994-08-09','Male','Residential Area 8','AB-'),(48,'2000-04-27','Female','Residential Area 9','A+'),(49,'1997-10-16','Male','Residential Area 10','A-'),(50,'1998-03-12','Female','Residential Area 1','B+'),(51,'1995-07-24','Male','Residential Area 2','B-'),(52,'2001-11-05','Female','Residential Area 3','O+'),(53,'1992-01-18','Male','Residential Area 4','O-'),(54,'1999-09-30','Female','Residential Area 5','AB+'),(55,'1996-05-14','Male','Residential Area 6','AB-'),(56,'2002-12-21','Female','Residential Area 7','A+');
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prescription_items`
--

DROP TABLE IF EXISTS `prescription_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prescription_items` (
  `prescription_item_id` int NOT NULL AUTO_INCREMENT,
  `consultation_id` int NOT NULL,
  `medicine_name` varchar(150) NOT NULL,
  `dosage` varchar(100) NOT NULL,
  `frequency` varchar(100) NOT NULL,
  `duration` varchar(100) NOT NULL,
  `instructions` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`prescription_item_id`),
  KEY `consultation_id` (`consultation_id`),
  CONSTRAINT `prescription_items_ibfk_1` FOREIGN KEY (`consultation_id`) REFERENCES `consultations` (`consultation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescription_items`
--

LOCK TABLES `prescription_items` WRITE;
/*!40000 ALTER TABLE `prescription_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `prescription_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `queue`
--

DROP TABLE IF EXISTS `queue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `queue` (
  `queue_id` int NOT NULL AUTO_INCREMENT,
  `appointment_id` int NOT NULL,
  `doctor_id` int NOT NULL,
  `queue_date` date NOT NULL,
  `queue_number` int NOT NULL,
  `priority_level` int NOT NULL DEFAULT '0',
  `emergency_status` enum('NONE','PENDING','APPROVED','REJECTED') NOT NULL DEFAULT 'NONE',
  `status` enum('WAITING','CALLED','IN_CONSULTATION','COMPLETED','CANCELLED') NOT NULL DEFAULT 'WAITING',
  `called_at` datetime DEFAULT NULL,
  `completed_at` datetime DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`queue_id`),
  UNIQUE KEY `appointment_id` (`appointment_id`),
  UNIQUE KEY `unique_doctor_queue_number` (`doctor_id`,`queue_date`,`queue_number`),
  CONSTRAINT `queue_ibfk_1` FOREIGN KEY (`appointment_id`) REFERENCES `appointments` (`appointment_id`),
  CONSTRAINT `queue_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`doctor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `queue`
--

LOCK TABLES `queue` WRITE;
/*!40000 ALTER TABLE `queue` DISABLE KEYS */;
INSERT INTO `queue` VALUES (42,42,1,'2026-11-12',1,0,'PENDING','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(43,43,1,'2026-11-12',2,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(44,44,1,'2026-11-12',3,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(45,45,1,'2026-11-12',4,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(46,46,1,'2026-11-12',5,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(47,47,1,'2026-11-12',6,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(48,48,1,'2026-11-12',7,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(49,49,1,'2026-11-12',8,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(50,50,1,'2026-11-12',9,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(51,51,1,'2026-11-12',10,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(52,52,1,'2026-11-12',11,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(53,53,1,'2026-11-12',12,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(54,54,1,'2026-11-12',13,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(55,55,1,'2026-11-12',14,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(56,56,1,'2026-11-12',15,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(57,57,1,'2026-11-12',16,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(58,58,1,'2026-11-12',17,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(59,59,1,'2026-11-12',18,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(60,60,1,'2026-11-12',19,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(61,61,1,'2026-11-12',20,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(62,62,1,'2026-11-12',21,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(63,63,1,'2026-11-12',22,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(64,64,1,'2026-11-12',23,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(65,65,1,'2026-11-12',24,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(66,66,1,'2026-11-12',25,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(67,67,2,'2026-11-12',1,0,'PENDING','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(68,68,2,'2026-11-12',2,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(69,69,2,'2026-11-12',3,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(70,70,2,'2026-11-12',4,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(71,71,2,'2026-11-12',5,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(72,72,2,'2026-11-12',6,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(73,73,2,'2026-11-12',7,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(74,74,2,'2026-11-12',8,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(75,75,2,'2026-11-12',9,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(76,76,2,'2026-11-12',10,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(77,77,2,'2026-11-12',11,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(78,78,2,'2026-11-12',12,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(79,79,2,'2026-11-12',13,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(80,80,2,'2026-11-12',14,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(81,81,2,'2026-11-12',15,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(82,82,2,'2026-11-12',16,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(83,83,2,'2026-11-12',17,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(84,84,2,'2026-11-12',18,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(85,85,2,'2026-11-12',19,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(86,86,2,'2026-11-12',20,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(87,87,2,'2026-11-12',21,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(88,88,2,'2026-11-12',22,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(89,89,2,'2026-11-12',23,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(90,90,2,'2026-11-12',24,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24'),(91,91,2,'2026-11-12',25,0,'NONE','WAITING',NULL,NULL,'2026-11-11 15:18:24');
/*!40000 ALTER TABLE `queue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('PATIENT','DOCTOR','ADMIN') NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'doctor1','doctor123','DOCTOR','Dr. Ananya Iyer','ananya.iyer@hospital.com','9876543211','2026-10-08 13:05:14'),(2,'doctor2','doctor123','DOCTOR','Dr. Rohan Mehta','rohan.mehta@hospital.com','9876543212','2026-10-08 13:05:14'),(3,'doctor3','doctor123','DOCTOR','Dr. Kavya Menon','kavya.menon@hospital.com','9876543213','2026-10-08 13:05:14'),(4,'doctor4','doctor123','DOCTOR','Dr. Arjun Rao','arjun.rao@hospital.com','9876543214','2026-10-08 13:05:14'),(5,'doctor5','doctor123','DOCTOR','Dr. Sneha Kapoor','sneha.kapoor@hospital.com','9876543215','2026-10-08 13:05:14'),(6,'admin1','admin123','ADMIN','Hospital Administrator','admin@hospital.com','9876543200','2026-10-08 13:06:38'),(7,'patient01','patient123','PATIENT','Aarav Sharma','aarav.sharma@hospital.com','9000000001','2026-10-08 13:07:34'),(8,'patient02','patient123','PATIENT','Diya Patel','diya.patel@hospital.com','9000000002','2026-10-08 13:07:34'),(9,'patient03','patient123','PATIENT','Arjun Nair','arjun.nair@hospital.com','9000000003','2026-10-08 13:07:34'),(10,'patient04','patient123','PATIENT','Ananya Singh','ananya.singh@hospital.com','9000000004','2026-10-08 13:07:34'),(11,'patient05','patient123','PATIENT','Vivaan Mehta','vivaan.mehta@hospital.com','9000000005','2026-10-08 13:07:34'),(12,'patient06','patient123','PATIENT','Ishita Rao','ishita.rao@hospital.com','9000000006','2026-10-08 13:07:34'),(13,'patient07','patient123','PATIENT','Aditya Kumar','aditya.kumar@hospital.com','9000000007','2026-10-08 13:07:34'),(14,'patient08','patient123','PATIENT','Meera Iyer','meera.iyer@hospital.com','9000000008','2026-10-08 13:07:34'),(15,'patient09','patient123','PATIENT','Kabir Malhotra','kabir.malhotra@hospital.com','9000000009','2026-10-08 13:07:34'),(16,'patient10','patient123','PATIENT','Saanvi Kapoor','saanvi.kapoor@hospital.com','9000000010','2026-10-08 13:07:34'),(17,'patient11','patient123','PATIENT','Reyansh Verma','reyansh.verma@hospital.com','9000000011','2026-10-08 13:07:34'),(18,'patient12','patient123','PATIENT','Myra Joshi','myra.joshi@hospital.com','9000000012','2026-10-08 13:07:34'),(19,'patient13','patient123','PATIENT','Atharv Desai','atharv.desai@hospital.com','9000000013','2026-10-08 13:07:34'),(20,'patient14','patient123','PATIENT','Kiara Shah','kiara.shah@hospital.com','9000000014','2026-10-08 13:07:34'),(21,'patient15','patient123','PATIENT','Vihaan Reddy','vihaan.reddy@hospital.com','9000000015','2026-10-08 13:07:34'),(22,'patient16','patient123','PATIENT','Aadhya Menon','aadhya.menon@hospital.com','9000000016','2026-10-08 13:07:34'),(23,'patient17','patient123','PATIENT','Dhruv Agarwal','dhruv.agarwal@hospital.com','9000000017','2026-10-08 13:07:34'),(24,'patient18','patient123','PATIENT','Ira Chatterjee','ira.chatterjee@hospital.com','9000000018','2026-10-08 13:07:34'),(25,'patient19','patient123','PATIENT','Rudra Banerjee','rudra.banerjee@hospital.com','9000000019','2026-10-08 13:07:34'),(26,'patient20','patient123','PATIENT','Avni Kulkarni','avni.kulkarni@hospital.com','9000000020','2026-10-08 13:07:34'),(27,'patient21','patient123','PATIENT','Krish Bansal','krish.bansal@hospital.com','9000000021','2026-10-08 13:07:34'),(28,'patient22','patient123','PATIENT','Navya Sinha','navya.sinha@hospital.com','9000000022','2026-10-08 13:07:34'),(29,'patient23','patient123','PATIENT','Yuvaan Gupta','yuvaan.gupta@hospital.com','9000000023','2026-10-08 13:07:34'),(30,'patient24','patient123','PATIENT','Riya Mukherjee','riya.mukherjee@hospital.com','9000000024','2026-10-08 13:07:34'),(31,'patient25','patient123','PATIENT','Aryan Thakur','aryan.thakur@hospital.com','9000000025','2026-10-08 13:07:34'),(32,'patient26','patient123','PATIENT','Tara Pillai','tara.pillai@hospital.com','9000000026','2026-10-08 13:07:34'),(33,'patient27','patient123','PATIENT','Ritvik Saxena','ritvik.saxena@hospital.com','9000000027','2026-10-08 13:07:34'),(34,'patient28','patient123','PATIENT','Nisha Raghavan','nisha.raghavan@hospital.com','9000000028','2026-10-08 13:07:34'),(35,'patient29','patient123','PATIENT','Karan Arora','karan.arora@hospital.com','9000000029','2026-10-08 13:07:34'),(36,'patient30','patient123','PATIENT','Simran Kaur','simran.kaur@hospital.com','9000000030','2026-10-08 13:07:34'),(37,'patient31','patient123','PATIENT','Devansh Mishra','devansh.mishra@hospital.com','9000000031','2026-10-08 13:07:34'),(38,'patient32','patient123','PATIENT','Pihu Yadav','pihu.yadav@hospital.com','9000000032','2026-10-08 13:07:34'),(39,'patient33','patient123','PATIENT','Raghav Tiwari','raghav.tiwari@hospital.com','9000000033','2026-10-08 13:07:34'),(40,'patient34','patient123','PATIENT','Shreya Bose','shreya.bose@hospital.com','9000000034','2026-10-08 13:07:34'),(41,'patient35','patient123','PATIENT','Manav Choudhary','manav.choudhary@hospital.com','9000000035','2026-10-08 13:07:34'),(42,'patient36','patient123','PATIENT','Aisha Khan','aisha.khan@hospital.com','9000000036','2026-10-08 13:07:34'),(43,'patient37','patient123','PATIENT','Siddharth Jain','siddharth.jain@hospital.com','9000000037','2026-10-08 13:07:34'),(44,'patient38','patient123','PATIENT','Mahi Verma','mahi.verma@hospital.com','9000000038','2026-10-08 13:07:34'),(45,'patient39','patient123','PATIENT','Harsh Vardhan','harsh.vardhan@hospital.com','9000000039','2026-10-08 13:07:34'),(46,'patient40','patient123','PATIENT','Tanvi Sethi','tanvi.sethi@hospital.com','9000000040','2026-10-08 13:07:34'),(47,'patient41','patient123','PATIENT','Ayush Pandey','ayush.pandey@hospital.com','9000000041','2026-10-08 13:07:34'),(48,'patient42','patient123','PATIENT','Rhea Dutta','rhea.dutta@hospital.com','9000000042','2026-10-08 13:07:34'),(49,'patient43','patient123','PATIENT','Parth Trivedi','parth.trivedi@hospital.com','9000000043','2026-10-08 13:07:34'),(50,'patient44','patient123','PATIENT','Ishaan Roy','ishaan.roy@hospital.com','9000000044','2026-10-08 13:07:34'),(51,'patient45','patient123','PATIENT','Neha Srivastava','neha.srivastava@hospital.com','9000000045','2026-10-08 13:07:34'),(52,'patient46','patient123','PATIENT','Samar Kapoor','samar.kapoor@hospital.com','9000000046','2026-10-08 13:07:34'),(53,'patient47','patient123','PATIENT','Aditi Bhat','aditi.bhat@hospital.com','9000000047','2026-10-08 13:07:34'),(54,'patient48','patient123','PATIENT','Varun Shetty','varun.shetty@hospital.com','9000000048','2026-10-08 13:07:34'),(55,'patient49','patient123','PATIENT','Manya Khanna','manya.khanna@hospital.com','9000000049','2026-10-08 13:07:34'),(56,'patient50','patient123','PATIENT','Nikhil Suri','nikhil.suri@hospital.com','9000000050','2026-10-08 13:07:34');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-11 21:01:48
