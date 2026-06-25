import dao.ProdutoDAO;
import java.sql.SQLException;

private void cadastrarProduto() {

    try {

        String nome = JOptionPane.showInputDialog("Nome:");

        String descricao = JOptionPane.showInputDialog("Descrição:");

        double preco = Double.parseDouble(
                JOptionPane.showInputDialog("Preço:")
        );

        int estoque = Integer.parseInt(
                JOptionPane.showInputDialog("Quantidade:")
        );

        String categoria = JOptionPane.showInputDialog("Categoria:");

        Produto produto = new Produto(
                nome,
                descricao,
                preco,
                estoque,
                categoria
        );

        ProdutoDAO dao = new ProdutoDAO();
        dao.inserir(produto);

        listarProdutos();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Erro ao cadastrar produto:\n" + e.getMessage()
        );
    }
}
