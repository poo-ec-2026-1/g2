import model.Produto;

public class Main {

    public static void main(String[] args) {

        // Cadastro
        Produto.adicionarProduto(
                "Leite",
                "Leite Integral 1L",
                6.50,
                20,
                "Laticínios"
        );

        Produto.adicionarProduto(
                "Refrigerante",
                "Coca-Cola 2L",
                12.99,
                15,
                "Bebidas"
        );

        // Listagem
        Produto.listarProdutos();

        // Edição
        Produto.editarProduto(
                1,
                "Leite Desnatado",
                "Leite Desnatado 1L",
                7.20,
                18,
                "Laticínios"
        );

        // Listagem após edição
        Produto.listarProdutos();

        // Exclusão
        Produto.excluirProduto(2);

        // Listagem final
        Produto.listarProdutos();
    }
}
