-- MySQL Workbench Synchronization
-- Generated: 2025-04-28 21:09
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: Aluno_Noite

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

ALTER SCHEMA `ordens_serviço_assistencia`  DEFAULT CHARACTER SET utf8  DEFAULT COLLATE utf8_general_ci ;

ALTER TABLE `ordens_serviço_assistencia`.`Funcionarios` 
CHARACTER SET = utf8 , COLLATE = utf8_general_ci ;

ALTER TABLE `ordens_serviço_assistencia`.`Clientes` 
CHARACTER SET = utf8 , COLLATE = utf8_general_ci ;

ALTER TABLE `ordens_serviço_assistencia`.`Aparelhos` 
CHARACTER SET = utf8 , COLLATE = utf8_general_ci ;

ALTER TABLE `ordens_serviço_assistencia`.`OS` 
CHARACTER SET = utf8 , COLLATE = utf8_general_ci ;

ALTER TABLE `ordens_serviço_assistencia`.`Orcamentos` 
CHARACTER SET = utf8 , COLLATE = utf8_general_ci ;

ALTER TABLE `ordens_serviço_assistencia`.`Itens_Orcamento` 
CHARACTER SET = utf8 , COLLATE = utf8_general_ci ;

CREATE TABLE IF NOT EXISTS `ordens_serviço_assistencia`.`Historico_OS` (
  `idHistorico_OS` INT(11) NOT NULL AUTO_INCREMENT,
  `OS` INT(11) NOT NULL,
  `data` DATETIME NOT NULL,
  `evento` VARCHAR(100) NOT NULL,
  `obs` TEXT NOT NULL,
  PRIMARY KEY (`idHistorico_OS`),
  UNIQUE INDEX `idHistorico_OS_UNIQUE` (`idHistorico_OS` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS `ordens_serviço_assistencia`.`orçamentos` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


select * from historico_os;