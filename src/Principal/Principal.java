package Principal;

import View.TelaMenu;
import java.awt.EventQueue;

public class Principal {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new TelaMenu().setVisible(true));
    }
}
