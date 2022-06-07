-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: trickytickets
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `trickytickets`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `trickytickets` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `trickytickets`;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `ID` bigint NOT NULL,
  `Name` varchar(225) DEFAULT NULL,
  `Description` varchar(1500) DEFAULT NULL,
  `created_by` varchar(225) DEFAULT NULL,
  `modified_by` varchar(225) DEFAULT NULL,
  `created_datetime` timestamp NULL DEFAULT NULL,
  `modified_datetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Microsoft Office - Licenza','Problematiche di attivazione e licenza','Admin','Admin1','2022-04-19 08:00:00','2022-05-12 13:52:13'),(2,'Microsoft Office - Utilizzo','Malfunzionamenti nell\'utilizzo del pacchetto Office','Admin','Admin','2022-06-01 07:39:30','2022-06-01 07:39:30'),(3,'AS400 - Malfunzionamento','Problematiche nell\'utilizzo del software gestionale','Admin','Admin','2022-06-01 07:40:13','2022-06-01 07:40:13'),(4,'AS400 - Installazione e attivazione','Problematiche nella configurazione del software gestionale','Admin','Admin','2022-06-01 07:40:37','2022-06-01 07:40:37'),(5,'Hardware - PC Portatile','Problematiche hardware relative al laptop, incluse le rotture accidentali','Admin','Admin','2022-06-07 11:10:34','2022-06-07 11:10:34'),(6,'Hardware - PC Fisso','Problematiche hardware relative alle postazioni fisse','Admin','Admin','2022-06-07 11:10:51','2022-06-07 11:10:51');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint NOT NULL,
  `ticketNo` bigint DEFAULT NULL,
  `ticketId` bigint DEFAULT NULL,
  `TicketTitle` varchar(225) DEFAULT NULL,
  `userName` varchar(225) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `comment` varchar(1500) DEFAULT NULL,
  `created_by` varchar(225) DEFAULT NULL,
  `modified_by` varchar(225) DEFAULT NULL,
  `created_datetime` timestamp NULL DEFAULT NULL,
  `modified_datetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,10001001,1,'Problemi inoltro','Giulia Gillespie','2022-04-19','Licenza non attiva','User','User','2022-04-19 08:00:00','2022-04-19 08:00:00'),(2,10001003,3,'Impossibile verificare il codice Product Key','Admin','2022-06-01','Ciao Jimi, come annunciato al telefono ho mandato un nuovo codice di attivazione all\'indirizzo mail presente nel tuo profilo. Per cortesia lascia un commento a attivazione ultimata, grazie','Admin','Admin','2022-06-01 07:56:58','2022-06-01 07:56:58'),(3,10001003,3,'Impossibile verificare il codice Product Key','JimiHendrix','2022-06-01','Grazie, confermo che sono riuscito ad attivare il pacchetto','JimiHendrix','JimiHendrix','2022-06-01 08:00:57','2022-06-01 08:00:57'),(4,10001003,3,'Impossibile verificare il codice Product Key','Admin','2022-06-01','Perfetto, procedo con la chiusura del ticket.','Admin','Admin','2022-06-01 08:01:54','2022-06-01 08:01:54'),(5,10001004,4,'Scarichi di magazzino errati','Admin','2022-06-07','Risolto tramite reinstallazione del client, procedo alla chiusura','Admin','Admin','2022-06-07 11:13:01','2022-06-07 11:13:01'),(6,10001007,7,'Schermo rotto','Admin','2022-06-07','Purtroppo i danni di questo tipo non prevedono riparazione a carico dell\'azienda, devo annullare la richiesta','Admin','Admin','2022-06-07 11:13:43','2022-06-07 11:13:43');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket` (
  `Id` bigint NOT NULL,
  `categoryId` bigint DEFAULT NULL,
  `CategoryName` varchar(225) DEFAULT NULL,
  `userId` bigint DEFAULT NULL,
  `UserName` varchar(225) DEFAULT NULL,
  `TicketNo` bigint DEFAULT NULL,
  `Title` varchar(755) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `Status` varchar(255) DEFAULT NULL,
  `description` varchar(1500) DEFAULT NULL,
  `created_by` varchar(225) DEFAULT NULL,
  `modified_by` varchar(225) DEFAULT NULL,
  `created_datetime` timestamp NULL DEFAULT NULL,
  `modified_datetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (3,1,'Microsoft Office - Licenza',2,'JimiHendrix',10001003,'Impossibile verificare il codice Product Key','2022-06-07','Chiuso','Riporto l\'errore nel titolo: tutte le applicazioni hanno la schermata rossa, inoltre alcune funzionalità sono bloccate (non posso inoltrare le mail su outlook ad esempio). Sono in turno fino alle 13 tutti i giorni.','JimiHendrix','JimiHendrix','2022-06-01 07:43:46','2022-06-01 07:43:46'),(4,3,'AS400 - Malfunzionamento',3,'KurtCobain',10001004,'Scarichi di magazzino errati','2022-06-07','Chiuso','Ogni volta che eseguo uno scarico giacenze, vedo che vengono in realtà scalati il doppio dei pezzi richiesti, ovviamente questo sballa i magazzini, è urgente grazie.','KurtCobain','KurtCobain','2022-06-01 07:55:10','2022-06-01 07:55:10'),(5,2,'Microsoft Office - Utilizzo',4,'JimMorrison',10001005,'Crash di Excel','2022-06-01','In lavorazione','Ogni volta che apro un file con estensione .xls ho un crash','JimMorrison','JimMorrison','2022-06-01 10:55:15','2022-06-01 10:55:15'),(6,4,'AS400 - Installazione e attivazione',2,'JimiHendrix',10001006,'Nuovo PC','2022-06-01','In lavorazione','Dovrei installare AS400 sul PC che mi è stato dato per smart working grazie','JimiHendrix','JimiHendrix','2022-06-01 11:03:33','2022-06-01 11:03:33'),(7,5,'Hardware - PC Portatile',5,'BillieJoeArmstrong',10001007,'Schermo rotto','2022-06-07','Annullato','Buongiorno, ho inavvertitamente scagliato il computer a terra, lo schermo si è rotto, chiedo assistenza','BillieJoeArmstrong','BillieJoeArmstrong','2022-06-07 11:11:28','2022-06-07 11:11:28');
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `ID` bigint NOT NULL,
  `Name` varchar(225) DEFAULT NULL,
  `UserName` varchar(225) DEFAULT NULL,
  `password` varchar(225) DEFAULT NULL,
  `email` varchar(225) DEFAULT NULL,
  `contactNo` varchar(225) DEFAULT NULL,
  `businessArea` varchar(225) DEFAULT NULL,
  `roleId` bigint DEFAULT NULL,
  `roleName` varchar(225) DEFAULT NULL,
  `created_by` varchar(225) DEFAULT NULL,
  `modified_by` varchar(225) DEFAULT NULL,
  `created_datetime` timestamp NULL DEFAULT NULL,
  `modified_datetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Davide Piccinelli','Admin','Banana84','davide.piccinelli@mail.polimi.it','3339484791','IT',1,'Fornitore','root','root','2022-04-19 08:00:00','2022-04-19 08:00:00'),(2,'Jimi Hendrix','JimiHendrix','Banana84','jimi@hendrix.com','3339484791','Logistico',2,'Utente','SysAdmin','SysAdmin','2022-06-01 07:42:13','2022-06-01 07:42:13'),(3,'Kurt Cobain','KurtCobain','Banana84','kurt@cobain.com','3339484791','Amministrativo',2,'Utente','SysAdmin','SysAdmin','2022-06-01 07:53:53','2022-06-01 07:53:53'),(4,'Jim Morrison','JimMorrison','Banana84','jim@morrison.com','3339484791','Commerciale',2,'Utente','SysAdmin','SysAdmin','2022-06-01 10:47:33','2022-06-01 10:47:33'),(5,'Billie Joe Armstrong','BillieJoeArmstrong','Banana84','billie@joe.armstrong','3339484791','Logistico',2,'Utente','SysAdmin','SysAdmin','2022-06-07 11:09:11','2022-06-07 11:09:11');
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

-- Dump completed on 2022-06-07 15:51:08
