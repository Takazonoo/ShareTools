package View;

import DAO.AmigoDAO;
import Model.Amigo;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class TelaAmigos extends javax.swing.JFrame {

    private final AmigoDAO amigoDAO = new AmigoDAO();
    private java.util.List<Amigo> registros = java.util.Collections.emptyList();

    public TelaAmigos() {
        initComponents();
        modificarVisual();
        tableAmigo.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ((DefaultTableModel) tableAmigo.getModel()).setRowCount(0);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnEdita = new javax.swing.JButton();
        campoNome = new javax.swing.JTextField();
        btnExclui = new javax.swing.JButton();
        btnCadastra = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableAmigo = new javax.swing.JTable();
        campoTelefone = new javax.swing.JTextField();
        btnExit = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Amigos");
        setUndecorated(true);

        btnEdita.setBackground(new java.awt.Color(255, 255, 255));
        btnEdita.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        btnEdita.setForeground(new java.awt.Color(0,0,0));
        btnEdita.setText("Editar");
        btnEdita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditaActionPerformed(evt);
            }
        });

        campoNome.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        campoNome.setForeground(new java.awt.Color(0, 0, 0));
        campoNome.setCaretColor(new java.awt.Color(38, 117, 191));
        campoNome.setSelectionColor(new java.awt.Color(0, 0, 0));

        btnExclui.setBackground(new java.awt.Color(255, 255, 255));
        btnExclui.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        btnExclui.setForeground(new java.awt.Color(0,0,0));
        btnExclui.setText("Excluir");
        btnExclui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluiActionPerformed(evt);
            }
        });

        btnCadastra.setBackground(new java.awt.Color(255, 255, 255));
        btnCadastra.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        btnCadastra.setForeground(new java.awt.Color(0,0,0));
        btnCadastra.setText("Cadastrar");
        btnCadastra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastraActionPerformed(evt);
            }
        });

        tableAmigo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tableAmigo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Telefone", "Empréstimos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableAmigo.setGridColor(new java.awt.Color(255, 255, 255));
        tableAmigo.setRowHeight(25);
        tableAmigo.setSelectionBackground(new java.awt.Color(200, 200, 200));
        tableAmigo.setSurrendersFocusOnKeystroke(true);
        tableAmigo.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tableAmigo);

        campoTelefone.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        campoTelefone.setForeground(new java.awt.Color(0, 0, 0));
        campoTelefone.setSelectionColor(new java.awt.Color(0, 0, 0));

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
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Nome:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Nome:");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Telefone:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 28)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("AMIGOS");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/telaAmigos_1.png"))); // NOI18N
        jLabel3.setText("Nome:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(790, 790, 790)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(280, 280, 280)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(280, 280, 280)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(campoTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(280, 280, 280)
                .addComponent(btnCadastra, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnEdita, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnExclui, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(280, 280, 280)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 920, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1080, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(110, 110, 110)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(campoTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCadastra, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdita, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExclui, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addComponent(jLabel1))
            .addGroup(layout.createSequentialGroup()
                .addGap(370, 370, 370)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel3)
        );

        jLabel3.getAccessibleContext().setAccessibleName("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCadastraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastraActionPerformed
        salvar(0);
    }//GEN-LAST:event_btnCadastraActionPerformed

    private void btnEditaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditaActionPerformed
        try {
            salvar(Mensagens.idSelecionado(tableAmigo));
        } catch (Mensagens erro) {
            Mensagens.aviso(this, erro);
        }
    }//GEN-LAST:event_btnEditaActionPerformed

    private void btnExcluiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluiActionPerformed
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
    }//GEN-LAST:event_btnExcluiActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        dispose();
    }//GEN-LAST:event_btnExitActionPerformed

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

    private void modificarVisual() {
        Color accentColor = Color.decode("#000000");
        campoNome.setBorder(new LineBorder(accentColor, 1));
        campoTelefone.setBorder(new LineBorder(accentColor, 1));
        tableAmigo.setGridColor(accentColor);
        tableAmigo.setBorder(new LineBorder(accentColor, 1));
        tableAmigo.setForeground(accentColor);
        tableAmigo.setSelectionForeground(accentColor);

        JTableHeader tableHeader = tableAmigo.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableHeader.setBackground(Color.WHITE);
        tableHeader.setForeground(accentColor);
        tableHeader.setBorder(new LineBorder(accentColor, 1));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        tableAmigo.setDefaultRenderer(Object.class, centerRenderer);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastra;
    private javax.swing.JButton btnEdita;
    private javax.swing.JButton btnExclui;
    private javax.swing.JButton btnExit;
    private javax.swing.JTextField campoNome;
    private javax.swing.JTextField campoTelefone;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tableAmigo;
    // End of variables declaration//GEN-END:variables
}
