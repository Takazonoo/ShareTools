package View;

import DAO.FerramentaDAO;
import Model.Ferramenta;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TelaFerramentas extends javax.swing.JFrame {

    private final FerramentaDAO ferramentaDAO = new FerramentaDAO();
    private java.util.List<Ferramenta> registros = java.util.Collections.emptyList();

    private final java.text.NumberFormat dinheiro = java.text.NumberFormat.getCurrencyInstance(
            java.util.Locale.forLanguageTag("pt-BR"));

    public TelaFerramentas() {
        initComponents();
        tableFerramenta.getSelectionModel().addListSelectionListener(evento -> {
            int linha = tableFerramenta.getSelectedRow();
            if (!evento.getValueIsAdjusting() && linha >= 0) {
                preencherCampos(registros.get(tableFerramenta.convertRowIndexToModel(linha)));
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
        javax.swing.JPanel tela = InterfaceShareTools.janela(this, "Ferramentas",
                "Organize seu catálogo e consulte o custo de aquisição.",
                InterfaceShareTools.botao("Voltar ao menu", false, this::dispose));
        javax.swing.JPanel resumo = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.TRAILING, 8, 0));
        resumo.setOpaque(false);
        resumo.add(InterfaceShareTools.rotulo("Custo total do catálogo"));
        resumo.add(campoCustoTotal);
        tela.add(InterfaceShareTools.cadastro(tableFerramenta, resumo,
                InterfaceShareTools.botao("Cadastrar", true, () -> btnCadastrarActionPerformed()),
                InterfaceShareTools.botao("Editar", false, () -> btnEditarActionPerformed()),
                InterfaceShareTools.botao("Excluir", false, () -> btnExcluirActionPerformed()),
                InterfaceShareTools.campo("Nome", campoNome),
                InterfaceShareTools.campo("Marca", campoMarca),
                InterfaceShareTools.campo("Custo de aquisição (R$)", campoCusto)),
                java.awt.BorderLayout.CENTER);
        InterfaceShareTools.posicionar(this);
    }

    private void btnCadastrarActionPerformed() {
        salvar(0);
    }

    private void btnEditarActionPerformed() {
        try {
            salvar(Mensagens.idSelecionado(tableFerramenta));
        } catch (Mensagens erro) {
            Mensagens.aviso(this, erro);
        }
    }

    private void btnExcluirActionPerformed() {
        try {
            int id = Mensagens.idSelecionado(tableFerramenta);
            if (!confirmar("Deseja excluir o registro selecionado?")) return;
            if (!ferramentaDAO.DeleteFerramentaBD(id)) {
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
            Ferramenta item = lerFormulario(id);
            if (ferramentaDAO.getMinhaLista().stream().anyMatch(outra -> outra.getId() != id
                    && outra.getNome().equalsIgnoreCase(item.getNome()))
                    && !confirmar("Já existe uma ferramenta com esse nome. Deseja continuar?")) {
                return;
            }
            boolean salvo = id == 0 ? ferramentaDAO.InsertFerramentaBD(item) : ferramentaDAO.UpdateFerramentaBD(item);
            if (!salvo) {
                throw new Mensagens("O registro selecionado já foi removido.");
            }
            atualizarTabela();
            limparCampos();
        } catch (Mensagens | SQLException erro) {
            Mensagens.aviso(this, erro);
        }
    }

    private Ferramenta lerFormulario(int id) throws Mensagens {
        String nome = Mensagens.texto(campoNome.getText(), "Nome", 45);
        String marca = Mensagens.texto(campoMarca.getText(), "Marca", 45);
        String custo = campoCusto.getText().strip().replace(',', '.');
        if (!custo.matches("[0-9]{1,3}(\\.[0-9]{1,2})?")) {
            throw new Mensagens("Informe um custo de 0 a 999,99 com até duas casas decimais.");
        }
        return new Ferramenta(id, nome, marca, Double.parseDouble(custo));
    }

    private boolean confirmar(String pergunta) {
        return JOptionPane.showConfirmDialog(this, pergunta, "Confirmação", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION;
    }

    private void preencherCampos(Ferramenta item) {
        campoNome.setText(item.getNome());
        campoMarca.setText(item.getMarca());
        campoCusto.setText(Double.toString(item.getCustoAquisicao()));
    }

    private void limparCampos() {
        campoNome.setText(""); campoMarca.setText(""); campoCusto.setText("");
        tableFerramenta.clearSelection();
    }

    private void atualizarTabela() {
        try {
            java.util.List<Ferramenta> atualizados = ferramentaDAO.getMinhaLista();
            DefaultTableModel modelo = (DefaultTableModel) tableFerramenta.getModel();
            tableFerramenta.clearSelection();
            modelo.setRowCount(0);
            registros = atualizados;
            for (Ferramenta item : registros) {
                modelo.addRow(new Object[]{item.getId(), item.getNome(), item.getMarca(), dinheiro.format(item.getCustoAquisicao())});
            }
            java.math.BigDecimal total = registros.stream()
                    .map(item -> java.math.BigDecimal.valueOf(item.getCustoAquisicao()))
                    .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
            campoCustoTotal.setText(dinheiro.format(total));
            campoCustoTotal.setToolTipText(dinheiro.format(total));
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaFerramentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaFerramentas().setVisible(true);
            }
        });
    }

    private final javax.swing.JTable tableFerramenta = InterfaceShareTools.tabela("ID", "Nome", "Marca", "Custo de aquisição");
    private final javax.swing.JTextField campoNome = new javax.swing.JTextField(16);
    private final javax.swing.JTextField campoMarca = new javax.swing.JTextField(16);
    private final javax.swing.JTextField campoCusto = new javax.swing.JTextField(16);
    private final JLabel campoCustoTotal = InterfaceShareTools.rotulo("—");
}
