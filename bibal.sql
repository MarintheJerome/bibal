-- --------------------------------------------------------
-- Hôte :                        127.0.0.1
-- Version du serveur:           10.1.18-MariaDB - mariadb.org binary distribution
-- SE du serveur:                Win64
-- HeidiSQL Version:             9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Export de la structure de table bibal. auteur
CREATE TABLE IF NOT EXISTS `auteur` (
  `idAuteur` int(11) NOT NULL AUTO_INCREMENT,
  `nomAuteur` varchar(255) DEFAULT NULL,
  `prenomAuteur` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idAuteur`)
) ENGINE=InnoDB AUTO_INCREMENT=530 DEFAULT CHARSET=latin1;

-- Export de données de la table bibal.auteur : ~0 rows (environ)
/*!40000 ALTER TABLE `auteur` DISABLE KEYS */;
/*!40000 ALTER TABLE `auteur` ENABLE KEYS */;


-- Export de la structure de table bibal. emprunt
CREATE TABLE IF NOT EXISTS `emprunt` (
  `idUsager` int(11) NOT NULL,
  `idExemplaire` int(11) NOT NULL,
  `dateDebut` date DEFAULT NULL,
  `durée` int(11) DEFAULT NULL,
  `dateRetourEffective` date DEFAULT NULL,
  PRIMARY KEY (`idUsager`,`idExemplaire`),
  KEY `FKEmprunt674792` (`idExemplaire`),
  KEY `FKEmprunt886677` (`idUsager`),
  CONSTRAINT `FKEmprunt674792` FOREIGN KEY (`idExemplaire`) REFERENCES `exemplaire` (`idExemplaire`),
  CONSTRAINT `FKEmprunt886677` FOREIGN KEY (`idUsager`) REFERENCES `usager` (`idUsager`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Export de données de la table bibal.emprunt : ~0 rows (environ)
/*!40000 ALTER TABLE `emprunt` DISABLE KEYS */;
/*!40000 ALTER TABLE `emprunt` ENABLE KEYS */;


-- Export de la structure de table bibal. exemplaire
CREATE TABLE IF NOT EXISTS `exemplaire` (
  `idExemplaire` int(11) NOT NULL AUTO_INCREMENT,
  `etatExemplaire` varchar(255) DEFAULT NULL,
  `ISBN` varchar(255) NOT NULL,
  PRIMARY KEY (`idExemplaire`),
  KEY `FKExemplaire431975` (`ISBN`),
  CONSTRAINT `FKExemplaire431975` FOREIGN KEY (`ISBN`) REFERENCES `oeuvre` (`ISBN`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=latin1;

-- Export de données de la table bibal.exemplaire : ~0 rows (environ)
/*!40000 ALTER TABLE `exemplaire` DISABLE KEYS */;
/*!40000 ALTER TABLE `exemplaire` ENABLE KEYS */;


-- Export de la structure de table bibal. oeuvre
CREATE TABLE IF NOT EXISTS `oeuvre` (
  `ISBN` varchar(255) NOT NULL,
  `nomOeuvre` varchar(255) NOT NULL,
  `titre` varchar(255) NOT NULL,
  `dateParution` date DEFAULT NULL,
  `nbReservation` int(11) NOT NULL DEFAULT '0',
  `dateEdition` date DEFAULT NULL,
  `resume` text,
  `numero` int(11) DEFAULT NULL,
  `periodicite` int(11) DEFAULT NULL,
  `idAuteur` int(11) DEFAULT NULL,
  PRIMARY KEY (`ISBN`),
  KEY `FKOeuvre185793` (`idAuteur`),
  CONSTRAINT `FKOeuvre185793` FOREIGN KEY (`idAuteur`) REFERENCES `auteur` (`idAuteur`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Export de données de la table bibal.oeuvre : ~0 rows (environ)
/*!40000 ALTER TABLE `oeuvre` DISABLE KEYS */;
/*!40000 ALTER TABLE `oeuvre` ENABLE KEYS */;


-- Export de la structure de table bibal. reservation
CREATE TABLE IF NOT EXISTS `reservation` (
  `idUsager` int(11) NOT NULL,
  `ISBN` varchar(255) NOT NULL,
  `dateReservation` date NOT NULL,
  `etatReservation` varchar(255) NOT NULL,
  PRIMARY KEY (`idUsager`,`ISBN`),
  KEY `FKReservatio87873` (`ISBN`),
  KEY `FKReservatio905104` (`idUsager`),
  CONSTRAINT `FKReservatio87873` FOREIGN KEY (`ISBN`) REFERENCES `oeuvre` (`ISBN`),
  CONSTRAINT `FKReservatio905104` FOREIGN KEY (`idUsager`) REFERENCES `usager` (`idUsager`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Export de données de la table bibal.reservation : ~0 rows (environ)
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;


-- Export de la structure de table bibal. usager
CREATE TABLE IF NOT EXISTS `usager` (
  `idUsager` int(11) NOT NULL AUTO_INCREMENT,
  `nomUsager` varchar(255) NOT NULL,
  `prenomUsager` varchar(255) NOT NULL,
  `Mail` varchar(255) DEFAULT NULL,
  `Adresse` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idUsager`)
) ENGINE=InnoDB AUTO_INCREMENT=369 DEFAULT CHARSET=latin1;

-- Export de données de la table bibal.usager : ~0 rows (environ)
/*!40000 ALTER TABLE `usager` DISABLE KEYS */;
/*!40000 ALTER TABLE `usager` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
