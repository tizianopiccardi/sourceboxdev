-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generato il: 04 gen, 2012 at 04:14 
-- Versione MySQL: 5.5.8
-- Versione PHP: 5.3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `sourcebox`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `boxes`
--

CREATE TABLE IF NOT EXISTS `boxes` (
  `idboxes` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `creation` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `alias` varchar(45) CHARACTER SET cp1251 COLLATE cp1251_general_cs NOT NULL,
  `language` varchar(45) DEFAULT NULL,
  `lastvisit` timestamp NULL DEFAULT NULL,
  `readonly` int(11) DEFAULT NULL,
  `password` varchar(60) DEFAULT '',
  `lastevent` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`idboxes`),
  KEY `aliasIndex` (`alias`),
  KEY `lasteventIndex` (`lastevent`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `inbox`
--

CREATE TABLE IF NOT EXISTS `inbox` (
  `idinbox` int(11) NOT NULL AUTO_INCREMENT,
  `since` datetime DEFAULT NULL,
  `boxes_idboxes` int(11) unsigned NOT NULL,
  `users_iduser` int(10) unsigned NOT NULL,
  `cursor_line` int(11) DEFAULT NULL,
  `cursor_column` int(11) DEFAULT NULL,
  PRIMARY KEY (`idinbox`),
  UNIQUE KEY `uniqueJoin` (`boxes_idboxes`,`users_iduser`),
  KEY `fk_inbox_boxes1` (`boxes_idboxes`),
  KEY `fk_inbox_users1` (`users_iduser`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `messages`
--

CREATE TABLE IF NOT EXISTS `messages` (
  `idmessages` int(11) NOT NULL AUTO_INCREMENT,
  `text` text,
  `time` timestamp NULL DEFAULT NULL,
  `iduser` int(10) unsigned DEFAULT NULL,
  `idbox` int(11) unsigned NOT NULL,
  PRIMARY KEY (`idmessages`),
  KEY `fk_messages_users1` (`iduser`),
  KEY `fk_messages_boxes1` (`idbox`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=20 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `operations`
--

CREATE TABLE IF NOT EXISTS `operations` (
  `idoperation` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `from_line` int(10) unsigned NOT NULL,
  `to_line` int(10) unsigned NOT NULL,
  `from_char` int(10) unsigned NOT NULL,
  `to_char` int(10) unsigned NOT NULL,
  `string` text NOT NULL,
  `boxes_idboxes` int(11) unsigned NOT NULL,
  PRIMARY KEY (`idoperation`) USING HASH,
  KEY `op_box` (`boxes_idboxes`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=214 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `revisions`
--

CREATE TABLE IF NOT EXISTS `revisions` (
  `idrevision` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `source` text,
  `idbox` int(11) unsigned NOT NULL,
  `rev` int(11) DEFAULT NULL,
  `lastoperation` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`idrevision`),
  UNIQUE KEY `idbbox_rev` (`idbox`,`rev`),
  KEY `fk_revisions_boxes` (`idbox`),
  KEY `fk_revisions_operations1` (`lastoperation`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `iduser` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `last_activity` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`iduser`),
  KEY `lastactivity` (`last_activity`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `inbox`
--
ALTER TABLE `inbox`
  ADD CONSTRAINT `fk_inbox_boxes1` FOREIGN KEY (`boxes_idboxes`) REFERENCES `boxes` (`idboxes`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_inbox_users1` FOREIGN KEY (`users_iduser`) REFERENCES `users` (`iduser`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Limiti per la tabella `messages`
--
ALTER TABLE `messages`
  ADD CONSTRAINT `fk_messages_boxes1` FOREIGN KEY (`idbox`) REFERENCES `boxes` (`idboxes`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_messages_users1` FOREIGN KEY (`iduser`) REFERENCES `users` (`iduser`) ON DELETE SET NULL ON UPDATE NO ACTION;

--
-- Limiti per la tabella `operations`
--
ALTER TABLE `operations`
  ADD CONSTRAINT `fk_operations_boxes` FOREIGN KEY (`boxes_idboxes`) REFERENCES `boxes` (`idboxes`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `revisions`
--
ALTER TABLE `revisions`
  ADD CONSTRAINT `fk_revisions_boxes` FOREIGN KEY (`idbox`) REFERENCES `boxes` (`idboxes`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_revisions_operations1` FOREIGN KEY (`lastoperation`) REFERENCES `operations` (`idoperation`) ON DELETE NO ACTION ON UPDATE NO ACTION;
