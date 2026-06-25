import dao.ProdutoDAO;
import java.sql.SQLException;

private void listarProdutos() {

    try {

        areaTexto.setText("");

        ProdutoDAO dao = new ProdutoDAO();

        for (Produto p : dao.listar()) {

            areaTexto.append(p.toString());
            areaTexto.append("\n");
        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Erro ao listar produtos:\n" + e.getMessage()
        );
    }
}
