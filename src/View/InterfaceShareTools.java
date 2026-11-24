package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;

final class InterfaceShareTools {
    private static final Color FUNDO = new Color(0xF1F5F4);
    private static final Color TEXTO = new Color(0x172F32);
    private static final Color DESTAQUE = new Color(0x12635A);
    private static final Color BORDA = new Color(0xB8CBC7);
    private static final Font FONTE = new Font(Font.SANS_SERIF, Font.PLAIN, 14);

    private InterfaceShareTools() {
    }

    static JPanel janela(JFrame tela, String titulo, String descricao, JButton voltar) {
        tela.setTitle(titulo.equals("ShareTools") ? titulo : titulo + " – ShareTools");
        JPanel raiz = new JPanel(new BorderLayout(0, 16));
        raiz.setBackground(FUNDO);
        raiz.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JPanel cabecalho = new JPanel(new BorderLayout(16, 4));
        cabecalho.setOpaque(false);
        JLabel nome = rotulo(titulo);
        nome.setFont(FONTE.deriveFont(Font.BOLD, 28f));
        cabecalho.add(nome, BorderLayout.CENTER);
        cabecalho.add(voltar, BorderLayout.EAST);
        cabecalho.add(rotulo(descricao), BorderLayout.SOUTH);
        raiz.add(cabecalho, BorderLayout.NORTH);
        tela.setContentPane(raiz);
        return raiz;
    }

    static void posicionar(JFrame tela) {
        tela.pack();
        Dimension tamanho = tela.getSize();
        Rectangle area = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        Dimension minimo = tela.getMinimumSize();
        tela.setMinimumSize(new Dimension(Math.min(minimo.width, area.width),
                Math.min(minimo.height, area.height)));
        tela.setSize(Math.min(tamanho.width, area.width), Math.min(tamanho.height, area.height));
        tela.setLocationRelativeTo(null);
    }

    static JLabel rotulo(String texto) {
        JLabel rotulo = new JLabel(texto);
        rotulo.setFont(FONTE);
        rotulo.setForeground(TEXTO);
        return rotulo;
    }

    static JButton botao(String texto, boolean principal, Runnable acao) {
        JButton botao = new JButton(texto);
        botao.setFont(FONTE.deriveFont(Font.BOLD));
        botao.setMargin(new Insets(9, 16, 9, 16));
        botao.setBackground(principal ? DESTAQUE : Color.WHITE);
        botao.setForeground(principal ? Color.WHITE : TEXTO);
        botao.addActionListener(evento -> acao.run());
        return botao;
    }

    static JPanel campo(String nome, JTextComponent entrada) {
        JLabel rotulo = rotulo(nome);
        rotulo.setLabelFor(entrada);
        entrada.setFont(FONTE);
        entrada.setForeground(TEXTO);
        entrada.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA), BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        JPanel campo = new JPanel(new BorderLayout(0, 5));
        campo.setOpaque(false);
        campo.add(rotulo, BorderLayout.NORTH);
        campo.add(entrada, BorderLayout.CENTER);
        return campo;
    }

    static JPanel cadastro(JTable tabela, JComponent resumo, JButton cadastrar, JButton editar,
            JButton excluir, JPanel... campos) {
        JPanel entradas = new JPanel(new GridLayout(0, 2, 16, 10));
        entradas.setOpaque(false);
        for (JPanel campo : campos) {
            entradas.add(campo);
        }

        JPanel acoes = new JPanel(new FlowLayout(FlowLayout.LEADING, 8, 0));
        acoes.setOpaque(false);
        acoes.add(cadastrar);
        acoes.add(editar);
        acoes.add(excluir);
        editar.setEnabled(false);
        excluir.setEnabled(false);
        tabela.getSelectionModel().addListSelectionListener(evento -> {
            boolean selecionado = tabela.getSelectedRow() >= 0;
            editar.setEnabled(selecionado);
            excluir.setEnabled(selecionado);
        });

        JPanel formulario = new JPanel(new BorderLayout(0, 12));
        formulario.setBackground(Color.WHITE);
        formulario.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDA),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)));
        formulario.add(entradas, BorderLayout.CENTER);
        formulario.add(acoes, BorderLayout.SOUTH);

        JScrollPane lista = new JScrollPane(tabela);
        lista.setBorder(BorderFactory.createLineBorder(BORDA));
        lista.getViewport().setBackground(Color.WHITE);
        JPanel painel = new JPanel(new BorderLayout(0, 12));
        painel.setOpaque(false);
        painel.add(formulario, BorderLayout.NORTH);
        painel.add(lista, BorderLayout.CENTER);
        if (resumo != null) {
            painel.add(resumo, BorderLayout.SOUTH);
        }
        return painel;
    }

    static JTable tabela(String... colunas) {
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };
        JTable tabela = new JTable(modelo);
        tabela.setFont(FONTE);
        tabela.setForeground(TEXTO);
        tabela.setSelectionBackground(new Color(0xCDEBE5));
        tabela.setSelectionForeground(TEXTO);
        tabela.setRowHeight(tabela.getFontMetrics(FONTE).getHeight() + 12);
        tabela.setShowVerticalLines(false);
        tabela.setGridColor(FUNDO);
        tabela.setFillsViewportHeight(true);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setPreferredScrollableViewportSize(new Dimension(720, 220));
        tabela.getTableHeader().setFont(FONTE.deriveFont(Font.BOLD));
        tabela.getTableHeader().setReorderingAllowed(false);
        for (int indice = 0; indice < colunas.length; indice++) {
            TableColumn coluna = tabela.getColumnModel().getColumn(indice);
            coluna.setMinWidth(tabela.getTableHeader().getFontMetrics(
                    tabela.getTableHeader().getFont()).stringWidth(colunas[indice]) + 16);
            coluna.setPreferredWidth(indice == 0 ? 48 : 130);
        }
        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable origem, Object valor,
                    boolean selecionado, boolean foco, int linha, int coluna) {
                super.getTableCellRendererComponent(origem, valor, selecionado, foco, linha, coluna);
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                setToolTipText(getText().isEmpty() ? null : getText());
                return this;
            }
        });
        return tabela;
    }
}
