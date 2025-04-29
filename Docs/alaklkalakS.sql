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
  UNIQUE INDEX `ID_Funcionarios_UNIQUE` (`ID_Funcionarios` ASC) VISIBLE,
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC) VISIBLE,
  PRIMARY KEY (`ID_Funcionarios`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ordens_Serviço_Assistencia`.`Clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ordens_Serviço_Assistencia`.`Clientes` (
  `idClientes` INT NOT NULL AUTO_INCREMENT,
  `nome_clientes` VARCHAR(50) NOT NULL,
  `cpf` CHAR(11) NOT NULL,
  `telefone_Cliente` CHAR(11) NOT NULL,
  UNIQUE INDEX `idClientes_UNIQUE` (`idClientes` ASC) VISIBLE,
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC) VISIBLE,
  PRIMARY KEY (`idClientes`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ordens_Serviço_Assistencia`.`Produtos_Clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ordens_Serviço_Assistencia`.`Produtos_Clientes` (
  `idAparelhos` INT NOT NULL AUTO_INCREMENT,
  `Marca` VARCHAR(20) NOT NULL,
  `modelo` VARCHAR(50) NOT NULL,
  `numero_Serie` VARCHAR(45) NOT NULL,
  `cliente_Produto` INT NOT NULL,
  UNIQUE INDEX `idProdutos_UNIQUE` (`idAparelhos` ASC) VISIBLE,
  PRIMARY KEY (`idAparelhos`),
  UNIQUE INDEX `numero_Serie_UNIQUE` (`numero_Serie` ASC) VISIBLE,
  INDEX `clientes _idx` (`cliente_Produto` ASC) VISIBLE,
  CONSTRAINT `cliente_Produto`
    FOREIGN KEY (`cliente_Produto`)
    REFERENCES `Ordens_Serviço_Assistencia`.`Clientes` (`idClientes`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ordens_Serviço_Assistencia`.`Orcamentos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ordens_Serviço_Assistencia`.`Orcamentos` (
  `id_OrCamentos` INT NOT NULL AUTO_INCREMENT,
  `Cliente` INT NOT NULL,
  `Data_Orcamento` DATETIME NOT NULL,
  `descricao` TEXT NOT NULL,
  `Valor_estimado` DECIMAL(10,2) NOT NULL,
  `OS` INT NOT NULL,
  PRIMARY KEY (`id_OrCamentos`),
  UNIQUE INDEX `idOrçamentos_UNIQUE` (`id_OrCamentos` ASC) VISIBLE,
  INDEX `cliente_idx` (`Cliente` ASC) VISIBLE,
  INDEX `OS_idx` (`OS` ASC) VISIBLE,
  CONSTRAINT `cliente`
    FOREIGN KEY (`Cliente`)
    REFERENCES `Ordens_Serviço_Assistencia`.`Clientes` (`idClientes`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `OS`
    FOREIGN KEY (`OS`)
    REFERENCES `Ordens_Serviço_Assistencia`.`OS` (`id_OS`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ordens_Serviço_Assistencia`.`OS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ordens_Serviço_Assistencia`.`OS` (
  `id_OS` INT NOT NULL AUTO_INCREMENT,
  `Cliente` INT NOT NULL,
  `Aparelhos` INT NOT NULL,
  `Descricao` VARCHAR(500) NOT NULL,
  `data_abertura` DATETIME NOT NULL,
  `data_Previsao` DATE NOT NULL,
  `data_Conclusao` DATE NULL,
  `Funcionario` INT NOT NULL,
  `Orcamento` INT NOT NULL,
  PRIMARY KEY (`id_OS`),
  UNIQUE INDEX `id_OS_UNIQUE` (`id_OS` ASC) VISIBLE,
  INDEX `Clientes _idx` (`Cliente` ASC) VISIBLE,
  INDEX `Aparelhos _idx` (`Aparelhos` ASC) VISIBLE,
  INDEX `Funcionarios _idx` (`Funcionario` ASC) VISIBLE,
  INDEX `Orcamento_idx` (`Orcamento` ASC) VISIBLE,
  CONSTRAINT `Clientes `
    FOREIGN KEY (`Cliente`)
    REFERENCES `Ordens_Serviço_Assistencia`.`Clientes` (`cpf`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Aparelhos `
    FOREIGN KEY (`Aparelhos`)
    REFERENCES `Ordens_Serviço_Assistencia`.`Produtos_Clientes` (`idAparelhos`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Funcionarios `
    FOREIGN KEY (`Funcionario`)
    REFERENCES `Ordens_Serviço_Assistencia`.`Funcionarios` (`ID_Funcionarios`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Orcamento`
    FOREIGN KEY (`Orcamento`)
    REFERENCES `Ordens_Serviço_Assistencia`.`Orcamentos` (`id_OrCamentos`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ordens_Serviço_Assistencia`.`Estoque_Pecas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ordens_Serviço_Assistencia`.`Estoque_Pecas` (
  `id_Peca` INT NOT NULL AUTO_INCREMENT,
  `tipo_peca` VARCHAR(60) NOT NULL,
  `Quantidade` INT NOT NULL,
  `Fornecedor` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_Peca`),
  UNIQUE INDEX `id_Peca_UNIQUE` (`id_Peca` ASC) VISIBLE)
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
  `Item` INT NOT NULL,
  PRIMARY KEY (`id_Itens_Orcamento`),
  UNIQUE INDEX `id_Itens_Orcamento_UNIQUE` (`id_Itens_Orcamento` ASC) VISIBLE,
  INDEX `Orcamento _idx` (`Orcamento` ASC) VISIBLE,
  INDEX `item_idx` (`Item` ASC) VISIBLE,
  CONSTRAINT `Orcamento `
    FOREIGN KEY (`Orcamento`)
    REFERENCES `Ordens_Serviço_Assistencia`.`Orcamentos` (`id_OrCamentos`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `item`
    FOREIGN KEY (`Item`)
    REFERENCES `Ordens_Serviço_Assistencia`.`Estoque_Pecas` (`id_Peca`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ordens_Serviço_Assistencia`.`Historico_OS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ordens_Serviço_Assistencia`.`Historico_OS` (
  `idHistorico_OS` INT NOT NULL AUTO_INCREMENT,
  `OS` INT NOT NULL,
  `data` DATETIME NOT NULL,
  `evento` VARCHAR(100) NOT NULL,
  `obs` TEXT NOT NULL,
  PRIMARY KEY (`idHistorico_OS`),
  UNIQUE INDEX `idHistorico_OS_UNIQUE` (`idHistorico_OS` ASC) VISIBLE,
  INDEX `OS_idx` (`OS` ASC) VISIBLE,
  CONSTRAINT `OS`
    FOREIGN KEY (`OS`)
    REFERENCES `Ordens_Serviço_Assistencia`.`OS` (`id_OS`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ordens_Serviço_Assistencia`.`Pagamentos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ordens_Serviço_Assistencia`.`Pagamentos` (
  `idPagamentos` INT NOT NULL AUTO_INCREMENT,
  `OS` INT NOT NULL,
  `data_Pagamento` DATETIME NOT NULL,
  `valor` DECIMAL(10,2) NOT NULL,
  `forma_Pagamento` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`idPagamentos`),
  UNIQUE INDEX `idPagamentos_UNIQUE` (`idPagamentos` ASC) VISIBLE,
  INDEX `OS_idx` (`OS` ASC) VISIBLE,
  CONSTRAINT `OS`
    FOREIGN KEY (`OS`)
    REFERENCES `Ordens_Serviço_Assistencia`.`OS` (`id_OS`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ordens_Serviço_Assistencia`.`Comprovantes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ordens_Serviço_Assistencia`.`Comprovantes` (
  `id_Comprovantes` INT NOT NULL AUTO_INCREMENT,
  `Cliente` INT NOT NULL,
  `valor` INT NOT NULL,
  `Data` DATE NOT NULL,
  `Observacao` TEXT NULL,
  `OS_comprovante` INT NOT NULL,
  PRIMARY KEY (`id_Comprovantes`),
  UNIQUE INDEX `id_Comprovantes_UNIQUE` (`id_Comprovantes` ASC) VISIBLE,
  INDEX `cliente_idx` (`Cliente` ASC) VISIBLE,
  INDEX `valor _idx` (`valor` ASC) VISIBLE,
  INDEX `OS_idx` (`OS_comprovante` ASC) VISIBLE,
  CONSTRAINT `cliente`
    FOREIGN KEY (`Cliente`)
    REFERENCES `Ordens_Serviço_Assistencia`.`Clientes` (`idClientes`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `valor `
    FOREIGN KEY (`valor`)
    REFERENCES `Ordens_Serviço_Assistencia`.`Pagamentos` (`idPagamentos`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `OS_comprovante `
    FOREIGN KEY (`OS_comprovante`)
    REFERENCES `Ordens_Serviço_Assistencia`.`OS` (`id_OS`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
