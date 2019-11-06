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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (8,'a','86f7e437faa5a7fce15d1ddcb9eaeaea377667b8','ADMIN','Head','John Rey','Alipe','Alcances'),(9,'b','e9d71f5ee7c92d6dc9e92ffdad17b8bd49418f98','MSWDO','Head','b','b','b');
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
  `loc_long` double DEFAULT '10.986046',
  `loc_lat` double DEFAULT '122.536953',
  PRIMARY KEY (`bene_id`),
  KEY `fk_brgy_id_beneficiary_idx` (`fk_brgy_id_beneficiary`),
  CONSTRAINT `fk_brgy_id_beneficiary` FOREIGN KEY (`fk_brgy_id_beneficiary`) REFERENCES `brgy` (`brgy_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `beneficiary`
--

LOCK TABLES `beneficiary` WRITE;
/*!40000 ALTER TABLE `beneficiary` DISABLE KEYS */;
INSERT INTO `beneficiary` VALUES (1,'John Rey','Alcances','Alipe','Male','1988-06-25',6,'Not Applicable','No','No','Masters','Ilonggo',50000,'Teacher','Not Applicable','Not Applicable','Partially Damaged','09494369008',0,0),(4,'Jasmin','Deocariza','Alipe','Female','1997-03-24',5,'Lactating Mother','No','No','College','Gwapa',10000,'Med Tech','Not Applicable','House owner, rent-free lot without consent of the owner','Partially Damaged','094999999999',0,0),(5,'ddd','ddd','ddd','Male','1988-10-14',5,'Not Applicable','No','No','High School','asdf',10000,'Farmer','Missing','Rented house and lot','Partially Damaged','0',0,0),(6,'ffff','ff','ffffff','Male','1988-06-25',5,'Not Applicable','Yes','Yes','Elementary','asdf',0,'Farmer','Not Applicable','Not Applicable','Not Applicable','0',0,0),(7,'hhh','hhh','hhhh','Male','1879-10-10',5,'Not Applicable','Yes','Yes','Elementary','hhhh',10000,'Farmer','Not Applicable','Not Applicable','Not Applicable','0',0,0),(8,'John Rey','Alcances','Alipe','Male','1988-06-25',5,'Not Applicable','No','No','Masters','Ilonggo',50000,'Farmer','Not Applicable','Rent-free house and lot with owner\'s consent','Partially Damaged','09494369008',0,0),(10,'d','d','d','Male','2019-10-09',5,'Not Applicable','Yes','Yes','Elementary','',0,'','Not Applicable','Not Applicable','Not Applicable','0',0,0),(18,'sdf','','sdf','Male','2019-10-09',5,'Not Applicable','Yes','Yes','Elementary','',0,'Farmer','Not Applicable','Not Applicable','Not Applicable','0',0,0),(20,'llll','llll','llll','Male','2019-10-11',5,'Not Applicable','Yes','Yes','Elementary','',0,'','Not Applicable','Not Applicable','Not Applicable','0',0,0),(21,'pppp','ppp','ppppp','Male','2019-10-17',5,'Not Applicable','Yes','Yes','Elementary','pppp',5,'Farmer','Missing','House and lot owner','Totally Damaged','88888',122.53738403320312,10.984840694937644),(22,'john','as','aff','Male','1988-10-14',6,'PWD','Yes','Yes','Elementary','',0,'','Not Applicable','Not Applicable','Not Applicable','0',0,0),(23,'ff','fff','ffff','Male','2019-10-17',5,'Not Applicable','Yes','Yes','Elementary','',7,'','Not Applicable','Not Applicable','Not Applicable','0',122.53910064697266,10.954337700134095),(24,'lemuel john','aaaa','galve','Male','1997-10-11',4,'PWD','Yes','Yes','Doctoral','Ilonggo',200000,'Farmer','With Illness','House owner, rent-free lot without consent of the owner','Totally Damaged','3333333',122.5393795967102,10.9879160983652),(25,'a','a','a','Male','2019-10-17',5,'Not Applicable','Yes','Yes','Elementary','',0,'Farmer','Not Applicable','Not Applicable','Not Applicable','0',122.52725601196289,10.984503662476728),(26,'ssssss','sssssss','sssssssss','Male','1988-10-14',5,'Lactating Mother','Yes','Yes','College','sssssss',20000,'Farmer','Missing','Rented house and lot','Partially Damaged','9999999999999',122.53377914428711,10.984840694937644),(27,'new ','new','new','Male','2019-10-20',5,'Not Applicable','Yes','Yes','Elementary','',0,'','Not Applicable','Not Applicable','Not Applicable','0',122.52674102783203,10.958888070314618),(28,'sdf','sdf','sdf','Male','2019-10-20',5,'Not Applicable','Yes','Yes','Elementary','sdf',0,'Farmer','Not Applicable','Not Applicable','Not Applicable','sdf',122.52862930297852,10.989727622371035),(29,'sdf','sdf','sdf','Male','2019-10-20',5,'Not Applicable','Yes','Yes','Elementary','',0,'Farmer','Not Applicable','Not Applicable','Not Applicable','0',122.53154754638672,10.980627761520783),(30,'aa','','aaaa','Male','2019-10-20',5,'Not Applicable','Yes','Yes','Elementary','',0,'','Not Applicable','Not Applicable','Not Applicable','0',122.5385856628418,10.986525851469693),(31,'as','','sdf','Male','2019-10-20',5,'Not Applicable','Yes','Yes','Elementary','',0,'','Not Applicable','Not Applicable','Not Applicable','0',122.53697097301483,10.986025571628419);
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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crop`
--

LOCK TABLES `crop` WRITE;
/*!40000 ALTER TABLE `crop` DISABLE KEYS */;
INSERT INTO `crop` VALUES (7,6,'sdf','sdf','sdf','sdf','2019-10-07','sdf','Planted'),(8,6,'sdf','sdf','sdf','0.0','2019-10-07','sdfsdf','Planted'),(9,1,'Lubi','iloilo','var','class','2019-10-08','rem','Planted'),(12,6,'grasya','asdf','asdf','asdfa','2019-10-08','asdfasdf','Harvested'),(14,8,'Lubi','Banate','var1','Class','2020-10-05','Rem1','Planted'),(15,8,'Manok','class1','2','6.0','2019-11-14','rem1','Harvested'),(21,21,'pppp','pp','ppp','ppp','2019-10-11','pppp','Planted'),(22,4,'a','a','a','a','2019-10-17','a','Harvested'),(23,8,'f','fa','af','af','2019-10-17','af',NULL),(24,4,'a','a','a','a','2019-10-17','a','Planted'),(25,26,'ssss','ssss','ssss','ssss','2019-10-17','sssss','Planted'),(26,28,'sdf','','','','2019-10-20','sdf','Planted');
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
INSERT INTO `crop_season` VALUES (1,15,'saku',5,4,'2019-10-14','sdfasdf'),(6,12,'Truck',9,20000003,'2019-10-12','damu'),(7,22,'saku',22,222,'2019-10-17','sdfsadf');
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
  `long` double DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `radius` double DEFAULT NULL,
  `remarks` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`dis_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `disaster`
--

LOCK TABLES `disaster` WRITE;
/*!40000 ALTER TABLE `disaster` DISABLE KEYS */;
INSERT INTO `disaster` VALUES (1,'typhoon','auring','2019-09-09',0.1,0.2,3,'baskog'),(4,'Riot','jailbreak','2019-10-15',2.13155515151512,1.2365664021,5.15465132121241,'remarks'),(5,'Famine','Undang','2019-10-09',2.3333333,10.1545451565165,3.223111611213,'sdfsdfsdf');
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
  `fk_brgy_id_evac` int(11) DEFAULT NULL,
  `long` double DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`evac_id`),
  KEY `brgy_id_idx` (`fk_brgy_id_evac`),
  CONSTRAINT `fk_brgy_id_evac` FOREIGN KEY (`fk_brgy_id_evac`) REFERENCES `brgy` (`brgy_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evac`
--

LOCK TABLES `evac` WRITE;
/*!40000 ALTER TABLE `evac` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evac_event`
--

LOCK TABLES `evac_event` WRITE;
/*!40000 ALTER TABLE `evac_event` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `far`
--

LOCK TABLES `far` WRITE;
/*!40000 ALTER TABLE `far` DISABLE KEYS */;
INSERT INTO `far` VALUES (5,8,5,'Undang','2019-10-17','Cash',3,3,'dfdf'),(6,22,NULL,'wala','2019-10-17','Cash',3,3,'ffff'),(7,1,NULL,'gggggg','2019-10-17','Cash',5,7,'sdfsdf'),(9,4,NULL,'sdfdfdf','2019-10-17','Cash',2,3,'sdfsdf'),(12,1,NULL,'feeding','2019-10-17','Construction Materials',2,3,'sdfsdf'),(13,4,NULL,'gurang','2019-10-17','Cash',3,4,'sdfsdf');
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fmember`
--

LOCK TABLES `fmember` WRITE;
/*!40000 ALTER TABLE `fmember` DISABLE KEYS */;
INSERT INTO `fmember` VALUES (8,6,'Zane Aldrick','Deocariza','Alipe','Father',12,'Male','None','','Bata ni nonoy'),(9,1,'Jasmin','Deocariza','Alipe','Husband',22,'Female','College','MedTech','asawa ni nonoy'),(10,6,'jason','sdf','sdf','Son',5,'Male','Elementary','sdf','sdf'),(11,1,'fn','mn','ln','Aunt',555,'Female','College','occc','rem'),(12,8,'Zane Aldrick','Deocariza','Alipe','Father',1,'Male','None','Sleeping','Bata ko'),(13,8,'Jasmin','Rendon','Deocariza','Wife',22,'Female','College','Medtech','Wife ko'),(17,21,'pppp','ppp','pppp','Aunt',0,'Male','Elementary','pppp','pppp'),(18,22,'Zane','sdf','sdf','Father',1,'Male','College','sdfsdf','sdfsdf'),(19,24,'meia','asdf','galve','Husband',27,'Female','Doctoral','nurse','ayus'),(20,26,'sss','sss','sss','Daughter',9,'Female','Elementary','sssss','sssss'),(21,28,'sdf','','','Aunt',0,'Male','Elementary','','sdf');
/*!40000 ALTER TABLE `fmember` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `livestock`
--

LOCK TABLES `livestock` WRITE;
/*!40000 ALTER TABLE `livestock` DISABLE KEYS */;
INSERT INTO `livestock` VALUES (3,1,'manok','pula',5,6,'2019-10-10','manok na pula ni tatay','Disposed'),(4,6,'ls','cl',12,11,'2020-10-14','pula asdfasdfsa','Disposed'),(6,21,'pppp','pppp',4,6,'2019-10-11','ppppp','Disposed'),(7,4,'f','f',2,4,'2019-10-17','f','Disposed'),(8,26,'ssss','ssss',5,6,'2019-10-17','ssssss','Disposed'),(9,28,'sdf','',0,0,'2019-10-20','sfd','Held');
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
INSERT INTO `ls_season` VALUES (2,6,500000,'2019-10-14','sdfsdfsdf'),(4,4,123123123,'2019-10-14','sdfsdfsdf'),(5,7,500000,'2019-10-17','fasdfsd'),(6,8,30000,'2019-10-17','sdfsdfsd');
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registration`
--

LOCK TABLES `registration` WRITE;
/*!40000 ALTER TABLE `registration` DISABLE KEYS */;
INSERT INTO `registration` VALUES (1,8,23,'Yes','Family Planning','2019-10-17'),(2,8,24,'Yes','Legal Assistance','2019-10-17'),(3,8,25,'Yes','Not Applicable','2019-10-17'),(5,8,27,'Yes','Not Applicable','2019-10-20'),(6,8,28,'Yes','Not Applicable','2019-10-20'),(7,8,30,'Yes','Not Applicable','2019-10-20'),(8,8,31,'Yes','Not Applicable','2019-10-20');
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

-- Dump completed on 2019-10-23 15:18:04
