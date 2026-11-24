package View;

import DAO.AmigoDAO;
import Model.Amigo;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TelaAmigos extends javax.swing.JFrame {

    private final AmigoDAO amigoDAO = new AmigoDAO();
    private java.util.List<Amigo> registros = java.util.Collections.emptyList();

    public TelaAmigos() {
        initComponents();
        tableAmigo.getSelectionModel().addListSelectionListener(evento -> {
            int linha = tableAmigo.getSelectedRow();
            if (!evento.getValueIsAdjusting() && linha >= 0) {
                preencherCampos(registros.get(tableAmigo.convertRowIndexToModel(linha)));
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
        javax.swing.JPanel tela = InterfaceShareTools.janela(this, "Amigos",
                "Cadastre contatos e acompanhe seus empréstimos.",
                InterfaceShareTools.botao("Voltar ao menu", false, this::dispose));
        tela.add(InterfaceShareTools.cadastro(tableAmigo, null,
                InterfaceShareTools.botao("Cadastrar", true, () -> btnCadastraActionPerformed()),
                InterfaceShareTools.botao("Editar", false, () -> btnEditaActionPerformed()),
                InterfaceShareTools.botao("Excluir", false, () -> btnExcluiActionPerformed()),
                InterfaceShareTools.campo("Nome", campoNome),
                InterfaceShareTools.campo("Telefone com DDD (11 dígitos)", campoTelefone)),
                java.awt.BorderLayout.CENTER);
        InterfaceShareTools.posicionar(this);
    }

    private void btnCadastraActionPerformed() {
        salvar(0);
    }

    private void btnEditaActionPerformed() {
        try {
            salvar(Mensagens.idSelecionado(tableAmigo));
        } catch (Mensagens erro) {
            Mensagens.aviso(this, erro);
        }
    }

    private void btnExcluiActionPerformed() {
        try {
            int id = Mensagens.idSelecionado(tableAmigo);
            if (!confirmar("Deseja excluir o registro selecionado?")) return;
            if (!amigoDAO.DeleteAmigoBD(id)) {
                throw new Mensagens("O registro tem histórico de empréstimos ou já foi removido.");
            }
            atualizarTabela();
            limparCampos();
        } catch (Mensagens | SQLException erro) {
            Mensagens.aviso(this, erro);
        }
    }

    private void salvar(int id) {
        try {
            Amigo item = lerFormulario(id);
            boolean salvo = id == 0 ? amigoDAO.InsertAmigoBD(item) : amigoDAO.UpdateAmigoBD(item);
            if (!salvo) {
                throw new Mensagens("O registro selecionado já foi removido.");
            }
            atualizarTabela();
            limparCampos();
        } catch (Mensagens | SQLException erro) {
            Mensagens.aviso(this, erro);
        }
    }

    private Amigo lerFormulario(int id) throws Mensagens {
        String nome = Mensagens.texto(campoNome.getText(), "Nome", 50);
        String telefone = campoTelefone.getText().strip();
        if (!telefone.matches("[1-9][0-9]{10}")) throw new Mensagens("Informe um telefone com DDD e 11 dígitos.");
        return new Amigo(id, nome, Long.parseLong(telefone));
    }

    private boolean confirmar(String pergunta) {
        return JOptionPane.showConfirmDialog(this, pergunta, "Confirmação", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION;
    }

    private void preencherCampos(Amigo item) {
        campoNome.setText(item.getNome());
        campoTelefone.setText(Long.toString(item.getTelefone()));
    }

    private void limparCampos() {
        campoNome.setText(""); campoTelefone.setText("");
        tableAmigo.clearSelection();
    }

    private void atualizarTabela() {
        try {
            java.util.List<Amigo> atualizados = amigoDAO.getMinhaLista();
            DefaultTableModel modelo = (DefaultTableModel) tableAmigo.getModel();
            tableAmigo.clearSelection();
            modelo.setRowCount(0);
            registros = atualizados;
            for (Amigo item : registros) {
                modelo.addRow(new Object[]{item.getId(), item.getNome(), item.getTelefone(), item.getQuantEmprest()});
            }
        } catch (SQLException erro) {
            Mensagens.aviso(this, erro);
        }
    }

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaAmigos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaAmigos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaAmigos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaAmigos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaAmigos().setVisible(true);
            }
        });
    }

    private final javax.swing.JTable tableAmigo = InterfaceShareTools.tabela("ID", "Nome", "Telefone", "Empréstimos");
    private final javax.swing.JTextField campoNome = new javax.swing.JTextField(16);
    private final javax.swing.JTextField campoTelefone = new javax.swing.JTextField(16);
}
