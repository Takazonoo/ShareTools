package View;

import DAO.*;
import Model.Emprestimo;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

public class TelaEmprestimos extends javax.swing.JFrame {

    private final EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    private java.util.List<Emprestimo> registros = java.util.Collections.emptyList();

    private final AmigoDAO amigoDAO = new AmigoDAO();
    private final FerramentaDAO ferramentaDAO = new FerramentaDAO();
    private static final java.time.format.DateTimeFormatter FORMATO_DATA =
            java.time.format.DateTimeFormatter.ofPattern("dd-MM-uuuu")
                    .withResolverStyle(java.time.format.ResolverStyle.STRICT);

    private static int lerId(String texto) throws Mensagens {
        try {
            int id = Integer.parseInt(texto.strip());
            if (id > 0) return id;
        } catch (NumberFormatException erro) {
            // A mensagem é a mesma para IDs inválidos e ausentes.
        }
        throw new Mensagens("Informe IDs positivos para o amigo e a ferramenta.");
    }

    private static java.sql.Date lerData(String texto) throws Mensagens {
        try {
            java.time.LocalDate data = java.time.LocalDate.parse(texto.strip(), FORMATO_DATA);
            if (data.getYear() < 1000 || data.getYear() > 9999) {
                throw new Mensagens("Informe um ano entre 1000 e 9999.");
            }
            return java.sql.Date.valueOf(data);
        } catch (java.time.format.DateTimeParseException erro) {
            throw new Mensagens("Informe uma data válida no formato DD-MM-AAAA.");
        }
    }

    private static String formatarData(java.sql.Date data) {
        return data == null ? "" : FORMATO_DATA.format(data.toLocalDate());
    }

    private static MaskFormatter mascaraCampo() {
        try {
            MaskFormatter mascara = new MaskFormatter("##-##-####");
            mascara.setPlaceholderCharacter('_');
            return mascara;
        } catch (ParseException erro) {
            throw new IllegalStateException(erro);
        }
    }

    public TelaEmprestimos() {
        initComponents();
        tableEmprestimos.getSelectionModel().addListSelectionListener(evento -> {
            int linha = tableEmprestimos.getSelectedRow();
            if (!evento.getValueIsAdjusting() && linha >= 0) {
                preencherCampos(registros.get(tableEmprestimos.convertRowIndexToModel(linha)));
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent evento) {
                atualizarTabela();
            }
        });
    }

    private void initComponents() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        campoDataPed.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        campoDataDev.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        javax.swing.JPanel tela = InterfaceShareTools.janela(this, "Empréstimos",
                "Use os IDs das tabelas de amigos e ferramentas para registrar um empréstimo.",
                InterfaceShareTools.botao("Voltar ao menu", false, this::dispose));
        tela.add(InterfaceShareTools.cadastro(tableEmprestimos, null,
                InterfaceShareTools.botao("Cadastrar", true, () -> btnCadastrarActionPerformed()),
                InterfaceShareTools.botao("Editar", false, () -> btnEditarActionPerformed()),
                InterfaceShareTools.botao("Excluir", false, () -> btnExcluirActionPerformed()),
                InterfaceShareTools.campo("ID do amigo", campoAmigo),
                InterfaceShareTools.campo("ID da ferramenta", campoFerramenta),
                InterfaceShareTools.campo("Empréstimo (DD-MM-AAAA)", campoDataPed),
                InterfaceShareTools.campo("Devolução (DD-MM-AAAA, opcional)", campoDataDev)),
                java.awt.BorderLayout.CENTER);
        InterfaceShareTools.posicionar(this);
    }

    private void btnCadastrarActionPerformed() {
        salvar(0);
    }

    private void btnEditarActionPerformed() {
        try {
            salvar(Mensagens.idSelecionado(tableEmprestimos));
        } catch (Mensagens erro) {
            Mensagens.aviso(this, erro);
        }
    }

    private void btnExcluirActionPerformed() {
        try {
            int id = Mensagens.idSelecionado(tableEmprestimos);
            if (!confirmar("Deseja excluir o registro selecionado?")) return;
            if (!emprestimoDAO.DeleteEmprestimoBD(id)) {
                throw new Mensagens("O empréstimo já foi removido.");
            }
            atualizarTabela();
            limparCampos();
        } catch (Mensagens | SQLException erro) {
            Mensagens.aviso(this, erro);
        }
    }

    private void salvar(int id) {
        try {
            Emprestimo item = lerFormulario(id);
            if (!item.isStatus() && emprestimoDAO.getMinhaLista().stream().anyMatch(outro -> outro.getId() != id
                    && outro.getAmigo().getId() == item.getAmigo().getId() && !outro.isStatus())
                    && !confirmar("Este amigo tem outro empréstimo pendente. Deseja continuar?")) {
                return;
            }
            boolean salvo = id == 0 ? emprestimoDAO.InsertEmprestimoBD(item) : emprestimoDAO.UpdateEmprestimoBD(item);
            if (!salvo) {
                throw new Mensagens("O registro selecionado já foi removido.");
            }
            atualizarTabela();
            limparCampos();
        } catch (Mensagens | SQLException erro) {
            Mensagens.aviso(this, erro);
        }
    }

    private Emprestimo lerFormulario(int id) throws Mensagens, SQLException {
        int amigoId = lerId(campoAmigo.getText());
        int ferramentaId = lerId(campoFerramenta.getText());
        java.sql.Date inicio = lerData(campoDataPed.getText());
        String textoFim = campoDataDev.getText().strip();
        java.sql.Date fim = textoFim.isEmpty() || "__-__-____".equals(textoFim) ? null : lerData(textoFim);
        if (fim != null && fim.before(inicio)) {
            throw new Mensagens("A devolução deve ocorrer na mesma data do empréstimo ou depois.");
        }
        Model.Amigo amigo = amigoDAO.getMinhaLista().stream().filter(a -> a.getId() == amigoId)
                .findFirst().orElseThrow(() -> new Mensagens("ID de amigo inexistente."));
        Model.Ferramenta ferramenta = ferramentaDAO.getMinhaLista().stream().filter(f -> f.getId() == ferramentaId)
                .findFirst().orElseThrow(() -> new Mensagens("ID de ferramenta inexistente."));
        return new Emprestimo(id, amigo, ferramenta, inicio, fim);
    }

    private boolean confirmar(String pergunta) {
        return JOptionPane.showConfirmDialog(this, pergunta, "Confirmação", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION;
    }

    private void preencherCampos(Emprestimo item) {
        campoAmigo.setText(Integer.toString(item.getAmigo().getId()));
        campoFerramenta.setText(Integer.toString(item.getFerramenta().getId()));
        campoDataPed.setText(formatarData(item.getDataEmprestimo()));
        campoDataDev.setValue(null);
        if (item.getDataDevolucao() != null) campoDataDev.setText(formatarData(item.getDataDevolucao()));
    }

    private void limparCampos() {
        campoAmigo.setText(""); campoFerramenta.setText("");
        campoDataPed.setValue(null); campoDataDev.setValue(null);
        tableEmprestimos.clearSelection();
    }

    private void atualizarTabela() {
        try {
            java.util.List<Emprestimo> atualizados = emprestimoDAO.getMinhaLista();
            DefaultTableModel modelo = (DefaultTableModel) tableEmprestimos.getModel();
            tableEmprestimos.clearSelection();
            modelo.setRowCount(0);
            registros = atualizados;
            for (Emprestimo item : registros) {
                modelo.addRow(new Object[]{item.getId(), item.getAmigo().getNome(), item.getFerramenta().getNome(),
                        formatarData(item.getDataEmprestimo()), formatarData(item.getDataDevolucao()),
                        item.isStatus() ? "Concluído" : "Pendente"});
            }
        } catch (SQLException erro) {
            Mensagens.aviso(this, erro);
        }
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaEmprestimos().setVisible(true);
            }
        });
    }

    private final javax.swing.JTable tableEmprestimos = InterfaceShareTools.tabela("ID", "Amigo", "Ferramenta", "Empréstimo", "Devolução", "Status");
    private final javax.swing.JTextField campoAmigo = new javax.swing.JTextField(16);
    private final javax.swing.JTextField campoFerramenta = new javax.swing.JTextField(16);
    private final javax.swing.JFormattedTextField campoDataPed = new javax.swing.JFormattedTextField(mascaraCampo());
    private final javax.swing.JFormattedTextField campoDataDev = new javax.swing.JFormattedTextField(mascaraCampo());
}
