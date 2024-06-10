package View;

import DAO.FerramentaDAO;
import Model.Ferramenta;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class TelaFerramentas extends javax.swing.JFrame {
    
    private FerramentaDAO ferramentaDAO; // cria o vinculo com o Ferramenta.java
    
    public TelaFerramentas() {
        initComponents();
        modificarVisual();
        this.ferramentaDAO = new FerramentaDAO(); // carrega DAO de Ferramenta.java
        carregaTabela();
        custoFerramentas();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        campoNome = new javax.swing.JTextField();
        campoMarca = new javax.swing.JTextField();
        campoCusto = new javax.swing.JTextField();
        btnCadastrar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableFerramenta = new javax.swing.JTable();
        btnExit = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        campoCustoTotal = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ferramentas");
        setUndecorated(true);
        setResizable(false);

        campoNome.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        campoNome.setForeground(new java.awt.Color(0, 0, 0));
        campoNome.setSelectionColor(new java.awt.Color(0, 0, 0));

        campoMarca.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        campoMarca.setForeground(new java.awt.Color(0, 0, 0));
        campoMarca.setSelectionColor(new java.awt.Color(0, 0, 0));

        campoCusto.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        campoCusto.setForeground(new java.awt.Color(0, 0, 0));
        campoCusto.setSelectionColor(new java.awt.Color(0, 0, 0));

        btnCadastrar.setBackground(new java.awt.Color(255, 255, 255));
        btnCadastrar.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        btnCadastrar.setForeground(new java.awt.Color(0,0,0));
        btnCadastrar.setText("Cadastrar");
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(255, 255, 255));
        btnEditar.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(0,0,0));
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setBackground(new java.awt.Color(255, 255, 255));
        btnExcluir.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        btnExcluir.setForeground(new java.awt.Color(0,0,0));
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        tableFerramenta.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tableFerramenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nome:", "Marca:", "Custo:"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableFerramenta.setRowHeight(25);
        tableFerramenta.setSelectionBackground(new java.awt.Color(200, 200, 200));
        tableFerramenta.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tableFerramenta);

        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/btnexit.png"))); // NOI18N
        btnExit.setBorderPainted(false);
        btnExit.setContentAreaFilled(false);
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("R$:");

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("GASTO TOTAL COM");
        jLabel2.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("FERRAMENTAS:");

        campoCustoTotal.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        campoCustoTotal.setForeground(new java.awt.Color(0, 0, 0));
        campoCustoTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoCustoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(campoCustoTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Nome:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Marca:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Custo:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 28)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("FERRAMENTAS");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/telaFerramentas.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(640, 640, 640)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(campoCusto, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(60, 60, 60)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(440, 440, 440)
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addComponent(campoMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel5)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(90, 90, 90)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(campoCusto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(290, 290, 290)
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addComponent(campoMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel5)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
          try {
            // recebendo e validando dados da interface grafica.
            String nome = "";
            String marca = "";
            double custo = 0;

            if (this.campoNome.getText().length() < 2) {
                throw new Mensagens("Nome deve conter ao menos 2 caracteres.");
            } else {
                nome = this.campoNome.getText();
            }
            
            if (this.campoMarca.getText().length() < 2) {
                throw new Mensagens("Marca deve conter ao menos 2 caracteres.");
            } else {
                marca = this.campoMarca.getText();
            }

            if (this.campoCusto.getText().length() < 2) {
                throw new Mensagens("Custo deve ser número e conter ao menos 2 dígitos.");
            } else {
                custo = Double.parseDouble(this.campoCusto.getText());
            }
            
            Ferramenta objeto = new Ferramenta(nome, marca, custo);
            
            // envia os dados para o Controlador cadastrar
            if (this.ferramentaDAO.FerramentaExistenteDB(nome)) {
                String pergunta = "Foi encontrado outra ferramenta com este nome.\nDeseja adicionar mais uma no estoque?";
                int resposta = JOptionPane.showConfirmDialog(rootPane, pergunta, "Confirmação", JOptionPane.YES_NO_OPTION);
                
                if(resposta == JOptionPane.YES_OPTION) {
                    
                    if(this.ferramentaDAO.InsertFerramentaBD(objeto)) {
                        JOptionPane.showMessageDialog(rootPane, "Ferramenta cadastrada com sucesso!");

                        // limpa campos da interface
                        this.campoNome.setText("");
                        this.campoMarca.setText("");
                        this.campoCusto.setText("");
                    }
                }
            } else {
                if(this.ferramentaDAO.InsertFerramentaBD(objeto)) {
                    JOptionPane.showMessageDialog(rootPane, "Ferramenta cadastrada com sucesso!");

                    this.campoNome.setText("");
                    this.campoMarca.setText("");
                    this.campoCusto.setText("");
                }
            }

        } catch (Mensagens erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (NumberFormatException erro) {
            JOptionPane.showMessageDialog(null, "Custo inválido, informe um número.", "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException erro) {
            Logger.getLogger(TelaFerramentas.class.getName()).log(Level.SEVERE, null, erro);
        } finally {
            carregaTabela(); // atualiza a tabela.
            custoFerramentas(); // atualiza o valor das ferramentas.
        }
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
          
        try {
            int id = 0;
            
            if(this.tableFerramenta.getSelectedRow() == -1) {
                throw new Mensagens("Primeiro, selecione uma ferramenta para remover");
            } else {
                id = Integer.parseInt(this.tableFerramenta.getValueAt(this.tableFerramenta.getSelectedRow(), 0).toString());
            }
            
            String pergunta = "Tem certeza que deseja remover esta ferramenta?";
            int resposta = JOptionPane.showConfirmDialog(rootPane, pergunta, "Confirmação", JOptionPane.YES_NO_OPTION);
            
            if(resposta == JOptionPane.YES_OPTION && this.ferramentaDAO.DeleteFerramentaBD(id)) {
                JOptionPane.showMessageDialog(rootPane, "Ferramenta removida com sucesso!");
            }
        } catch (Mensagens erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        } finally {
            carregaTabela();
            custoFerramentas();
        }
        
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        
        try {
            // recebendo e validando dados da interface grafica.
        
            int id = 0;
            String nome = "";
            String marca = "";
            double custoAquisicao = 0;
            
            if (this.campoNome.getText().length() < 2) {
                throw new Mensagens("Nome deve conter ao menos 2 caracteres.");
            } else {
                nome = this.campoNome.getText();
            }

            if (this.campoMarca.getText().length() < 2) {
                throw new Mensagens("Marca deve conter ao menos 2 caracteres.");
            } else {
                marca = (this.campoMarca.getText());
            }
            
            if (this.campoCusto.getText().length() < 2) {
                throw new Mensagens("Custo deve ser número e conter ao menos 2 dígitos.");
            } else {
                custoAquisicao = Double.parseDouble(this.campoCusto.getText());
            }
            
            ArrayList<Ferramenta> minhalista = ferramentaDAO.getMinhaLista();
            if (this.tableFerramenta.getSelectedRow() == -1) {
                throw new Mensagens("Primeiro, selecione uma ferramenta para alterar");
            } else {
                id = Integer.parseInt(this.tableFerramenta.getValueAt(this.tableFerramenta.getSelectedRow(), 0).toString());
            }
            
            for (Ferramenta f : minhalista) { // checa se essas informações já estão cadastradas
                if(f.getNome().equalsIgnoreCase(nome) && f.getMarca().equalsIgnoreCase(marca) && f.getId() != id){
                    int resposta = JOptionPane.showConfirmDialog(rootPane, "Já existe uma ferramenta com o mesmo nome e marca.\nDeseja mesmo continuar?", "Confirmação", JOptionPane.YES_NO_OPTION);
                    if(resposta == JOptionPane.NO_OPTION) {
                        throw new Mensagens("Edição cancelada!");
                    }
                } 
                else if(f.getNome().equalsIgnoreCase(nome) && f.getId() != id){
                    int resposta = JOptionPane.showConfirmDialog(rootPane, "Foi encontrada outra ferramenta com este nome.\nDeseja mesmo continuar?", "Confirmação", JOptionPane.YES_NO_OPTION);
                    if(resposta == JOptionPane.NO_OPTION) {
                        throw new Mensagens("Edição cancelada!");
                    }
                }
            } 

            // envia os dados para a Ferramenta processar
            if (this.ferramentaDAO.UpdateFerramentaBD(new Ferramenta(id, nome, marca, custoAquisicao))) {

                // limpa os campos
                this.campoNome.setText("");
                this.campoMarca.setText("");
                this.campoCusto.setText("");
                JOptionPane.showMessageDialog(rootPane, "Ferramenta alterada com sucesso!");

            }
            System.out.println(this.ferramentaDAO.getMinhaLista().toString());
        } catch (Mensagens erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage());
        } catch (NumberFormatException erro) {
            JOptionPane.showMessageDialog(null, "Custo inválido, informe um número.");
        } finally {
            carregaTabela(); // atualiza a tabela.
            custoFerramentas();
        }
        
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        TelaFerramentas.this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void carregaTabela() { // listando os objetos ferramenta na tabela

        DefaultTableModel modelo = (DefaultTableModel) this.tableFerramenta.getModel();
        modelo.setNumRows(0);

        ArrayList<Ferramenta> minhalista = ferramentaDAO.getMinhaLista();

        for (Ferramenta a : minhalista) {
            modelo.addRow(new Object[]{
                a.getId(),
                a.getNome(),
                a.getMarca(),
                a.getCustoAquisicao()
            });
        }
    }
    
    private void custoFerramentas(){
        String contadorCusto = String.valueOf(ferramentaDAO.getContadorCusto());
        this.campoCustoTotal.setText(contadorCusto);
    }
    
    private void modificarVisual() {
        Color accentColor = Color.decode("#000000");
        campoNome.setBorder(new LineBorder(accentColor, 1));
        campoMarca.setBorder(new LineBorder(accentColor, 1));
        campoCusto.setBorder(new LineBorder(accentColor, 1));
        tableFerramenta.setGridColor(accentColor);
        tableFerramenta.setBorder(new LineBorder(accentColor, 1));
        tableFerramenta.setForeground(accentColor);
        tableFerramenta.setSelectionForeground(accentColor);
        
        JTableHeader tableHeader = tableFerramenta.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableHeader.setBackground(Color.WHITE);
        tableHeader.setForeground(accentColor);
        tableHeader.setBorder(new LineBorder(accentColor, 1));
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        tableFerramenta.setDefaultRenderer(Object.class, centerRenderer);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnExit;
    private javax.swing.JTextField campoCusto;
    private javax.swing.JLabel campoCustoTotal;
    private javax.swing.JTextField campoMarca;
    private javax.swing.JTextField campoNome;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableFerramenta;
    // End of variables declaration//GEN-END:variables
}
