import view.TelaPrincipal;

public class Main {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {

            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true);

        });
    }
}
