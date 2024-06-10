SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


CREATE SCHEMA IF NOT EXISTS `mysql` DEFAULT CHARACTER SET utf8mb4 ;
USE `mysql` ;


CREATE TABLE IF NOT EXISTS `mysql`.`tb_amigos` (
  `id_amigo` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `telefone` BIGINT(11) NOT NULL UNIQUE,
  `qtd_emprestimos` INT NOT NULL,
  PRIMARY KEY (`id_amigo`)
);


CREATE TABLE IF NOT EXISTS `mysql`.`tb_ferramentas` (
  `id_ferramenta` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `marca` VARCHAR(45) NOT NULL,
  `custo_aquisicao` DECIMAL(5,2) NOT NULL,
  PRIMARY KEY (`id_ferramenta`)
);


CREATE TABLE IF NOT EXISTS `mysql`.`tb_emprestimos` (
  `id_emprestimo` INT NOT NULL AUTO_INCREMENT,
  `fk_amigo` INT NOT NULL,
  `fk_ferramenta` INT NOT NULL,
  `data_emprestimo` DATE NOT NULL,
  `data_devolucao` DATE NULL,
  `status` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id_emprestimo`),
  FOREIGN KEY(fk_amigo) REFERENCES tb_amigos(id_amigo) ON DELETE CASCADE,
  FOREIGN KEY(fk_ferramenta) REFERENCES tb_ferramentas(id_ferramenta) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS `mysql`.`tb_eventos` (
  `id_evento` INT NOT NULL AUTO_INCREMENT,
  `fk_amigo` INT NOT NULL,
  `nome_evento` VARCHAR(50) NOT NULL,
  `local_evento` VARCHAR(50) NOT NULL,
  `data_evento` DATE NOT NULL,
  PRIMARY KEY (`id_evento`),
  FOREIGN KEY(fk_amigo) REFERENCES tb_amigos(id_amigo)
);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


INSERT INTO `mysql`.`tb_amigos`
(nome, telefone, qtd_emprestimos)
VALUES
("Ricardo", 4899167906, 0),
("Edson", 4884194059, 1),
("Richard", 48912345678, 0),
("Irene", 48987654321, 1);


INSERT INTO `mysql`.`tb_ferramentas`
(nome, marca, custo_aquisicao)
VALUES
("Martelo", "Stanley", 60),
("Chave inglesa", "Vonder", 58.90),
("Chave Phillips", "Sparta", 5.87),
("Alicate", "Tramontina", 20);


INSERT INTO `mysql`.`tb_emprestimos`
(fk_amigo, fk_ferramenta, data_emprestimo, data_devolucao, status)
VALUES
(2, 4, '2023-02-18', '2023-03-01', true),
(4, 1, '2023-04-25', null, false);
