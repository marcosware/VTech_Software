-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Ordens_Serviço_Assistencia
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Ordens_Serviço_Assistencia
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Ordens_Serviço_Assistencia` DEFAULT CHARACTER SET utf8 ;
USE `Ordens_Serviço_Assistencia` ;

-- -----------------------------------------------------
-- Table `Ordens_Serviço_Assistencia`.`Funcionarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ordens_Serviço_Assistencia`.`Funcionarios` (
  `ID_Funcionarios` INT NOT NULL AUTO_INCREMENT,
  `nome_Funcionario` VARCHAR(50) NOT NULL,
  `cpf` CHAR(11) NOT NULL,
  `telefone` CHAR(11) NOT NULL,
  `especialidade` VARCHAR(50) NOT NULL,
  UNIQUE INDEX `ID_Funcionarios_UNIQUE` (`ID_Funcionarios` ASC) VISIBLE,
  PRIMARY KEY (`cpf`),
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC) VISIBLE)
ENGINE = InnoDB;

select * from funcionarios;
-- -----------------------------------------------------
-- Table `Ordens_Serviço_Assistencia`.`Clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ordens_Serviço_Assistencia`.`Clientes` (
  `idClientes` INT NOT NULL AUTO_INCREMENT,
  `nome_clientes` VARCHAR(50) NOT NULL,
  `cpf` CHAR(11) NOT NULL,
  `telefone_Cliente` CHAR(11) NOT NULL,
  UNIQUE INDEX `idClientes_UNIQUE` (`idClientes` ASC) VISIBLE,
  PRIMARY KEY (`cpf`),
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ordens_Serviço_Assistencia`.`Aparelhos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ordens_Serviço_Assistencia`.`Aparelhos` (
  `idAparelhos` INT NOT NULL AUTO_INCREMENT,
  `Marca` VARCHAR(20) NOT NULL,
  `modelo` VARCHAR(50) NOT NULL,
  `numero_Serie` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `idProdutos_UNIQUE` (`idAparelhos` ASC) VISIBLE,
  PRIMARY KEY (`idAparelhos`),
  UNIQUE INDEX `numero_Serie_UNIQUE` (`numero_Serie` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ordens_Serviço_Assistencia`.`OS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ordens_Serviço_Assistencia`.`OS` (
  `id_OS` INT NOT NULL AUTO_INCREMENT,
  `Cliente` CHAR(11) NOT NULL,
  `Aparelhos` INT NOT NULL,
  `Descricao` VARCHAR(500) NOT NULL,
  `data_abertura` DATETIME NOT NULL,
  `data_Previsao` DATE NOT NULL,
  `data_Conclusao` DATE NULL,
  PRIMARY KEY (`id_OS`),
  UNIQUE INDEX `id_OS_UNIQUE` (`id_OS` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ordens_Serviço_Assistencia`.`Orcamentos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ordens_Serviço_Assistencia`.`Orcamentos` (
  `idOrçamentos` INT NOT NULL AUTO_INCREMENT,
  `Cliente` CHAR(11) NOT NULL,
  `Data_Orcamento` DATETIME NOT NULL,
  `descricao` TEXT NOT NULL,
  `Valor_estimado` DECIMAL(10,2) NOT NULL,
  `OS` INT NOT NULL,
  PRIMARY KEY (`idOrçamentos`),
  UNIQUE INDEX `idOrçamentos_UNIQUE` (`idOrçamentos` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ordens_Serviço_Assistencia`.`Itens_Orcamento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ordens_Serviço_Assistencia`.`Itens_Orcamento` (
  `id_Itens_Orcamento` INT NOT NULL AUTO_INCREMENT,
  `Orcamento` INT NOT NULL,
  `descricao_Item` VARCHAR(300) NOT NULL,
  `quantidade` INT NOT NULL,
  `valor_Unitario` DECIMAL(10,2) NOT NULL,
  `Subtotal` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id_Itens_Orcamento`),
  UNIQUE INDEX `id_Itens_Orcamento_UNIQUE` (`id_Itens_Orcamento` ASC) VISIBLE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
