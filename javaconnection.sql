-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 06, 2026 at 04:00 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `javaconnection`
--

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `nip` varchar(20) NOT NULL,
  `tgl_lhr` varchar(255) DEFAULT NULL,
  `nm_kar` varchar(100) NOT NULL,
  `tem_lhr` varchar(100) DEFAULT NULL,
  `jabatan` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`nip`, `tgl_lhr`, `nm_kar`, `tem_lhr`, `jabatan`) VALUES
('K001', '2006-12-26', 'Nei', 'Padang', 'Data Scientist'),
('K002', '1993-07-31', 'Sai', 'Bandung', 'Game Dev'),
('K003', '1990-12-12', 'Mad', 'Ngawi', 'Quality Assurance'),
('K004', '1111-11-11', 'Usman', 'Nigeria', 'Pro Hengker Tzy'),
('K005', '2000-12-21', 'Ahmar', 'Papua Nukieu', 'Math GOD'),
('K006', '0666-06-06', 'EL', 'Merauke', 'Unemployment');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`nip`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
