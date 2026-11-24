-- Execute com o aplicativo fechado, após criar e conferir o esquema de destino vazio.
-- Use o cliente mysql em modo batch, sem --force, para abortar e reverter se houver erro.
START TRANSACTION;

INSERT INTO sharetools.tb_amigos (id_amigo, nome, telefone)
SELECT id_amigo, nome, telefone FROM mysql.tb_amigos;

INSERT INTO sharetools.tb_ferramentas (id_ferramenta, nome, marca, custo_aquisicao)
SELECT id_ferramenta, nome, marca, custo_aquisicao FROM mysql.tb_ferramentas;

INSERT INTO sharetools.tb_emprestimos
    (id_emprestimo, fk_amigo, fk_ferramenta, data_emprestimo, data_devolucao, status)
SELECT id_emprestimo, fk_amigo, fk_ferramenta, data_emprestimo, data_devolucao,
    data_devolucao IS NOT NULL
FROM mysql.tb_emprestimos;

COMMIT;
