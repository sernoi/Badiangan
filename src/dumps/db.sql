CREATE DATABASE  IF NOT EXISTS `badiangan` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `badiangan`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: badiangan
-- ------------------------------------------------------
-- Server version	5.7.16-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `department` varchar(45) NOT NULL,
  `position` varchar(45) NOT NULL,
  `fname` varchar(45) NOT NULL,
  `lname` varchar(45) NOT NULL,
  `mname` varchar(45) NOT NULL,
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (8,'admin','86f7e437faa5a7fce15d1ddcb9eaeaea377667b8','ADMIN','Head','John Rey','Alipe','Alcances'),(9,'b','e9d71f5ee7c92d6dc9e92ffdad17b8bd49418f98','MSWDO','Head','b','b','b'),(10,'c','84a516841ba77a5b4648de2cd0dfcb30ea46dbb4','MDRRMO','Head','c','c','c'),(11,'d','3c363836cf4e16666669a25da280a1865c2d2874','MAO','Head','d','d','d'),(12,'e','58e6b3a414a1e090dfc6029add0f3555ccba127f','MPDO','Head','e','e','e'),(13,'viewer','7110eda4d09e062aa5e4a390b0a572ac0d2c0220','VIEWER','Head','z','z','z'),(14,'gab','26cc3217be640e8220112c25628da6e11c78db95','MSWDO','Head','gabrielle ramses','Haro','s'),(15,'gg','b8d09b4d8580aacbd9efc4540a9b88d2feb9d7e5','MAO','Head','','',''),(16,'dd','388ad1c312a488ee9e12998fe097f2258fa8d5ee','MDRRMO','Head','','',''),(17,'vv','4cf997735475afd79f8711e22efaa9d306294785','MPDO','Head','','',''),(18,'bb','9a900f538965a426994e1e90600920aff0b4e8d2','VIEWER','Head','','','');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `beneficiary`
--

DROP TABLE IF EXISTS `beneficiary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `beneficiary` (
  `bene_id` int(11) NOT NULL AUTO_INCREMENT,
  `fname` varchar(100) NOT NULL,
  `mname` varchar(100) DEFAULT NULL,
  `lname` varchar(100) NOT NULL,
  `sex` varchar(45) DEFAULT 'Male',
  `dob` date DEFAULT NULL,
  `fk_brgy_id_beneficiary` int(11) DEFAULT NULL,
  `code` varchar(45) DEFAULT NULL,
  `fourps` varchar(45) DEFAULT NULL,
  `ip` varchar(45) DEFAULT NULL,
  `hea` varchar(100) DEFAULT NULL,
  `ethnicity` varchar(100) DEFAULT NULL,
  `net_income` double DEFAULT '0',
  `occ` varchar(200) DEFAULT NULL,
  `health_condition` varchar(100) DEFAULT NULL,
  `house_status` varchar(100) DEFAULT NULL,
  `house_condition` varchar(100) DEFAULT NULL,
  `contact_num` varchar(45) DEFAULT NULL,
  `loc_lat` double DEFAULT '122.536953',
  `loc_long` double DEFAULT '10.986046',
  PRIMARY KEY (`bene_id`),
  KEY `fk_brgy_id_beneficiary_idx` (`fk_brgy_id_beneficiary`),
  CONSTRAINT `fk_brgy_id_beneficiary` FOREIGN KEY (`fk_brgy_id_beneficiary`) REFERENCES `brgy` (`brgy_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `beneficiary`
--

LOCK TABLES `beneficiary` WRITE;
/*!40000 ALTER TABLE `beneficiary` DISABLE KEYS */;
INSERT INTO `beneficiary` VALUES (1,'Ernesto','Enarsao','Espada','Male','1965-11-11',5,'PWD','Yes','Yes','Elementary','Ati',10000,'Farmer','Not Applicable','House owner, rent-free lot with owner\'s consent','Totally Damaged','09443326514',10.986499520972874,122.53800094127655),(2,'Asuncion','Te','Go','Female','1956-11-09',4,'Not Applicable','Yes','No','Elementary','',12000,'Farmer','Not Applicable','Not Applicable','Not Applicable','',11.00202883992389,122.54547357559204);
/*!40000 ALTER TABLE `beneficiary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brgy`
--

DROP TABLE IF EXISTS `brgy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brgy` (
  `brgy_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`brgy_id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brgy`
--

LOCK TABLES `brgy` WRITE;
/*!40000 ALTER TABLE `brgy` DISABLE KEYS */;
INSERT INTO `brgy` VALUES (5,'Carmelo'),(4,'Poblacion'),(6,'Zona Sur');
/*!40000 ALTER TABLE `brgy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crop`
--

DROP TABLE IF EXISTS `crop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crop` (
  `crop_id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_bene_id_crop` int(11) DEFAULT NULL,
  `crop` varchar(45) DEFAULT NULL,
  `area` varchar(45) DEFAULT NULL,
  `variety` varchar(45) DEFAULT NULL,
  `classification` varchar(45) DEFAULT NULL,
  `exp` date DEFAULT NULL,
  `remarks` varchar(1000) DEFAULT NULL,
  `status` varchar(45) DEFAULT 'Planted',
  PRIMARY KEY (`crop_id`),
  KEY `bene_id_idx` (`fk_bene_id_crop`),
  CONSTRAINT `fk_bene_id_crop` FOREIGN KEY (`fk_bene_id_crop`) REFERENCES `beneficiary` (`bene_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crop`
--

LOCK TABLES `crop` WRITE;
/*!40000 ALTER TABLE `crop` DISABLE KEYS */;
INSERT INTO `crop` VALUES (31,1,'Rice','2','R1','Irrigated','2019-11-22','','Planted');
/*!40000 ALTER TABLE `crop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crop_season`
--

DROP TABLE IF EXISTS `crop_season`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crop_season` (
  `cs_id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_crop_id_cs` int(11) DEFAULT NULL,
  `form` varchar(45) DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `profit` double DEFAULT NULL,
  `date` date DEFAULT NULL,
  `remarks` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`cs_id`),
  KEY `farm_id_idx` (`fk_crop_id_cs`),
  CONSTRAINT `fk_crop_id_cs` FOREIGN KEY (`fk_crop_id_cs`) REFERENCES `crop` (`crop_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crop_season`
--

LOCK TABLES `crop_season` WRITE;
/*!40000 ALTER TABLE `crop_season` DISABLE KEYS */;
/*!40000 ALTER TABLE `crop_season` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `disaster`
--

DROP TABLE IF EXISTS `disaster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `disaster` (
  `dis_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `long` double DEFAULT NULL,
  `radius` double DEFAULT NULL,
  `remarks` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`dis_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `disaster`
--

LOCK TABLES `disaster` WRITE;
/*!40000 ALTER TABLE `disaster` DISABLE KEYS */;
INSERT INTO `disaster` VALUES (13,'Bioterrorism','aaa','2019-11-03',10.989727622371035,122.55626678466797,3000,'');
/*!40000 ALTER TABLE `disaster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evac`
--

DROP TABLE IF EXISTS `evac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evac` (
  `evac_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `long` double DEFAULT NULL,
  PRIMARY KEY (`evac_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evac`
--

LOCK TABLES `evac` WRITE;
/*!40000 ALTER TABLE `evac` DISABLE KEYS */;
INSERT INTO `evac` VALUES (15,'aaa',11.00455642379427,122.5198745727539);
/*!40000 ALTER TABLE `evac` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evac_event`
--

DROP TABLE IF EXISTS `evac_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evac_event` (
  `evac_event_id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_evac_id_evac_event` int(11) DEFAULT NULL,
  `fk_dis_id_evac_event` int(11) DEFAULT NULL,
  `fk_bene_id_evac_event` int(11) DEFAULT NULL,
  PRIMARY KEY (`evac_event_id`),
  KEY `evac_id_idx` (`fk_evac_id_evac_event`),
  KEY `dis_id_idx` (`fk_dis_id_evac_event`),
  KEY `bene_id_idx` (`fk_bene_id_evac_event`),
  CONSTRAINT `fk_bene_id_evac_event` FOREIGN KEY (`fk_bene_id_evac_event`) REFERENCES `beneficiary` (`bene_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_dis_id_evac_event` FOREIGN KEY (`fk_dis_id_evac_event`) REFERENCES `disaster` (`dis_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_evac_id_evac_event` FOREIGN KEY (`fk_evac_id_evac_event`) REFERENCES `evac` (`evac_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evac_event`
--

LOCK TABLES `evac_event` WRITE;
/*!40000 ALTER TABLE `evac_event` DISABLE KEYS */;
INSERT INTO `evac_event` VALUES (27,15,13,1),(28,15,13,2);
/*!40000 ALTER TABLE `evac_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `far`
--

DROP TABLE IF EXISTS `far`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `far` (
  `far_id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_bene_id_far` int(11) DEFAULT NULL,
  `fk_dis_id_far` int(11) DEFAULT NULL,
  `during` varchar(200) DEFAULT NULL COMMENT 'Assistance provided during',
  `date` date DEFAULT NULL,
  `type` varchar(200) DEFAULT NULL COMMENT 'Kind/Type (Cash or items)',
  `qty` int(11) DEFAULT NULL,
  `cost` double DEFAULT NULL,
  `provider` varchar(200) DEFAULT NULL COMMENT 'Company/Individual',
  PRIMARY KEY (`far_id`),
  KEY `dis_id_idx` (`fk_dis_id_far`),
  KEY `fk_bene_id_far_idx` (`fk_bene_id_far`),
  CONSTRAINT `fk_bene_id_far` FOREIGN KEY (`fk_bene_id_far`) REFERENCES `beneficiary` (`bene_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_dis_id_far` FOREIGN KEY (`fk_dis_id_far`) REFERENCES `disaster` (`dis_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `far`
--

LOCK TABLES `far` WRITE;
/*!40000 ALTER TABLE `far` DISABLE KEYS */;
/*!40000 ALTER TABLE `far` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fmember`
--

DROP TABLE IF EXISTS `fmember`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fmember` (
  `fmem_id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_bene_id_member` int(11) DEFAULT NULL,
  `fname` varchar(45) DEFAULT NULL,
  `mname` varchar(45) DEFAULT NULL,
  `lname` varchar(45) DEFAULT NULL,
  `rel_to_hod` varchar(45) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `sex` varchar(45) DEFAULT NULL,
  `educ` varchar(100) DEFAULT NULL,
  `occ_skills` varchar(200) DEFAULT NULL,
  `remarks` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`fmem_id`),
  KEY `bene_id_idx` (`fk_bene_id_member`),
  CONSTRAINT `fk_bene_id_member` FOREIGN KEY (`fk_bene_id_member`) REFERENCES `beneficiary` (`bene_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fmember`
--

LOCK TABLES `fmember` WRITE;
/*!40000 ALTER TABLE `fmember` DISABLE KEYS */;
INSERT INTO `fmember` VALUES (24,1,'Domingo','Jaro','Espada','Father',10,'Male','Elementary','',''),(25,1,'Maricel','Jaro','Espada','Father',8,'Female','Elementary','',''),(26,1,'Cecilia','Jaro','Espada','Husband',48,'Female','High School','Labandera',''),(27,2,'Aurelio','Simon','Go','Wife',63,'Male','High School','Farmer',''),(28,2,'Fernando','Simon','Go','Mother',23,'Male','Vocational','Technician',''),(29,2,'Maria','Simon','Go','Mother',20,'Female','Vocational','',''),(30,2,'Adeline','Simon','Go','Mother',14,'Female','Elementary','','');
/*!40000 ALTER TABLE `fmember` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hazard`
--

DROP TABLE IF EXISTS `hazard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hazard` (
  `haz_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) DEFAULT NULL,
  `lt` double DEFAULT NULL,
  `lg` double DEFAULT NULL,
  PRIMARY KEY (`haz_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hazard`
--

LOCK TABLES `hazard` WRITE;
/*!40000 ALTER TABLE `hazard` DISABLE KEYS */;
/*!40000 ALTER TABLE `hazard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `livestock`
--

DROP TABLE IF EXISTS `livestock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `livestock` (
  `ls_id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_bene_id_livestock` int(11) DEFAULT NULL,
  `livestock_raised` varchar(100) DEFAULT NULL,
  `classification` varchar(100) DEFAULT NULL,
  `heads` int(11) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `exp` date DEFAULT NULL,
  `remarks` varchar(1000) DEFAULT NULL,
  `status` varchar(45) DEFAULT 'Held',
  PRIMARY KEY (`ls_id`),
  KEY `bene_id_idx` (`fk_bene_id_livestock`),
  CONSTRAINT `fk_bene_id_livestock` FOREIGN KEY (`fk_bene_id_livestock`) REFERENCES `beneficiary` (`bene_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `livestock`
--

LOCK TABLES `livestock` WRITE;
/*!40000 ALTER TABLE `livestock` DISABLE KEYS */;
INSERT INTO `livestock` VALUES (14,1,'Chicken','Jumbo',8,4,'2019-11-14','','Held'),(15,2,'Pig','Fattening',6,4,'2019-12-18','','Held');
/*!40000 ALTER TABLE `livestock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ls_season`
--

DROP TABLE IF EXISTS `ls_season`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ls_season` (
  `ls_season_id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_ls_id_ls_season` int(11) DEFAULT NULL,
  `profit` double DEFAULT NULL,
  `date` date DEFAULT NULL,
  `remarks` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`ls_season_id`),
  KEY `ls_id_idx` (`fk_ls_id_ls_season`),
  CONSTRAINT `fk_ls_id_ls_season` FOREIGN KEY (`fk_ls_id_ls_season`) REFERENCES `livestock` (`ls_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ls_season`
--

LOCK TABLES `ls_season` WRITE;
/*!40000 ALTER TABLE `ls_season` DISABLE KEYS */;
/*!40000 ALTER TABLE `ls_season` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registration`
--

DROP TABLE IF EXISTS `registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registration` (
  `reg_id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_admin_id_registration` int(11) DEFAULT NULL,
  `fk_bene_id_registration` int(11) DEFAULT NULL,
  `walkin_status` varchar(45) DEFAULT NULL,
  `case` varchar(45) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`reg_id`),
  KEY `fk_admin_id_registration_idx` (`fk_admin_id_registration`),
  KEY `fk_bene_id_registration_idx` (`fk_bene_id_registration`),
  CONSTRAINT `fk_admin_id_registration` FOREIGN KEY (`fk_admin_id_registration`) REFERENCES `admin` (`admin_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_bene_id_registration` FOREIGN KEY (`fk_bene_id_registration`) REFERENCES `beneficiary` (`bene_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registration`
--

LOCK TABLES `registration` WRITE;
/*!40000 ALTER TABLE `registration` DISABLE KEYS */;
INSERT INTO `registration` VALUES (18,8,1,'Yes','Not Applicable','2019-11-03'),(19,8,2,'Yes','Children and Women Abuse','2019-11-03');
/*!40000 ALTER TABLE `registration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'badiangan'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-03 15:57:17
