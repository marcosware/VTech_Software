-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema ordens_serviço_assistencia
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ordens_serviço_assistencia
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ordens_serviço_assistencia` DEFAULT CHARACTER SET utf8mb3 ;
USE `ordens_serviço_assistencia` ;

-- -----------------------------------------------------
-- Table `ordens_serviço_assistencia`.`clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ordens_serviço_assistencia`.`clientes` (
  `idClientes` INT NOT NULL AUTO_INCREMENT,
  `nome_clientes` VARCHAR(50) NOT NULL,
  `cpf` CHAR(11) NOT NULL,
  `telefone_Cliente` CHAR(11) NOT NULL,
  PRIMARY KEY (`idClientes`),
  UNIQUE INDEX `idClientes_UNIQUE` (`idClientes` ASC) VISIBLE,
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ordens_serviço_assistencia`.`os`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ordens_serviço_assistencia`.`os` (
  `id_OS` INT NOT NULL AUTO_INCREMENT,
  `Cliente` INT NOT NULL,
  `Aparelhos` INT NOT NULL,
  `Descricao` VARCHAR(500) NOT NULL,
  `data_abertura` DATETIME NOT NULL,
  `data_Previsao` DATE NOT NULL,
  `data_Conclusao` DATE NULL DEFAULT NULL,
  `Funcionario` INT NOT NULL,
  `Orcamento` INT NOT NULL,
  `Status_OS` ENUM('INICIADA', 'EM ANDAMENTO', 'CONCLUÍDA') NULL DEFAULT NULL,
  PRIMARY KEY (`id_OS`),
  UNIQUE INDEX `id_OS_UNIQUE` (`id_OS` ASC) VISIBLE,
  INDEX `Clientes _idx` (`Cliente` ASC) VISIBLE,
  INDEX `Aparelhos _idx` (`Aparelhos` ASC) VISIBLE,
  INDEX `Funcionarios _idx` (`Funcionario` ASC) VISIBLE,
  INDEX `Orcamento_idx` (`Orcamento` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ordens_serviço_assistencia`.`comprovantes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ordens_serviço_assistencia`.`comprovantes` (
  `id_Comprovantes` INT NOT NULL AUTO_INCREMENT,
  `Cliente` INT NOT NULL,
  `valor` INT NOT NULL,
  `Data` DATE NOT NULL,
  `Observacao` TEXT NULL DEFAULT NULL,
  `OS_comprovante` INT NOT NULL,
  PRIMARY KEY (`id_Comprovantes`),
  UNIQUE INDEX `id_Comprovantes_UNIQUE` (`id_Comprovantes` ASC) VISIBLE,
  INDEX `cliente_idx` (`Cliente` ASC) VISIBLE,
  INDEX `OS_idx` (`OS_comprovante` ASC) VISIBLE,
  CONSTRAINT `cliente`
    FOREIGN KEY (`Cliente`)
    REFERENCES `ordens_serviço_assistencia`.`clientes` (`idClientes`),
  CONSTRAINT `OS_comprovante `
    FOREIGN KEY (`OS_comprovante`)
    REFERENCES `ordens_serviço_assistencia`.`os` (`id_OS`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ordens_serviço_assistencia`.`historico_os`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ordens_serviço_assistencia`.`historico_os` (
  `idHistorico_OS` INT NOT NULL AUTO_INCREMENT,
  `OS` INT NOT NULL,
  `data` DATETIME NOT NULL,
  `evento` VARCHAR(100) NOT NULL,
  `obs` TEXT NOT NULL,
  PRIMARY KEY (`idHistorico_OS`),
  UNIQUE INDEX `idHistorico_OS_UNIQUE` (`idHistorico_OS` ASC) VISIBLE,
  INDEX `os _idx` (`OS` ASC) VISIBLE,
  CONSTRAINT `os `
    FOREIGN KEY (`OS`)
    REFERENCES `ordens_serviço_assistencia`.`os` (`id_OS`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ordens_serviço_assistencia`.`orcamentos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ordens_serviço_assistencia`.`orcamentos` (
  `idOrçamentos` INT NOT NULL AUTO_INCREMENT,
  `Cliente` INT NOT NULL,
  `Data_Orcamento` DATETIME NOT NULL,
  `descricao` TEXT NOT NULL,
  `Valor_estimado` DECIMAL(10,2) NOT NULL,
  `OS` INT NOT NULL,
  PRIMARY KEY (`idOrçamentos`),
  UNIQUE INDEX `idOrçamentos_UNIQUE` (`idOrçamentos` ASC) VISIBLE,
  INDEX `cliente_idx` (`Cliente` ASC) VISIBLE,
  INDEX `os _idx` (`OS` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ordens_serviço_assistencia`.`itens_orcamento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ordens_serviço_assistencia`.`itens_orcamento` (
  `id_Itens_Orcamento` INT NOT NULL AUTO_INCREMENT,
  `Orcamento` INT NOT NULL,
  `descricao_Item` VARCHAR(300) NOT NULL,
  `quantidade` INT NOT NULL,
  `valor_Unitario` DECIMAL(10,2) NOT NULL,
  `Subtotal` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id_Itens_Orcamento`),
  UNIQUE INDEX `id_Itens_Orcamento_UNIQUE` (`id_Itens_Orcamento` ASC) VISIBLE,
  INDEX `Orcamento _idx` (`Orcamento` ASC) VISIBLE,
  CONSTRAINT `orcamento `
    FOREIGN KEY (`Orcamento`)
    REFERENCES `ordens_serviço_assistencia`.`orcamentos` (`idOrçamentos`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ordens_serviço_assistencia`.`produtos_clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ordens_serviço_assistencia`.`produtos_clientes` (
  `idAparelhos` INT NOT NULL AUTO_INCREMENT,
  `Marca` VARCHAR(20) NOT NULL,
  `modelo` VARCHAR(50) NOT NULL,
  `numero_Serie` VARCHAR(45) NOT NULL,
  `cliente_Produto` INT NOT NULL,
  PRIMARY KEY (`idAparelhos`),
  UNIQUE INDEX `idProdutos_UNIQUE` (`idAparelhos` ASC) VISIBLE,
  UNIQUE INDEX `numero_Serie_UNIQUE` (`numero_Serie` ASC) VISIBLE,
  INDEX `clientes _idx` (`cliente_Produto` ASC) VISIBLE,
  CONSTRAINT `cliente_Produto`
    FOREIGN KEY (`cliente_Produto`)
    REFERENCES `ordens_serviço_assistencia`.`clientes` (`idClientes`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ordens_serviço_assistencia`.`usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ordens_serviço_assistencia`.`usuarios` (
  `id_usuario` INT NOT NULL AUTO_INCREMENT,
  `nome_usuario` VARCHAR(100) NOT NULL,
  `email_institucional` VARCHAR(150) NOT NULL,
  `codigo_registro` VARCHAR(10) NOT NULL,
  `perfil` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE INDEX `id_usuario` (`id_usuario` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
