package View;

import java.awt.Component;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class Mensagens extends Exception {
    Mensagens(String mensagem) {
        super(mensagem);
    }

    static void aviso(Component tela, Exception erro) {
        if (erro.getCause() != null) {
            Logger.getLogger(Mensagens.class.getName()).log(Level.WARNING, erro.getMessage(), erro);
        }
        JTextArea mensagem = new JTextArea(erro.getMessage());
        mensagem.setLineWrap(true);
        mensagem.setWrapStyleWord(true);
        mensagem.setEditable(false);
        mensagem.setOpaque(false);
        mensagem.setFont(javax.swing.UIManager.getFont("Label.font"));
        int largura = 32 * mensagem.getFontMetrics(mensagem.getFont()).charWidth('m');
        mensagem.setSize(largura, Short.MAX_VALUE);
        mensagem.setPreferredSize(new Dimension(largura, mensagem.getPreferredSize().height));
        JOptionPane.showMessageDialog(tela, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    static int idSelecionado(JTable tabela) throws Mensagens {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            throw new Mensagens("Selecione uma linha na tabela primeiro.");
        }
        return (Integer) tabela.getValueAt(linha, 0);
    }

    static String texto(String valor, String campo, int maximo) throws Mensagens {
        String texto = valor.strip();
        int tamanho = texto.codePointCount(0, texto.length());
        if (tamanho < 2 || tamanho > maximo) {
            throw new Mensagens(campo + " deve ter de 2 a " + maximo + " caracteres.");
        }
        return texto;
    }
}
