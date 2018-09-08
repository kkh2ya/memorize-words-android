-- phpMyAdmin SQL Dump
-- version 4.7.8
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Feb 21, 2018 at 04:17 PM
-- Server version: 5.7.21-0ubuntu0.16.04.1
-- PHP Version: 7.0.25-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `memorize_english`
--

-- --------------------------------------------------------

--
-- Table structure for table `userlist`
--

CREATE TABLE `userlist` (
  `words` varchar(50) NOT NULL,
  `answer` varchar(100) NOT NULL,
  `sentence` varchar(300) NOT NULL,
  `sentence_answer` varchar(70) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `userlist`
--

INSERT INTO `userlist` (`words`, `answer`, `sentence`, `sentence_answer`) VALUES
('make a lie', '嘘をつく', 'Making a lie is a habit.', 'Making a lie'),
('yearn for', '憧れる', 'She yearned for city life.', 'yearned for'),
('recommend', '進める', 'The hotel\'s new restaurant comes highly recommended.', 'recommended'),
('reconcile', '仲直りをする', 'He has recently been reconciled with his wife.', 'reconciled'),
('sue', '訴える', 'They threatened to sue if the work was not completed. ', 'sue'),
('leave', '離れる', 'Come on, it\'s time we left. ', 'left'),
('miss', '懐かしい', 'As time goes by, I miss him even more.', 'miss'),
('tease', 'からかう', 'I used to get teased about my name.', 'teased'),
('rain stops', '雨が止む', 'Let\'s wait till the rain stops.', 'the rain stops'),
('catch', '捕まえる', 'He caught hold of her arm as she tried to push past him.', 'caught'),
('rumor', '噂', 'I fear lest the rumor may be true.', 'rumor'),
('insure', '保険に入る', 'The painting is insured for $1 million.', 'insured'),
('apply', '申し込む', 'You should apply in person.', 'apply'),
('clean up', '片づける', 'Who\'s going to clean up this mess?', 'clean up'),
('call the police', '交番に届ける', 'Why don\'t you call the police?', 'call the police'),
('near', '身近だ', 'Only the nearest relatives were present at the funeral.', 'nearest'),
('the elderly', 'お年寄り', 'I deliver mobile meals to the elderly.', 'the elderly'),
('timid person', '小心者', 'How about a timid person who keeps saying sorry?', 'timid person'),
('make a loss', '損する', 'I never expected to make a loss.', 'make a loss'),
('make a profit', '得する', 'No, I managed to make a profit.', 'make a profit'),
('let', '許す', 'Don\'t let her upset you.', 'let'),
('refrain', '遠慮する', 'Please refrain from smoking.', 'refrain'),
('kick', '蹴る', 'The boys were kicking a ball around in the yard.', 'kicking'),
('became stable', '落ち着く', 'Oscar was sent to England once he became stable.', 'became stable'),
('revealed', 'ばれる', 'It was revealed that important evidence had been suppressed.', 'revealed'),
('born', '生まれる', 'She was born into a very musical family.', 'born'),
('miser', 'ケチ', 'The miser is always poor.', 'miser'),
('conscious', '気が付く', 'He became acutely conscious of having failed his parents. ', 'conscious'),
('have a feeling', '気がする', 'I have a feeling that something unpleasant will happen.', 'have a feeling'),
('on purpose', 'わざと', 'I didn\'t do it on purpose.', 'on purpose'),
('election', '選挙', 'How many candidates are standing for election?', 'election'),
('as one pleases', 'わがままだ', 'Freedom without responsibility is licence to do as one pleases.', 'as one pleases'),
('got a promotion', '昇進する', 'I hear you got a promotion recently.', 'got a promotion'),
('win the lottery', '宝くじに当たる', 'What would you do if you could win the lottery?', 'win the lottery'),
('wounded', '傷つく', 'She felt deeply wounded by his cruel remarks.', 'wounded'),
('what a waste', 'もったいない', 'What a waste of time!', 'What a waste'),
('angry', '悔しい', 'He felt angry at the injustice of the situation.', 'angry'),
('pig', '豚', 'useful pig', 'pig');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
