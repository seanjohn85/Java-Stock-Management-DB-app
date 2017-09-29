-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 29, 2017 at 11:34 PM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 7.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `N00145905`
--

-- --------------------------------------------------------

--
-- Table structure for table `branch`
--

CREATE TABLE `branch` (
  `branchNo` int(11) NOT NULL,
  `branchName` varchar(40) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phoneNo` int(11) NOT NULL,
  `openHours` enum('8 to 4','8 to 6','9 to 4','9 to 6','10 to 6','10 to 8') NOT NULL DEFAULT '9 to 6',
  `openDate` date DEFAULT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `branch`
--

INSERT INTO `branch` (`branchNo`, `branchName`, `address`, `phoneNo`, `openHours`, `openDate`, `createdDate`) VALUES
(0, 'Head Office', 'main road Dublin', 181235841, '9 to 6', '2015-09-22', '2016-02-29 10:26:51'),
(1, 'Raheny', '12 Main st dub5', 8773214, '9 to 4', '0000-00-00', '2015-11-01 02:31:02'),
(2, 'Finglas', '3 Chalrestown shopping ceneter d11', 8345671, '8 to 4', '0000-00-00', '2015-11-03 20:47:54'),
(3, 'Blackrock', 'Blackrock Dublin            ', 215555555, '10 to 6', '2009-12-12', '2015-12-12 14:05:07'),
(4, 'Bray', 'Bray Town Wicklow', 1234567891, '10 to 8', '2009-12-12', '2015-12-12 11:41:57'),
(5, 'chinatown', 'Dublin 1                                       ', 1871287478, '10 to 6', '2009-03-27', '2015-12-12 13:37:07'),
(6, 'Cork', 'Cork city', 1232222222, '8 to 4', '2009-03-27', '2015-12-12 14:10:54');

-- --------------------------------------------------------

--
-- Table structure for table `branchmanager`
--

CREATE TABLE `branchmanager` (
  `managerNo` int(11) NOT NULL,
  `branchNo` int(11) NOT NULL,
  `accountId` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `branchmanager`
--

INSERT INTO `branchmanager` (`managerNo`, `branchNo`, `accountId`, `name`) VALUES
(1, 1, 1, 'John Kenny');

-- --------------------------------------------------------

--
-- Table structure for table `branchwebp`
--

CREATE TABLE `branchwebp` (
  `branchNo` int(11) NOT NULL,
  `branchName` varchar(40) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phoneNo` varchar(11) NOT NULL,
  `openHours` enum('8 to 4','8 to 6','9 to 4','9 to 6','10 to 6','10 to 8') NOT NULL DEFAULT '9 to 6',
  `openDate` date DEFAULT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `branchwebp`
--

INSERT INTO `branchwebp` (`branchNo`, `branchName`, `address`, `phoneNo`, `openHours`, `openDate`, `createdDate`) VALUES
(1, 'Raheny', '12 Main st \r\nDub5', '001-8773214', '9 to 4', '2009-05-12', '2015-11-01 02:31:02'),
(12, 'Finglas', 'Dunsink Lane \r\nD11           ', '001-2222821', '8 to 4', '2009-12-12', '2015-12-12 11:41:57'),
(13, 'Chinatown', 'Chinatown \r\ndublin1     ', '001-2220000', '10 to 6', '2009-03-27', '2015-12-12 13:37:07'),
(15, 'Kildare', 'Kidare Village, \r\nKildare', '021-5555555', '8 to 6', '2009-12-12', '2015-12-12 14:05:07'),
(16, 'Cork', 'Cork City\r\nCork', '123-2222222', '8 to 4', '2009-03-27', '2015-12-12 14:10:54'),
(18, 'Dundrum', '12 main street         \r\nDublin                        ', '001-2274161', '10 to 6', '2015-12-01', '2015-12-12 14:29:35'),
(19, 'Bray', '1 Barey  ave\r\nWicklow                                  ', '045-5125454', '9 to 6', '2008-03-01', '2015-12-12 14:38:32'),
(21, 'Dun Laoghaire', '12 Town square\r\nDun Laoghaire', '001-5555121', '10 to 6', '1999-03-12', '2016-03-30 17:02:24');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customerNo` int(11) NOT NULL,
  `branchNo` int(11) NOT NULL,
  `fName` varchar(50) NOT NULL,
  `lName` varchar(50) NOT NULL,
  `gender` enum('male','female') NOT NULL,
  `address` varchar(300) NOT NULL,
  `email` varchar(256) NOT NULL,
  `mobileNo` int(10) NOT NULL,
  `openDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customerNo`, `branchNo`, `fName`, `lName`, `gender`, `address`, `email`, `mobileNo`, `openDate`) VALUES
(1, 1, 'dj', 'smyth', 'male', '121 woodbine road d5', 'pj@none.ie', 871344985, '2015-11-06 20:27:56'),
(2, 1, 'Paul', 'Jackson', 'male', '15 Mellwood Park D5', 'paul@none.ie', 875871238, '2015-11-12 10:57:36'),
(3, 1, 'Jane', 'Cleary', 'female', '12 Mellwood Park', 'fb@none.ie', 871234567, '2015-11-12 10:57:36'),
(4, 2, 'Joe ', 'Bloggs', 'male', '24 Mellview lawn D11', 'joe@mel.ie', 87714585, '2015-11-12 11:00:22'),
(8, 4, 'Paddy', 'Mack', 'male', '12 wood vill drive', 'ma@p.ie', 871265854, '2016-02-29 10:33:02');

-- --------------------------------------------------------

--
-- Table structure for table `customerpurchase`
--

CREATE TABLE `customerpurchase` (
  `orderId` int(11) NOT NULL,
  `customerNo` int(11) NOT NULL,
  `stockId` int(11) NOT NULL,
  `custStockId` int(11) NOT NULL,
  `price` double(11,2) NOT NULL,
  `qty` int(11) NOT NULL,
  `orderDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customerpurchase`
--

INSERT INTO `customerpurchase` (`orderId`, `customerNo`, `stockId`, `custStockId`, `price`, `qty`, `orderDate`) VALUES
(1, 1, 1, 1, 116.60, 32, '2015-11-23 12:06:05'),
(2, 1, 1, 1, 116.60, 5, '2015-11-23 12:06:41'),
(3, 1, 13, 2, 41.03, 4, '2015-11-23 12:13:44'),
(4, 3, 2, 3, 67.98, 5, '2016-03-01 10:18:52'),
(5, 3, 17, 4, 94.77, 43, '2016-03-01 10:18:52'),
(6, 1, 1, 1, 60.43, 12, '2016-03-01 10:18:52');

-- --------------------------------------------------------

--
-- Table structure for table `customersale`
--

CREATE TABLE `customersale` (
  `saleId` int(11) NOT NULL,
  `customerNo` int(11) NOT NULL,
  `stockId` int(11) NOT NULL,
  `custStockId` int(11) NOT NULL,
  `price` decimal(11,2) NOT NULL,
  `qty` int(11) NOT NULL,
  `saleDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customersale`
--

INSERT INTO `customersale` (`saleId`, `customerNo`, `stockId`, `custStockId`, `price`, `qty`, `saleDate`) VALUES
(1, 1, 1, 1, '67.83', 2, '2016-03-01 09:59:04'),
(2, 1, 1, 1, '60.43', 1, '2016-03-01 10:18:52'),
(3, 3, 17, 1, '94.77', 3, '2016-03-01 10:18:52');

-- --------------------------------------------------------

--
-- Table structure for table `customerstock`
--

CREATE TABLE `customerstock` (
  `custStockId` int(11) NOT NULL,
  `customerNo` int(11) NOT NULL,
  `stockId` int(11) NOT NULL,
  `qty` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customerstock`
--

INSERT INTO `customerstock` (`custStockId`, `customerNo`, `stockId`, `qty`) VALUES
(1, 1, 1, 46),
(2, 1, 13, 4),
(3, 3, 2, 5),
(4, 3, 17, 40);

-- --------------------------------------------------------

--
-- Table structure for table `customerstockwebp`
--

CREATE TABLE `customerstockwebp` (
  `custStockId` int(11) NOT NULL,
  `customerNo` int(11) NOT NULL,
  `stockId` int(11) NOT NULL,
  `qty` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customerstockwebp`
--

INSERT INTO `customerstockwebp` (`custStockId`, `customerNo`, `stockId`, `qty`) VALUES
(12, 1, 14, 6),
(15, 31, 15, 4),
(16, 31, 1, 3),
(17, 31, 2, 2),
(18, 32, 23, 1),
(19, 32, 15, 2),
(20, 32, 45, 1),
(21, 32, 19, 1),
(22, 32, 42, 1),
(23, 32, 2, 1),
(24, 32, 40, 2),
(25, 33, 73, 8),
(26, 33, 48, 5),
(27, 33, 62, 6),
(28, 33, 13, 6),
(29, 33, 15, 6),
(30, 33, 1, 6),
(31, 34, 47, 5),
(32, 34, 46, 5),
(33, 34, 44, 4),
(34, 34, 42, 3),
(35, 35, 50, 5),
(36, 35, 62, 4),
(37, 35, 73, 3),
(38, 36, 54, 4),
(39, 36, 51, 4),
(40, 36, 38, 9),
(41, 36, 16, 1),
(42, 36, 21, 5),
(43, 37, 57, 1),
(44, 37, 56, 2),
(45, 37, 49, 2),
(46, 37, 67, 2),
(47, 37, 2, 2),
(48, 37, 1, 3),
(49, 37, 21, 3),
(50, 38, 58, 3),
(51, 38, 61, 5),
(52, 39, 47, 1),
(53, 39, 45, 1),
(54, 39, 22, 1),
(55, 39, 41, 1),
(56, 39, 39, 1),
(57, 39, 40, 1),
(58, 39, 2, 1),
(59, 40, 44, 1),
(60, 40, 43, 2),
(61, 40, 18, 2),
(62, 40, 15, 2),
(63, 40, 1, 1),
(64, 40, 13, 2),
(65, 40, 2, 2),
(66, 40, 14, 3),
(67, 40, 22, 4),
(68, 41, 46, 3),
(69, 41, 24, 3),
(70, 41, 50, 2),
(71, 41, 38, 2),
(72, 42, 60, 5),
(73, 42, 51, 5),
(74, 42, 59, 3),
(75, 42, 58, 3),
(76, 42, 56, 2),
(77, 43, 55, 1),
(78, 43, 45, 1),
(79, 43, 49, 1),
(80, 44, 64, 4),
(81, 44, 69, 30),
(82, 45, 56, 7),
(83, 45, 52, 3),
(84, 45, 60, 2),
(85, 45, 42, 1),
(86, 46, 18, 3),
(87, 46, 20, 3),
(88, 46, 40, 8),
(89, 47, 71, 20),
(90, 47, 68, 4),
(91, 47, 66, 4),
(92, 47, 64, 3),
(93, 47, 62, 2),
(94, 48, 73, 63),
(95, 49, 45, 11),
(96, 49, 41, 2),
(97, 49, 50, 3),
(98, 49, 42, 1),
(99, 49, 43, 3),
(100, 49, 65, 1),
(101, 49, 72, 2),
(102, 50, 51, 6),
(103, 50, 20, 5),
(104, 50, 19, 3),
(105, 51, 62, 4),
(106, 51, 54, 5),
(107, 51, 44, 6),
(109, 1, 13, 2),
(110, 1, 15, 2),
(111, 1, 21, 1);

-- --------------------------------------------------------

--
-- Table structure for table `customerwebp`
--

CREATE TABLE `customerwebp` (
  `customerNo` int(11) NOT NULL,
  `fName` varchar(40) NOT NULL,
  `lName` varchar(40) NOT NULL,
  `address` varchar(255) NOT NULL,
  `mobileNo` varchar(11) NOT NULL,
  `gender` enum('male','female') DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `branchNo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customerwebp`
--

INSERT INTO `customerwebp` (`customerNo`, `fName`, `lName`, `address`, `mobileNo`, `gender`, `email`, `branchNo`) VALUES
(1, 'Wayne ', 'Rooney', '12 main road\r\nDublin 5', '087-1234767', 'male', 'wr@mu.com', 1),
(2, 'Anthony', 'Martial', '1 main road\r\nDublin 5', '087-1529825', 'male', 'js@none.ie', 1),
(3, 'Memphis ', 'Depay', '2 Main road\r\nDublin 5', '086-5482847', 'male', 'md@mu.ie', 1),
(4, 'Juan', 'Mata', '3 Main road\r\nDublin 5', '087-8020123', 'male', 'jm@mu.ie', 1),
(5, 'David ', 'de Gea', '5 Main Road\r\nDublin 5', '087-1518512', 'male', 'deg@mu.ie', 1),
(31, 'Ander ', 'Herrera', '9 main road\r\ndublin', '087-1234767', 'male', 'ah@mu.ie', 1),
(32, 'Chris ', 'Smalling', '18 main road\r\nDublin 5', '087-2418168', 'male', 'cs@mu.ie', 1),
(33, 'Bastian ', 'Schweinsteiger', '77 main road\r\nDublin 5', '086-7128411', 'male', 'bs@mu.ie', 1),
(34, 'Christina ', 'Aguilera', '1 fake street', '089-2145512', 'female', 'ca@singer.ie', 12),
(35, 'Mariah ', 'Carey', '2 singer street\r\nDublin 11', '086-5544552', 'female', 'mc@singer.ie', 12),
(36, 'Britney ', 'Spears', '9 singer street\r\nDublin 11', '087-1521587', 'female', 'bs@singer.ie', 12),
(37, 'Miley ', 'Cyrus', '87 singer street\r\nDublin 11', '087-6185525', 'female', 'miley@singer.ie', 12),
(38, 'Rihanna ', 'Fenty', '79 Singer Street\r\nDublin 11', '086-5482145', 'female', 'ri@singer.ie', 12),
(39, 'Whitney ', 'Houston', '71 Singer Street\r\nDublin11', '089-2182546', 'female', 'wh@singer.ie', 12),
(40, 'Jennifer ', 'Lopez', '29 Singer Street\r\nDublin 11', '085-1112254', 'female', 'jl@singer.ie', 12),
(41, 'Taylor ', 'Swift', '97 Singer Lane\r\nDublin', '087-1234759', 'female', 'ts@singer.ie', 21),
(42, 'Avril ', 'Lavigne', '28 Singer Lane \r\nDublin', '089-5261261', 'female', 'al@singer.ie', 21),
(43, 'Gwen ', 'Stefani', '52 Singer lane\r\nDublin', '087-1218521', 'female', 'gs@singer.ie', 21),
(44, 'Bruse', 'Lee', '1 China court\r\nDublin 1', '087-1234584', 'male', 'bl@china.ie', 13),
(45, 'Jackie ', 'Chan', '1 China Ave\r\n\\dublin 1', '087-2181854', 'male', 'jc@china.ie', 13),
(46, 'Christian ', 'Bale', '1 main ave\r\nKildare', '087-2056921', 'male', 'cb@actor.ie', 15),
(47, 'Robert ', 'Downey Jr.', '12 fake road\r\nKildare', '087-1218521', 'male', 'rdj@actor.ie', 15),
(48, 'Charlie', 'Sheen', '12 Fake road\r\nKildare', '085-8255874', 'male', 'cs@actor.ie', 15),
(49, 'Brad ', 'Pitt', '12 fake ave\r\nKildare', '087-2182181', 'male', 'bp@joe.ie', 15),
(50, 'Clint', 'Eastwood', '12 pauls lane\r\nDundrum', '087-1528168', 'male', 'cl@actor.ie', 18),
(51, 'George ', 'Clooney', '12 georges st\r\nDundrum', '087-1252921', 'male', 'gc@google.com', 18);

-- --------------------------------------------------------

--
-- Table structure for table `fucuation`
--

CREATE TABLE `fucuation` (
  `recordNo` int(11) NOT NULL,
  `stockId` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `percent` int(11) NOT NULL,
  `price` decimal(11,2) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `fucuation`
--

INSERT INTO `fucuation` (`recordNo`, `stockId`, `amount`, `percent`, `price`, `date`) VALUES
(1, 1, 6, 5, '122.43', '2016-02-29 12:14:29'),
(2, 2, -8, -9, '82.53', '2016-02-29 12:14:29'),
(3, 13, 7, 17, '48.01', '2016-02-29 12:14:29'),
(4, 14, -9, -18, '40.99', '2016-02-29 12:14:29'),
(5, 15, 1, 8, '19.61', '2016-02-29 12:14:29'),
(6, 16, -110, -10, '990.00', '2016-02-29 12:14:29'),
(7, 17, 19, 17, '127.46', '2016-02-29 12:14:29'),
(8, 18, 3, 8, '36.08', '2016-02-29 12:14:29'),
(9, 19, -4, -6, '58.39', '2016-02-29 12:14:29'),
(10, 20, 0, -1, '36.61', '2016-02-29 12:14:29'),
(11, 21, 7, 13, '60.62', '2016-02-29 12:14:29'),
(12, 22, 0, 8, '4.29', '2016-02-29 12:14:29'),
(13, 23, 0, -1, '42.57', '2016-02-29 12:14:29'),
(14, 24, 1, 1, '125.68', '2016-02-29 12:14:29'),
(15, 1, -9, -7, '113.86', '2016-02-29 12:15:40'),
(16, 2, 6, 7, '88.31', '2016-02-29 12:15:40'),
(17, 13, 1, 3, '49.45', '2016-02-29 12:15:40'),
(18, 14, -1, -3, '39.76', '2016-02-29 12:15:40'),
(19, 15, -2, -11, '17.45', '2016-02-29 12:15:40'),
(20, 16, 59, 6, '1049.40', '2016-02-29 12:15:40'),
(21, 17, -19, -15, '108.34', '2016-02-29 12:15:40'),
(22, 18, -1, -4, '34.64', '2016-02-29 12:15:40'),
(23, 19, 10, 17, '68.32', '2016-02-29 12:15:40'),
(24, 20, 1, 2, '37.34', '2016-02-29 12:15:40'),
(25, 21, -2, -3, '58.80', '2016-02-29 12:15:40'),
(26, 22, 0, 1, '4.33', '2016-02-29 12:15:40'),
(27, 23, 0, 0, '42.57', '2016-02-29 12:15:41'),
(28, 24, 15, 12, '140.76', '2016-02-29 12:15:41'),
(29, 1, 14, 12, '127.52', '2016-02-29 12:19:23'),
(30, 2, -10, -11, '78.60', '2016-02-29 12:19:23'),
(31, 13, 2, 4, '51.43', '2016-02-29 12:19:23'),
(32, 14, -2, -5, '37.77', '2016-02-29 12:19:23'),
(33, 15, 2, 13, '19.72', '2016-02-29 12:19:23'),
(34, 16, -52, -5, '996.93', '2016-02-29 12:19:23'),
(35, 17, 14, 13, '122.42', '2016-02-29 12:19:23'),
(36, 18, -3, -9, '31.52', '2016-02-29 12:19:23'),
(37, 19, 3, 4, '71.05', '2016-02-29 12:19:23'),
(38, 20, 1, 2, '38.09', '2016-02-29 12:19:23'),
(39, 21, 9, 15, '67.62', '2016-02-29 12:19:23'),
(40, 22, 0, -3, '4.20', '2016-02-29 12:19:23'),
(41, 23, -4, -9, '38.74', '2016-02-29 12:19:23'),
(42, 24, -7, -5, '133.72', '2016-02-29 12:19:23'),
(43, 1, -18, -14, '109.67', '2016-02-29 12:22:01'),
(44, 2, 8, 10, '86.46', '2016-02-29 12:22:01'),
(45, 13, -1, -2, '50.40', '2016-02-29 12:22:01'),
(46, 14, -3, -9, '34.37', '2016-02-29 12:22:01'),
(47, 15, 4, 18, '23.27', '2016-02-29 12:22:01'),
(48, 16, -120, -12, '877.30', '2016-02-29 12:22:01'),
(49, 17, -21, -17, '101.61', '2016-02-29 12:22:02'),
(50, 18, 5, 16, '36.56', '2016-02-29 12:22:02'),
(51, 19, 2, 3, '73.18', '2016-02-29 12:22:02'),
(52, 20, -7, -19, '30.85', '2016-02-29 12:22:02'),
(53, 21, -8, -12, '59.51', '2016-02-29 12:22:02'),
(54, 22, -1, -16, '3.53', '2016-02-29 12:22:02'),
(55, 23, -3, -9, '35.25', '2016-02-29 12:22:02'),
(56, 24, 21, 16, '155.12', '2016-02-29 12:22:02'),
(57, 1, 16, 15, '126.12', '2016-02-29 12:24:10'),
(58, 2, 15, 17, '101.16', '2016-02-29 12:24:10'),
(59, 13, -1, -1, '49.90', '2016-02-29 12:24:11'),
(60, 14, -5, -15, '29.21', '2016-02-29 12:24:11'),
(61, 15, -3, -13, '20.24', '2016-02-29 12:24:11'),
(62, 16, -26, -3, '850.98', '2016-02-29 12:24:11'),
(63, 17, 5, 5, '106.69', '2016-02-29 12:24:11'),
(64, 18, -7, -18, '29.98', '2016-02-29 12:24:11'),
(65, 19, 0, 0, '73.18', '2016-02-29 12:24:11'),
(66, 20, 3, 11, '34.24', '2016-02-29 12:24:11'),
(67, 21, -6, -10, '53.56', '2016-02-29 12:24:11'),
(68, 22, 0, 6, '3.74', '2016-02-29 12:24:11'),
(69, 23, -2, -5, '33.49', '2016-02-29 12:24:11'),
(70, 24, -9, -6, '145.81', '2016-02-29 12:24:11'),
(71, 1, -19, -15, '107.20', '2016-02-29 12:41:47'),
(72, 2, -14, -14, '87.00', '2016-02-29 12:41:47'),
(73, 13, -3, -7, '46.41', '2016-02-29 12:41:47'),
(74, 14, 5, 18, '34.47', '2016-02-29 12:41:47'),
(75, 15, 3, 13, '22.87', '2016-02-29 12:41:47'),
(76, 16, 94, 11, '944.59', '2016-02-29 12:41:47'),
(77, 17, 20, 19, '126.96', '2016-02-29 12:41:47'),
(78, 18, 3, 9, '32.68', '2016-02-29 12:41:47'),
(79, 19, -12, -16, '61.47', '2016-02-29 12:41:47'),
(80, 20, 6, 18, '40.40', '2016-02-29 12:41:47'),
(81, 21, 1, 1, '54.10', '2016-02-29 12:41:47'),
(82, 22, 0, 7, '4.00', '2016-02-29 12:41:47'),
(83, 23, -2, -5, '31.82', '2016-02-29 12:41:47'),
(84, 24, -15, -10, '131.23', '2016-02-29 12:41:47'),
(85, 1, -18, -17, '88.98', '2016-02-29 12:42:46'),
(86, 2, 9, 10, '95.70', '2016-02-29 12:42:46'),
(87, 13, -9, -19, '37.59', '2016-02-29 12:42:46'),
(88, 14, -4, -13, '29.99', '2016-02-29 12:42:46'),
(89, 15, 0, 0, '22.87', '2016-02-29 12:42:46'),
(90, 16, -47, -5, '897.36', '2016-02-29 12:42:46'),
(91, 17, -24, -19, '102.84', '2016-02-29 12:42:46'),
(92, 18, 6, 19, '38.89', '2016-02-29 12:42:46'),
(93, 19, 1, 1, '62.08', '2016-02-29 12:42:46'),
(94, 20, -7, -17, '33.53', '2016-02-29 12:42:46'),
(95, 21, -4, -7, '50.31', '2016-02-29 12:42:46'),
(96, 22, 0, 2, '4.08', '2016-02-29 12:42:46'),
(97, 23, -6, -18, '26.09', '2016-02-29 12:42:46'),
(98, 24, 25, 19, '156.16', '2016-02-29 12:42:46'),
(99, 1, -3, -3, '86.31', '2016-02-29 12:43:12'),
(100, 2, 0, 0, '95.70', '2016-02-29 12:43:12'),
(101, 13, 5, 14, '42.85', '2016-02-29 12:43:12'),
(102, 14, -3, -11, '26.69', '2016-02-29 12:43:12'),
(103, 15, 2, 9, '24.93', '2016-02-29 12:43:12'),
(104, 16, 126, 14, '1022.99', '2016-02-29 12:43:12'),
(105, 17, 2, 2, '104.90', '2016-02-29 12:43:12'),
(106, 18, 7, 17, '45.50', '2016-02-29 12:43:12'),
(107, 19, 6, 10, '68.29', '2016-02-29 12:43:12'),
(108, 20, 5, 16, '38.89', '2016-02-29 12:43:12'),
(109, 21, 4, 8, '54.33', '2016-02-29 12:43:12'),
(110, 22, 0, 3, '4.20', '2016-02-29 12:43:12'),
(111, 23, -3, -10, '23.48', '2016-02-29 12:43:12'),
(112, 24, -20, -13, '135.86', '2016-02-29 12:43:12'),
(113, 1, -16, -18, '70.77', '2016-02-29 12:46:14'),
(114, 2, -16, -17, '79.43', '2016-02-29 12:46:14'),
(115, 13, -6, -15, '36.42', '2016-02-29 12:46:14'),
(116, 14, -1, -3, '25.89', '2016-02-29 12:46:14'),
(117, 15, -2, -8, '22.94', '2016-02-29 12:46:14'),
(118, 16, 113, 11, '1135.52', '2016-02-29 12:46:14'),
(119, 17, 20, 19, '124.83', '2016-02-29 12:46:14'),
(120, 18, -9, -19, '36.86', '2016-02-29 12:46:14'),
(121, 19, 10, 14, '77.85', '2016-02-29 12:46:14'),
(122, 20, 3, 8, '42.00', '2016-02-29 12:46:14'),
(123, 21, -8, -14, '46.72', '2016-02-29 12:46:14'),
(124, 22, 0, -6, '3.95', '2016-02-29 12:46:14'),
(125, 23, -1, -3, '22.78', '2016-02-29 12:46:14'),
(126, 24, 20, 15, '156.24', '2016-02-29 12:46:15'),
(127, 1, 4, 5, '74.31', '2016-02-29 12:48:37'),
(128, 2, -14, -18, '65.13', '2016-02-29 12:48:37'),
(129, 13, 3, 8, '39.33', '2016-02-29 12:48:37'),
(130, 14, 5, 19, '30.81', '2016-02-29 12:48:38'),
(131, 15, 0, 2, '23.40', '2016-02-29 12:48:38'),
(132, 16, 68, 6, '1203.65', '2016-02-29 12:48:38'),
(133, 17, 16, 13, '141.06', '2016-02-29 12:48:38'),
(134, 18, 6, 17, '43.13', '2016-02-29 12:48:38'),
(135, 19, 4, 5, '81.74', '2016-02-29 12:48:38'),
(136, 20, 8, 18, '49.56', '2016-02-29 12:48:38'),
(137, 21, -6, -13, '40.65', '2016-02-29 12:48:38'),
(138, 22, 0, 11, '4.38', '2016-02-29 12:48:38'),
(139, 23, -1, -3, '22.10', '2016-02-29 12:48:38'),
(140, 24, 30, 19, '185.93', '2016-02-29 12:48:38'),
(141, 1, -14, -19, '60.19', '2016-02-29 12:55:56'),
(142, 2, -4, -6, '61.22', '2016-02-29 12:55:56'),
(143, 13, 7, 18, '46.41', '2016-02-29 12:55:56'),
(144, 14, -6, -19, '24.96', '2016-02-29 12:55:56'),
(145, 15, -4, -18, '19.19', '2016-02-29 12:55:56'),
(146, 16, 169, 14, '1372.16', '2016-02-29 12:55:56'),
(147, 17, -27, -19, '114.26', '2016-02-29 12:55:56'),
(148, 18, -4, -10, '38.82', '2016-02-29 12:55:56'),
(149, 19, -5, -6, '76.84', '2016-02-29 12:55:56'),
(150, 20, -8, -16, '41.63', '2016-02-29 12:55:56'),
(151, 21, -5, -12, '35.77', '2016-02-29 12:55:56'),
(152, 22, 0, -8, '4.03', '2016-02-29 12:55:56'),
(153, 23, -2, -10, '19.89', '2016-02-29 12:55:56'),
(154, 24, 30, 16, '215.68', '2016-02-29 12:55:56'),
(155, 1, -9, -15, '51.16', '2016-02-29 12:56:36'),
(156, 2, 6, 10, '67.34', '2016-02-29 12:56:36'),
(157, 13, 4, 9, '50.59', '2016-02-29 12:56:36'),
(158, 14, 2, 8, '26.96', '2016-02-29 12:56:36'),
(159, 15, -2, -12, '16.89', '2016-02-29 12:56:36'),
(160, 16, -165, -12, '1207.50', '2016-02-29 12:56:36'),
(161, 17, 8, 7, '122.26', '2016-02-29 12:56:36'),
(162, 18, 3, 9, '42.31', '2016-02-29 12:56:36'),
(163, 19, -10, -13, '66.85', '2016-02-29 12:56:36'),
(164, 20, -2, -5, '39.55', '2016-02-29 12:56:36'),
(165, 21, -5, -14, '30.76', '2016-02-29 12:56:37'),
(166, 22, 0, 4, '4.19', '2016-02-29 12:56:37'),
(167, 23, 1, 7, '21.28', '2016-02-29 12:56:37'),
(168, 24, -41, -19, '174.70', '2016-02-29 12:56:37'),
(169, 1, 3, 5, '53.72', '2016-03-01 09:49:30'),
(170, 2, -7, -10, '60.61', '2016-03-01 09:49:30'),
(171, 13, 6, 11, '56.15', '2016-03-01 09:49:30'),
(172, 14, -5, -19, '21.84', '2016-03-01 09:49:30'),
(173, 15, 0, 1, '17.06', '2016-03-01 09:49:30'),
(174, 16, -169, -14, '1038.45', '2016-03-01 09:49:30'),
(175, 17, -17, -14, '105.14', '2016-03-01 09:49:30'),
(176, 18, 8, 18, '49.93', '2016-03-01 09:49:30'),
(177, 19, 0, 0, '66.85', '2016-03-01 09:49:30'),
(178, 20, 0, 0, '39.55', '2016-03-01 09:49:30'),
(179, 21, -3, -11, '27.38', '2016-03-01 09:49:30'),
(180, 22, 0, -9, '3.81', '2016-03-01 09:49:30'),
(181, 23, -3, -16, '17.88', '2016-03-01 09:49:30'),
(182, 24, 0, 0, '174.70', '2016-03-01 09:49:30'),
(183, 1, 4, 7, '57.48', '2016-03-01 09:54:32'),
(184, 2, -2, -3, '58.79', '2016-03-01 09:54:32'),
(185, 13, 1, 1, '56.71', '2016-03-01 09:54:32'),
(186, 14, -4, -17, '18.13', '2016-03-01 09:54:32'),
(187, 15, 3, 15, '19.62', '2016-03-01 09:54:32'),
(188, 16, -166, -16, '872.30', '2016-03-01 09:54:32'),
(189, 17, -13, -12, '92.52', '2016-03-01 09:54:32'),
(190, 18, 4, 9, '54.42', '2016-03-01 09:54:32'),
(191, 19, -4, -6, '62.84', '2016-03-01 09:54:32'),
(192, 20, -8, -19, '32.04', '2016-03-01 09:54:32'),
(193, 21, 2, 6, '29.02', '2016-03-01 09:54:32'),
(194, 22, 1, 15, '4.38', '2016-03-01 09:54:32'),
(195, 23, 3, 16, '20.74', '2016-03-01 09:54:32'),
(196, 24, -26, -15, '148.50', '2016-03-01 09:54:32'),
(197, 1, 10, 18, '67.83', '2016-03-01 09:57:26'),
(198, 2, 2, 4, '61.14', '2016-03-01 09:57:26'),
(199, 13, -6, -10, '51.04', '2016-03-01 09:57:26'),
(200, 14, -1, -8, '16.68', '2016-03-01 09:57:26'),
(201, 15, 3, 13, '22.17', '2016-03-01 09:57:26'),
(202, 16, 157, 18, '1029.31', '2016-03-01 09:57:26'),
(203, 17, 8, 9, '100.85', '2016-03-01 09:57:26'),
(204, 18, -7, -13, '47.35', '2016-03-01 09:57:26'),
(205, 19, -6, -10, '56.56', '2016-03-01 09:57:26'),
(206, 20, -5, -16, '26.91', '2016-03-01 09:57:26'),
(207, 21, 2, 7, '31.05', '2016-03-01 09:57:26'),
(208, 22, 1, 18, '5.17', '2016-03-01 09:57:26'),
(209, 23, 0, 0, '20.74', '2016-03-01 09:57:26'),
(210, 24, -6, -4, '142.56', '2016-03-01 09:57:26'),
(211, 1, 7, 10, '74.61', '2016-03-01 10:03:12'),
(212, 2, -7, -11, '54.41', '2016-03-01 10:03:12'),
(213, 13, -5, -10, '45.94', '2016-03-01 10:03:12'),
(214, 14, 0, 2, '17.01', '2016-03-01 10:03:12'),
(215, 15, -3, -13, '19.29', '2016-03-01 10:03:12'),
(216, 16, 82, 8, '1111.65', '2016-03-01 10:03:12'),
(217, 17, -17, -17, '83.71', '2016-03-01 10:03:12'),
(218, 18, -1, -3, '45.93', '2016-03-01 10:03:12'),
(219, 19, 3, 5, '59.39', '2016-03-01 10:03:12'),
(220, 20, -2, -9, '24.49', '2016-03-01 10:03:12'),
(221, 21, 6, 19, '36.95', '2016-03-01 10:03:12'),
(222, 22, 0, 9, '5.64', '2016-03-01 10:03:12'),
(223, 23, 2, 10, '22.81', '2016-03-01 10:03:12'),
(224, 24, -21, -15, '121.18', '2016-03-01 10:03:12'),
(225, 1, -14, -19, '60.43', '2016-03-01 10:13:08'),
(226, 2, 3, 5, '57.13', '2016-03-01 10:13:08'),
(227, 13, -8, -17, '38.13', '2016-03-01 10:13:08'),
(228, 14, -1, -7, '15.82', '2016-03-01 10:13:08'),
(229, 15, -1, -3, '18.71', '2016-03-01 10:13:08'),
(230, 16, -133, -12, '978.25', '2016-03-01 10:13:08'),
(231, 17, 2, 2, '85.38', '2016-03-01 10:13:08'),
(232, 18, 3, 7, '49.15', '2016-03-01 10:13:09'),
(233, 19, -1, -1, '58.80', '2016-03-01 10:13:09'),
(234, 20, -1, -3, '23.76', '2016-03-01 10:13:09'),
(235, 21, -7, -19, '29.93', '2016-03-01 10:13:09'),
(236, 22, -1, -12, '4.96', '2016-03-01 10:13:09'),
(237, 23, -4, -18, '18.70', '2016-03-01 10:13:09'),
(238, 24, 21, 17, '141.78', '2016-03-01 10:13:09'),
(239, 1, 0, 0, '60.43', '2016-03-01 10:14:00'),
(240, 2, 11, 19, '67.98', '2016-03-01 10:14:00'),
(241, 13, 3, 8, '41.18', '2016-03-01 10:14:00'),
(242, 14, -2, -11, '14.08', '2016-03-01 10:14:00'),
(243, 15, 3, 14, '21.33', '2016-03-01 10:14:00'),
(244, 16, -108, -11, '870.64', '2016-03-01 10:14:00'),
(245, 17, 9, 11, '94.77', '2016-03-01 10:14:00'),
(246, 18, -7, -14, '42.27', '2016-03-01 10:14:00'),
(247, 19, -5, -8, '54.10', '2016-03-01 10:14:00'),
(248, 20, 3, 12, '26.61', '2016-03-01 10:14:00'),
(249, 21, 3, 11, '33.22', '2016-03-01 10:14:00'),
(250, 22, 1, 18, '5.85', '2016-03-01 10:14:00'),
(251, 23, 2, 11, '20.76', '2016-03-01 10:14:00'),
(252, 24, -23, -16, '119.10', '2016-03-01 10:14:00'),
(253, 1, -11, -19, '48.95', '2016-03-01 10:25:01'),
(254, 2, -4, -6, '63.90', '2016-03-01 10:25:01'),
(255, 13, 2, 4, '42.83', '2016-03-01 10:25:01'),
(256, 14, 0, 2, '14.36', '2016-03-01 10:25:01'),
(257, 15, -4, -18, '17.49', '2016-03-01 10:25:01'),
(258, 16, 113, 13, '983.82', '2016-03-01 10:25:01'),
(259, 17, -1, -1, '93.82', '2016-03-01 10:25:01'),
(260, 18, -7, -16, '35.51', '2016-03-01 10:25:01'),
(261, 19, 2, 4, '56.26', '2016-03-01 10:25:01'),
(262, 20, -2, -8, '24.48', '2016-03-01 10:25:01'),
(263, 21, 3, 9, '36.21', '2016-03-01 10:25:01'),
(264, 22, -1, -19, '4.74', '2016-03-01 10:25:01'),
(265, 23, -1, -3, '20.14', '2016-03-01 10:25:01'),
(266, 24, -19, -16, '100.04', '2016-03-01 10:25:01');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `staffNo` int(11) NOT NULL,
  `branchNo` int(11) DEFAULT NULL,
  `accountId` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`staffNo`, `branchNo`, `accountId`, `name`) VALUES
(1, 1, 2, 'Joe Smith'),
(2, 0, 3, 'Alice Jones'),
(3, 0, 4, 'Clare Daly');

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

CREATE TABLE `stock` (
  `stockId` int(11) NOT NULL,
  `stockName` varchar(255) NOT NULL,
  `stockCode` varchar(5) NOT NULL,
  `qty` int(11) NOT NULL,
  `openPrice` decimal(11,2) NOT NULL,
  `currentPrice` decimal(11,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `stock`
--

INSERT INTO `stock` (`stockId`, `stockName`, `stockCode`, `qty`, `openPrice`, `currentPrice`) VALUES
(1, 'Apple', 'AAPL', 10000000, '116.00', '48.95'),
(2, 'Nike Ltd', 'NKE', 2041652, '90.69', '63.90'),
(13, 'Liberty Media Corp. Series A', 'LMCA', 754527452, '41.03', '42.83'),
(14, 'Delta Air Lines Inc', 'DAL', 25285256, '49.99', '14.36'),
(15, 'Manchester United Ltd. Cl A', 'MANU', 22521528, '18.16', '17.49'),
(16, 'Samsung Electronics Co. Ltd', 'SSNLF', 52252511, '1100.00', '983.82'),
(17, 'Facebook Inc. Cl A', 'FB', 24524581, '108.94', '93.82'),
(18, 'Yahoo! Inc.', 'YHOO', 8522524, '33.41', '35.51'),
(19, 'Autodesk Inc.', 'ADSK', 21515041, '62.12', '56.26'),
(20, 'Virgin America Inc.', 'VA', 51245511, '36.98', '24.48'),
(21, 'Microsoft Corp.', 'MSFT', 1825644, '53.65', '36.21'),
(22, 'MBN Corp', 'OSNDF', 15228248, '3.97', '4.74'),
(23, 'Google', 'ggl', 255212565, '43.00', '20.14'),
(24, 'Adidas', 'AD', 52852562, '124.44', '100.04'),
(26, 'dvb', 'hjh', 1, '1.00', '1.00'),
(27, 'dvb', 'hjh', 1, '1.00', '1.00');

-- --------------------------------------------------------

--
-- Table structure for table `stockwebp`
--

CREATE TABLE `stockwebp` (
  `stockId` int(11) NOT NULL,
  `stockName` varchar(255) NOT NULL,
  `stockCode` varchar(5) NOT NULL,
  `qty` int(11) NOT NULL,
  `openPrice` decimal(11,2) NOT NULL,
  `currentPrice` decimal(11,2) NOT NULL,
  `image` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `stockwebp`
--

INSERT INTO `stockwebp` (`stockId`, `stockName`, `stockCode`, `qty`, `openPrice`, `currentPrice`, `image`) VALUES
(1, 'Apple', 'AAPL', 10000000, '116.00', '48.95', 'apple.png'),
(2, 'Nike Ltd', 'NKE', 2041652, '90.69', '63.90', 'nike.png'),
(13, 'Liberty Media Corp', 'LMCA', 754527452, '41.03', '42.83', 'upc.png'),
(14, 'Delta Air Lines Inc', 'DAL', 25285256, '49.99', '14.36', 'delta.png'),
(15, 'Manchester United', 'MANU', 22521528, '18.16', '17.49', 'manutd.png'),
(16, 'Samsung Electronics', 'SSNLF', 52252511, '1100.00', '93.82', 'samsung.png'),
(17, 'Facebook Inc. Cl A', 'FB', 24524581, '108.94', '93.82', 'facebook.png'),
(18, 'Yahoo! Inc.', 'YHOO', 8522524, '33.41', '35.51', 'yahoo.png'),
(19, 'Autodesk Inc.', 'ADSK', 21515041, '62.12', '56.26', 'autodesk.png'),
(20, 'Virgin America Inc.', 'VA', 51245511, '36.98', '24.48', 'virgin.png'),
(21, 'Microsoft Corp.', 'MSFT', 1825644, '53.65', '36.21', 'microsoft.png'),
(22, 'MBN Corp', 'OSNDF', 15228248, '3.97', '4.74', 'mbna.png'),
(23, 'Google', 'ggl', 255212565, '43.00', '20.14', 'google.png'),
(24, 'Adidas', 'AD', 52852562, '124.44', '100.04', 'adidas.png'),
(38, 'Startbucks', 'sb', 23455, '121.12', '112.12', 'starb.png'),
(39, 'Costa', 'ca', 123321, '87.21', '87.21', 'costa.png'),
(40, 'Hilton', 'ht', 54312412, '82.12', '182.12', 'hilton.png'),
(41, 'Marriott', 'mar', 53564756, '87.25', '25.64', 'mar.png'),
(42, 'Pepsi', 'pep', 45637448, '45.25', '58.21', 'pep.png'),
(43, 'BMW', 'bm', 56633421, '54.74', '29.12', 'bmw.png'),
(44, 'Pringles', 'pring', 454343445, '12.15', '19.21', 'pring.png'),
(45, 'Burger King', 'bk', 5623343, '47.12', '54.12', 'bk.png'),
(46, 'Beats ', 'bts', 412452121, '87.12', '85.12', 'b2.png'),
(47, 'Lego', 'le', 77875454, '102.12', '97.25', 'lego.png'),
(48, 'Rolling Stones', 'rs', 12341453, '45.28', '39.12', 'rs.png'),
(49, 'Adobe', 'ad', 324126367, '27.25', '54.21', 'ad.png'),
(50, 'Twitter', 'tw', 423565453, '121.12', '152.00', 'tw.png'),
(51, 'Doritos', 'dor', 43216787, '91.36', '91.36', 'dor.png'),
(52, 'Harley Davidson', 'hd', 34347898, '15.37', '14.26', 'hd.png'),
(53, 'NBC Inc', 'NBC', 6578788, '67.84', '67.84', 'nbc.png'),
(54, 'Trump International', 'tri', 45678987, '69.45', '68.41', 'dt.png'),
(55, 'NFL', 'nfl', 54667889, '97.25', '85.24', 'nfl.png'),
(56, 'World Wrestling Ent', 'WWE', 67890667, '145.12', '75.21', 'wwe.png'),
(57, 'Reebok', 'RBK', 6789009, '57.84', '57.84', 'reebok.png'),
(58, 'CNN Ltd', 'cnn', 32546777, '83.17', '88.99', 'cnn.png'),
(59, 'KFC Inc', 'kfc', 33333357, '35.68', '27.54', 'kfc.png'),
(60, 'Ford Motors', 'ford', 54789875, '148.12', '101.25', 'ford.png'),
(61, 'Crocs', 'cr', 23143567, '41.12', '39.12', 'croc.png'),
(62, 'Wendy', 'wed', 34235678, '78.12', '78.12', 'wendy.png'),
(63, 'Toyota', 'toy', 5667897, '67.12', '67.12', 'toyota.png'),
(64, 'Intel', 'in', 4567890, '74.12', '74.12', 'intel.png'),
(65, 'Audi', 'au', 23347890, '99.12', '99.12', 'audi.png'),
(66, 'McDonalds', 'mcd', 54678978, '23.12', '24.25', 'md.png'),
(67, 'Taco Bell', 'tbe', 334678989, '41.12', '41.12', 'tb.png'),
(68, 'ToysRUs', 'tru', 54789900, '36.37', '42.74', 'toys.png'),
(69, 'Nintendo', 'nin', 55555867, '47.91', '1.52', 'nin.png'),
(70, 'Kellogg&#39;s', 'kelg', 435786907, '61.12', '54.82', 'kelloggs.png'),
(71, 'Red Bull', 'redb', 43556678, '37.12', '51.67', 'red.png'),
(72, 'Chevolet', 'chev', 45478790, '63.85', '61.46', 'chevolet.png'),
(73, 'Playboy', 'pb', 3456677, '73.24', '73.24', 'playboy.png');

-- --------------------------------------------------------

--
-- Table structure for table `useraccounts`
--

CREATE TABLE `useraccounts` (
  `accountId` int(11) NOT NULL,
  `username` varchar(12) NOT NULL,
  `password` varchar(20) NOT NULL,
  `accountType` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `useraccounts`
--

INSERT INTO `useraccounts` (`accountId`, `username`, `password`, `accountType`) VALUES
(1, 'MaJohn1', 'secret', 'MANAGER'),
(2, 'Joe1', 'secret', 'BRANCHADMIN'),
(3, 'HQAlice2', 'secret', 'ADMIN'),
(4, 'HQClare3', 'secret', 'HR');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(32) NOT NULL,
  `role` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`) VALUES
(1, 'joe@bloggs.com', 'secret', 'admin'),
(2, 'mary@bloggs.com', 'secret', 'staff'),
(3, 'fred@bloggs.com', 'secret', 'hr'),
(7, 'wr@mu.com', 'secret', 'customer'),
(8, 'js@none.ie', 'am', 'customer'),
(9, 'md@mu.ie', 'md', 'customer'),
(10, 'jm@mu.ie', 'jm', 'customer'),
(11, 'deg@mu.ie', 'deg', 'customer'),
(12, 'johnny', 'jy', 'Admin'),
(13, 'ah@mu.ie', 'ah', 'customer'),
(14, 'fdgfhgj', '1', 'Admin'),
(15, 'cs@mu.ie', 'cm', 'customer'),
(16, 'bs@mu.ie', 'bs', 'customer'),
(17, 'ca@singer.ie', 'ca', 'customer'),
(18, 'mc@singer.ie', 'ma', 'customer'),
(19, 'bs@singer.ie', 'brit', 'customer'),
(20, 'miley@singer.ie', 'mc', 'customer'),
(21, 'ri@singer.ie', 'rihanna', 'customer'),
(22, 'wh@singer.ie', 'wh', 'customer'),
(23, 'jl@singer.ie', 'jl', 'customer'),
(24, 'ts@singer.ie', 'ts', 'customer'),
(25, 'al@singer.ie', 'al', 'customer'),
(26, 'gs@singer.ie', 'gs', 'customer'),
(27, 'bl@china.ie', 'bl', 'customer'),
(28, 'jc@china.ie', 'jc', 'customer'),
(29, 'cb@actor.ie', 'cb', 'customer'),
(30, 'rdj@actor.ie', 'rd', 'customer'),
(31, 'cs@actor.ie', 'cs', 'customer'),
(32, 'bp@joe.ie', 'bp', 'customer'),
(33, 'cl@actor.ie', 'cl', 'customer'),
(34, 'gc@google.com', 'gc', 'customer');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `branch`
--
ALTER TABLE `branch`
  ADD PRIMARY KEY (`branchNo`),
  ADD KEY `name` (`branchName`);

--
-- Indexes for table `branchmanager`
--
ALTER TABLE `branchmanager`
  ADD PRIMARY KEY (`managerNo`),
  ADD KEY `branchNo` (`branchNo`),
  ADD KEY `accountId` (`accountId`);

--
-- Indexes for table `branchwebp`
--
ALTER TABLE `branchwebp`
  ADD PRIMARY KEY (`branchNo`),
  ADD KEY `name` (`branchName`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customerNo`),
  ADD KEY `branchNo` (`branchNo`);

--
-- Indexes for table `customerpurchase`
--
ALTER TABLE `customerpurchase`
  ADD PRIMARY KEY (`orderId`),
  ADD KEY `customerNo` (`customerNo`),
  ADD KEY `stockId` (`stockId`),
  ADD KEY `custStockId` (`custStockId`);

--
-- Indexes for table `customersale`
--
ALTER TABLE `customersale`
  ADD PRIMARY KEY (`saleId`),
  ADD KEY `stockId` (`stockId`),
  ADD KEY `customerNo` (`customerNo`),
  ADD KEY `custStockId` (`custStockId`);

--
-- Indexes for table `customerstock`
--
ALTER TABLE `customerstock`
  ADD PRIMARY KEY (`custStockId`),
  ADD KEY `customerNo` (`customerNo`,`stockId`,`qty`),
  ADD KEY `stockId` (`stockId`);

--
-- Indexes for table `customerstockwebp`
--
ALTER TABLE `customerstockwebp`
  ADD PRIMARY KEY (`custStockId`),
  ADD KEY `customerNo` (`customerNo`),
  ADD KEY `stockId` (`stockId`);

--
-- Indexes for table `customerwebp`
--
ALTER TABLE `customerwebp`
  ADD PRIMARY KEY (`customerNo`),
  ADD KEY `name` (`fName`),
  ADD KEY `branchNo` (`branchNo`);

--
-- Indexes for table `fucuation`
--
ALTER TABLE `fucuation`
  ADD PRIMARY KEY (`recordNo`),
  ADD KEY `stockId` (`stockId`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`staffNo`),
  ADD KEY `branchNo` (`branchNo`),
  ADD KEY `accountId` (`accountId`);

--
-- Indexes for table `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`stockId`);

--
-- Indexes for table `stockwebp`
--
ALTER TABLE `stockwebp`
  ADD PRIMARY KEY (`stockId`);

--
-- Indexes for table `useraccounts`
--
ALTER TABLE `useraccounts`
  ADD PRIMARY KEY (`accountId`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `branch`
--
ALTER TABLE `branch`
  MODIFY `branchNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `branchmanager`
--
ALTER TABLE `branchmanager`
  MODIFY `managerNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `branchwebp`
--
ALTER TABLE `branchwebp`
  MODIFY `branchNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `customerNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `customerpurchase`
--
ALTER TABLE `customerpurchase`
  MODIFY `orderId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `customersale`
--
ALTER TABLE `customersale`
  MODIFY `saleId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `customerstock`
--
ALTER TABLE `customerstock`
  MODIFY `custStockId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `customerstockwebp`
--
ALTER TABLE `customerstockwebp`
  MODIFY `custStockId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=112;
--
-- AUTO_INCREMENT for table `customerwebp`
--
ALTER TABLE `customerwebp`
  MODIFY `customerNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;
--
-- AUTO_INCREMENT for table `fucuation`
--
ALTER TABLE `fucuation`
  MODIFY `recordNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=267;
--
-- AUTO_INCREMENT for table `staff`
--
ALTER TABLE `staff`
  MODIFY `staffNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `stock`
--
ALTER TABLE `stock`
  MODIFY `stockId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;
--
-- AUTO_INCREMENT for table `stockwebp`
--
ALTER TABLE `stockwebp`
  MODIFY `stockId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=74;
--
-- AUTO_INCREMENT for table `useraccounts`
--
ALTER TABLE `useraccounts`
  MODIFY `accountId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `branchmanager`
--
ALTER TABLE `branchmanager`
  ADD CONSTRAINT `branchmanager_ibfk_1` FOREIGN KEY (`branchNo`) REFERENCES `branch` (`branchNo`),
  ADD CONSTRAINT `branchmanager_ibfk_2` FOREIGN KEY (`accountId`) REFERENCES `useraccounts` (`accountId`);

--
-- Constraints for table `customer`
--
ALTER TABLE `customer`
  ADD CONSTRAINT `customer_ibfk_2` FOREIGN KEY (`branchNo`) REFERENCES `branch` (`branchNo`);

--
-- Constraints for table `customerpurchase`
--
ALTER TABLE `customerpurchase`
  ADD CONSTRAINT `customerpurchase_ibfk_1` FOREIGN KEY (`customerNo`) REFERENCES `customer` (`customerNo`),
  ADD CONSTRAINT `customerpurchase_ibfk_2` FOREIGN KEY (`stockId`) REFERENCES `stock` (`stockId`),
  ADD CONSTRAINT `customerpurchase_ibfk_3` FOREIGN KEY (`custStockId`) REFERENCES `customerstock` (`custStockId`);

--
-- Constraints for table `customersale`
--
ALTER TABLE `customersale`
  ADD CONSTRAINT `customersale_ibfk_1` FOREIGN KEY (`customerNo`) REFERENCES `customer` (`customerNo`),
  ADD CONSTRAINT `customersale_ibfk_2` FOREIGN KEY (`stockId`) REFERENCES `stock` (`stockId`),
  ADD CONSTRAINT `customersale_ibfk_3` FOREIGN KEY (`custStockId`) REFERENCES `customerstock` (`custStockId`);

--
-- Constraints for table `customerstock`
--
ALTER TABLE `customerstock`
  ADD CONSTRAINT `customerstock_ibfk_1` FOREIGN KEY (`customerNo`) REFERENCES `customer` (`customerNo`),
  ADD CONSTRAINT `customerstock_ibfk_2` FOREIGN KEY (`stockId`) REFERENCES `stock` (`stockId`);

--
-- Constraints for table `customerstockwebp`
--
ALTER TABLE `customerstockwebp`
  ADD CONSTRAINT `customerstockwebp_ibfk_1` FOREIGN KEY (`customerNo`) REFERENCES `customerwebp` (`customerNo`),
  ADD CONSTRAINT `customerstockwebp_ibfk_2` FOREIGN KEY (`stockId`) REFERENCES `stockwebp` (`stockId`);

--
-- Constraints for table `customerwebp`
--
ALTER TABLE `customerwebp`
  ADD CONSTRAINT `customerwebp_ibfk_1` FOREIGN KEY (`branchNo`) REFERENCES `branchwebp` (`branchNo`);

--
-- Constraints for table `fucuation`
--
ALTER TABLE `fucuation`
  ADD CONSTRAINT `fucuation_ibfk_1` FOREIGN KEY (`stockId`) REFERENCES `stock` (`stockId`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
