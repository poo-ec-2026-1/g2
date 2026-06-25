import dao.ProdutoDAO;
import java.sql.SQLException;

private void editarProduto() {

    try {

        int id = Integer.parseInt(
                JOptionPane.showInputDialog("ID do produto:")
        );

        ProdutoDAO dao = new ProdutoDAO();

        Produto produto = dao.buscarPorId(id);

        if (produto == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Produto não encontrado."
            );

            return;
        }

        String nome = JOptionPane.showInputDialog(
                "Novo nome:",
                produto.getNome()
        );

        String descricao = JOptionPane.showInputDialog(
                "Nova descrição:",
                produto.getDescricao()
        );

        double preco = Double.parseDouble(
                JOptionPane.showInputDialog(
                        "Novo preço:",
                        produto.getPreco()
                )
        );

        int estoque = Integer.parseInt(
                JOptionPane.showInputDialog(
                        "Novo estoque:",
                        produto.getQuantidadeEstoque()
                )
        );

        String categoria = JOptionPane.showInputDialog(
                "Nova categoria:",
                produto.getCategoria()
        );

        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setQuantidadeEstoque(estoque);
        produto.setCategoria(categoria);

        dao.atualizar(produto);

        listarProdutos();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Erro ao editar produto:\n" + e.getMessage()
        );
    }
}
