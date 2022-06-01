/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 8.0.22 : Database - trickytickets
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`trickytickets` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `trickytickets`;

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

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

/*Data for the table `category` */

insert  into `category`(`ID`,`Name`,`Description`,`created_by`,`modified_by`,`created_datetime`,`modified_datetime`) values 
(1,'Microsoft Office','Problemi di licenza','Admin','Admin','2022-04-19 10:00:00','2022-04-19 10:00:00');

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

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

/*Data for the table `comment` */

insert  into `comment`(`id`,`ticketNo`,`ticketId`,`TicketTitle`,`userName`,`date`,`comment`,`created_by`,`modified_by`,`created_datetime`,`modified_datetime`) values 
(1,10001001,1,'Problematica Office',NULL,'2022-04-19','Licenza non attiva','User','User','2022-04-19 10:00:00','2022-04-19 10:00:00');

/*Table structure for table `ticket` */

DROP TABLE IF EXISTS `ticket`;

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

/*Data for the table `ticket` */

insert  into `ticket`(`Id`,`categoryId`,`CategoryName`,`userId`,`UserName`,`TicketNo`,`Title`,`Date`,`Status`,`description`,`created_by`,`modified_by`,`created_datetime`,`modified_datetime`) values 
(1,1,'Microsoft Office',2,'Giulia Gillespie',10001001,'Problemi inoltro','2022-04-19','Completed','Non riesco a inoltrare file','User','User','2022-04-19 10:00:00','2022-04-19 10:00:00');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

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

/*Data for the table `user` */

insert  into `user`(`ID`,`Name`,`UserName`,`password`,`email`,`contactNo`,`businessArea`,`roleId`,`roleName`,`created_by`,`modified_by`,`created_datetime`,`modified_datetime`) values 
(1,'Davide Piccinelli','Admin','Admin','davide.piccinelli@mail.polimi.it','3339484791','IT',1,'Fornitore','root','root','2022-04-19 10:00:00','2022-04-19 10:00:00'),
(2,'Giulia Gillespie','User','User','giulietta93@gmail.com','3339484791','Amministrativo',2,'Utente','root','root','2022-04-19 10:00:00','2022-04-19 10:00:00');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
