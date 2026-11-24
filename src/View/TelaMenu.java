package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TelaMenu extends JFrame {
    public TelaMenu() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel tela = InterfaceShareTools.janela(this, "ShareTools",
                "Gerencie suas ferramentas e seus empréstimos.",
                InterfaceShareTools.botao("Sair", false, () ->
                        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING))));

        JPanel atalhos = new JPanel(new GridLayout(0, 1, 0, 10));
        atalhos.setOpaque(false);
        atalhos.add(InterfaceShareTools.botao("Amigos", true, () -> abrir(new TelaAmigos())));
        atalhos.add(InterfaceShareTools.botao("Ferramentas", true, () -> abrir(new TelaFerramentas())));
        atalhos.add(InterfaceShareTools.botao("Empréstimos", true, () -> abrir(new TelaEmprestimos())));
        tela.add(atalhos, BorderLayout.CENTER);
        InterfaceShareTools.posicionar(this);
    }

    private void abrir(JFrame tela) {
        tela.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent evento) {
                TelaMenu.this.setVisible(true);
            }
        });
        setVisible(false);
        tela.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new TelaMenu().setVisible(true));
    }
}
