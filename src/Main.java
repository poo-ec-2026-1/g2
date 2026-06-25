import database.CriarTabelas;
import view.TelaPrincipal;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        try {

            CriarTabelas.criar();

            System.out.println("Banco iniciado.");

            SwingUtilities.invokeLater(() -> {
                new TelaPrincipal().setVisible(true);
            });

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
