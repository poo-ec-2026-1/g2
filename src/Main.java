import database.CriarTabelas;

public class Main {

    public static void main(String[] args) {

        try {

            CriarTabelas.criar();

            System.out.println("Banco iniciado.");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
