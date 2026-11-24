CREATE DATABASE IF NOT EXISTS sharetools CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE sharetools;

CREATE TABLE IF NOT EXISTS tb_amigos (
    id_amigo INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    telefone BIGINT NOT NULL UNIQUE,
    qtd_emprestimos INT NOT NULL DEFAULT 0
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS tb_ferramentas (
    id_ferramenta INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(45) NOT NULL,
    marca VARCHAR(45) NOT NULL,
    custo_aquisicao DECIMAL(5,2) NOT NULL,
    CONSTRAINT ck_ferramenta_custo CHECK (custo_aquisicao >= 0)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS tb_emprestimos (
    id_emprestimo INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    fk_amigo INT NOT NULL,
    fk_ferramenta INT NOT NULL,
    data_emprestimo DATE NOT NULL,
    data_devolucao DATE NULL,
    status TINYINT(1) NOT NULL,
    INDEX ix_emprestimo_ferramenta_periodo (fk_ferramenta, data_emprestimo),
    CONSTRAINT fk_emprestimo_amigo FOREIGN KEY (fk_amigo) REFERENCES tb_amigos (id_amigo),
    CONSTRAINT fk_emprestimo_ferramenta FOREIGN KEY (fk_ferramenta) REFERENCES tb_ferramentas (id_ferramenta),
    CONSTRAINT ck_emprestimo_datas CHECK (data_devolucao IS NULL OR data_devolucao >= data_emprestimo),
    CONSTRAINT ck_emprestimo_status CHECK (status = (data_devolucao IS NOT NULL))
) ENGINE=InnoDB;
