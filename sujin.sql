-- MySQL dump 10.16  Distrib 10.2.10-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: application
-- ------------------------------------------------------
-- Server version	10.2.10-MariaDB

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
  `ADMINNUM` int(11) NOT NULL AUTO_INCREMENT,
  `ADMINID` char(50) NOT NULL,
  `ADMINPW` char(50) NOT NULL,
  PRIMARY KEY (`ADMINNUM`),
  UNIQUE KEY `ADMINID` (`ADMINID`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (21,'skdlf','slkd'),(22,'sldkjf','sldkf'),(23,'jeongjh224','kang0909'),(24,'sss','slfl'),(25,'kasdl','sld'),(26,'kakakakakak','sldkfjlskdjf'),(27,'kangsj1','ahh'),(28,'sujin','kang'),(29,'cuteyujin','prettyyj'),(30,'skd','sss'),(31,'sfd','sss'),(32,'kangsjsj','abcdefg'),(33,'aa','bb'),(34,'abb','abb');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `music`
--

DROP TABLE IF EXISTS `music`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `music` (
  `MNUM` int(11) NOT NULL AUTO_INCREMENT,
  `TITLE` char(50) NOT NULL DEFAULT '0',
  `ADDNUM` int(11) NOT NULL DEFAULT 0,
  `GENRE` int(11) NOT NULL DEFAULT 1,
  `ALBUMNAME` char(50) NOT NULL DEFAULT '0',
  `SINGERN` int(11) NOT NULL,
  PRIMARY KEY (`MNUM`),
  KEY `SINGERN` (`SINGERN`),
  CONSTRAINT `SINGERN` FOREIGN KEY (`SINGERN`) REFERENCES `singer` (`SINGERNUM`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `music`
--

LOCK TABLES `music` WRITE;
/*!40000 ALTER TABLE `music` DISABLE KEYS */;
INSERT INTO `music` VALUES (4,'kk',3,2,'alall',1),(8,'sujin',1,3,'a',4),(10,'bbb',2,8,'cc',7),(11,'abc',2,6,'bcd',8),(12,'hi',1,2,'hello',2),(17,'heedong',1,4,'hdhd',7),(18,'pick-a-boo',0,7,'velel',13),(19,'aaaa',1,5,'hi',14);
/*!40000 ALTER TABLE `music` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlist`
--

DROP TABLE IF EXISTS `playlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `playlist` (
  `UNUM` int(11) NOT NULL,
  `MNUM` int(11) NOT NULL,
  `SELECTDATE` datetime NOT NULL DEFAULT current_timestamp(),
  `FAVORITE` int(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`UNUM`,`MNUM`),
  KEY `MNUM` (`MNUM`),
  CONSTRAINT `MNUM` FOREIGN KEY (`MNUM`) REFERENCES `music` (`MNUM`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `UNUM` FOREIGN KEY (`UNUM`) REFERENCES `user` (`UNUM`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlist`
--

LOCK TABLES `playlist` WRITE;
/*!40000 ALTER TABLE `playlist` DISABLE KEYS */;
INSERT INTO `playlist` VALUES (2,4,'2017-11-30 22:40:03',1),(3,4,'2017-11-30 22:11:59',0),(3,10,'2017-12-01 01:26:32',1),(3,11,'2017-12-01 01:26:43',1),(14,4,'2017-12-04 15:13:45',1),(14,8,'2017-12-01 01:33:47',0),(14,17,'2017-12-05 15:02:41',1),(14,19,'2017-12-05 15:03:19',1),(15,11,'2017-12-02 19:34:56',1);
/*!40000 ALTER TABLE `playlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchaselist`
--

DROP TABLE IF EXISTS `purchaselist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchaselist` (
  `MUSICNUM` int(11) NOT NULL,
  `USERNUM` int(11) NOT NULL,
  `PURCHASEDATE` datetime NOT NULL DEFAULT current_timestamp(),
  `COMMENT` char(100) NOT NULL,
  PRIMARY KEY (`MUSICNUM`,`USERNUM`),
  KEY `USERNUM` (`USERNUM`),
  CONSTRAINT `MUSICNUM` FOREIGN KEY (`MUSICNUM`) REFERENCES `music` (`MNUM`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `USERNUM` FOREIGN KEY (`USERNUM`) REFERENCES `user` (`UNUM`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchaselist`
--

LOCK TABLES `purchaselist` WRITE;
/*!40000 ALTER TABLE `purchaselist` DISABLE KEYS */;
INSERT INTO `purchaselist` VALUES (4,3,'2017-12-01 01:13:20','favorite'),(4,14,'2017-12-04 16:09:02','hihi'),(8,14,'2017-12-01 01:38:22','wow'),(10,16,'2017-12-02 20:37:45','sujin good'),(11,3,'2017-12-01 01:26:56','aa'),(12,14,'2017-12-02 20:52:08','uuuu'),(19,14,'2017-12-05 15:10:44','very good');
/*!40000 ALTER TABLE `purchaselist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `singer`
--

DROP TABLE IF EXISTS `singer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `singer` (
  `SINGERNUM` int(11) NOT NULL AUTO_INCREMENT,
  `SINGERNAME` char(50) NOT NULL,
  `SINGERTYPE` int(11) NOT NULL DEFAULT 1,
  `DEBUTDAY` char(50) DEFAULT NULL,
  `ENTERTAINMENT` char(50) DEFAULT NULL,
  PRIMARY KEY (`SINGERNUM`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `singer`
--

LOCK TABLES `singer` WRITE;
/*!40000 ALTER TABLE `singer` DISABLE KEYS */;
INSERT INTO `singer` VALUES (1,'sujin',1,'19970101','jyp'),(2,'suuu',2,'19980202','sm'),(4,'leemoonsae',1,'18800101','moonsae'),(6,'kang',2,'19980808','ene'),(7,'jin',3,'19970202','sk'),(8,'aaaa',1,'19980101','aaab'),(12,'yy',1,'20130202','sj'),(13,'redvelvet',2,'20140101','sm'),(14,'ksj',2,'19970101','yg'),(15,'pjy',1,'19870202','jyp');
/*!40000 ALTER TABLE `singer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `UNUM` int(11) NOT NULL AUTO_INCREMENT,
  `UID` char(50) NOT NULL,
  `UPW` char(50) NOT NULL,
  `UEMAIL` char(50) NOT NULL,
  `PURCHASENUM` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`UNUM`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'kangsj1111','kakaka','kangsj@naver.com',20),(2,'kangsj12345','sjsjsjsj','kangsj123@hanyang.ac.kr',0),(3,'kakaka','sdfsd','kansd@',15),(4,'kakakaka','sdfsdfsdfs','kskdfls',0),(5,'kakakaksldkfs','sjdlf','ka@kskdf',0),(6,'kangsjsd','sdlwif','skdj123',0),(7,'ksdlfk','sldkfj','123dkfs',0),(8,'ka1234sd','sowifj','ska@lskd',0),(9,'ksla;sldf','sjdijow','kdks@jsldkf',0),(10,'sldkfjd','slsl','aksk@skdj',0),(11,'kangyjcute','uglyyujin','disasteryujin@naver.com',0),(12,'pretty sujin','sldfls','kang@nav',15),(13,'sldf','fjs','kangsj@nava',0),(14,'kang','sujin','kangsj123@nna',36),(15,'sujinkang','sujinkang','kang@naver.com',0),(16,'yj','yj','kangyj@naver.com',4),(17,'aaaa','aa','skjf',0),(18,'hello','hihi','kangsj@naver.com',0),(19,'abcd','abcd','kang@hanyang.ac.kr',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-05 17:31:30
