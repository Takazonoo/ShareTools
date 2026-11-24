package View;

import DAO.*;
import Model.Emprestimo;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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
        modificarVisual();
        tableEmprestimos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ((DefaultTableModel) tableEmprestimos.getModel()).setRowCount(0);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        campoDataPed = new javax.swing.JFormattedTextField(mascaraCampo());
        campoFerramenta = new javax.swing.JTextField();
        btnCadastrar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableEmprestimos = new javax.swing.JTable();
        campoDataDev = new javax.swing.JFormattedTextField(mascaraCampo());
        campoAmigo = new javax.swing.JTextField();
        btnExit = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Empréstimos");
        setUndecorated(true);
        setResizable(false);

        campoDataPed.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        campoDataPed.setForeground(new java.awt.Color(0, 0, 0));
        campoDataPed.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        campoDataPed.setSelectionColor(new java.awt.Color(0, 0, 0));

        campoFerramenta.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        campoFerramenta.setForeground(new java.awt.Color(0, 0, 0));
        campoFerramenta.setSelectionColor(new java.awt.Color(0, 0, 0));

        btnCadastrar.setBackground(new java.awt.Color(255, 255, 255));
        btnCadastrar.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        btnCadastrar.setForeground(new java.awt.Color(0, 0, 0));
        btnCadastrar.setText(" Cadastrar");
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(255, 255, 255));
        btnEditar.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(0, 0, 0));
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setBackground(new java.awt.Color(255, 255, 255));
        btnExcluir.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        btnExcluir.setForeground(new java.awt.Color(0, 0, 0));
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        tableEmprestimos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tableEmprestimos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID:", "Amigo:", "Ferramenta:", "Data pedido:", "Data devolução:", "Status:"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableEmprestimos.setRowHeight(25);
        tableEmprestimos.setSelectionBackground(new java.awt.Color(200, 200, 200));
        tableEmprestimos.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tableEmprestimos);

        campoDataDev.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        campoDataDev.setForeground(new java.awt.Color(0, 0, 0));
        campoDataDev.setToolTipText("");
        campoDataDev.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        campoDataDev.setSelectionColor(new java.awt.Color(0, 0, 0));

        campoAmigo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        campoAmigo.setForeground(new java.awt.Color(0, 0, 0));
        campoAmigo.setSelectionColor(new java.awt.Color(0, 0, 0));

        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/btnexit.png"))); // NOI18N
        btnExit.setBorderPainted(false);
        btnExit.setContentAreaFilled(false);
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("ID amigo:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Data pedido:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("ID ferramenta:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Data devolução:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 28)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("EMPRÉSTIMOS");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/telaEmprestimos_1.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(campoAmigo, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(campoDataPed, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(campoDataDev, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createSequentialGroup()
                .addGap(630, 630, 630)
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 890, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(1010, 1010, 1010)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(540, 540, 540)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(270, 270, 270)
                .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(450, 450, 450)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(710, 710, 710)
                .addComponent(campoFerramenta, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 1080, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(campoAmigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(campoDataPed, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoDataDev, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(40, 40, 40)
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel3))
            .addGroup(layout.createSequentialGroup()
                .addGap(270, 270, 270)
                .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(270, 270, 270)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(campoFerramenta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel5)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        salvar(0);
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        try {
            salvar(Mensagens.idSelecionado(tableEmprestimos));
        } catch (Mensagens erro) {
            Mensagens.aviso(this, erro);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
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
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        dispose();
    }//GEN-LAST:event_btnExitActionPerformed

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

    private void modificarVisual() {
        Color accentColor = Color.decode("#000000");
        campoAmigo.setBorder(new LineBorder(accentColor, 1));
        campoFerramenta.setBorder(new LineBorder(accentColor, 1));
        campoDataPed.setBorder(new LineBorder(accentColor, 1));
        campoDataDev.setBorder(new LineBorder(accentColor, 1));
        tableEmprestimos.setGridColor(accentColor);
        tableEmprestimos.setBorder(new LineBorder(accentColor, 1));
        tableEmprestimos.setForeground(accentColor);
        tableEmprestimos.setSelectionForeground(accentColor);
        
        JTableHeader tableHeader = tableEmprestimos.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableHeader.setBackground(Color.WHITE);
        tableHeader.setForeground(accentColor);
        tableHeader.setBorder(new LineBorder(accentColor, 1));
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        tableEmprestimos.setDefaultRenderer(Object.class, centerRenderer);
    }
    
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaEmprestimos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnExit;
    private javax.swing.JTextField campoAmigo;
    private javax.swing.JFormattedTextField campoDataDev;
    private javax.swing.JFormattedTextField campoDataPed;
    private javax.swing.JTextField campoFerramenta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableEmprestimos;
    // End of variables declaration//GEN-END:variables
}
