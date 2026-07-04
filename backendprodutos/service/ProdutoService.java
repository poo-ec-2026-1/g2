package service;

import dao.ProdutoDAO;
import model.Produto;
import model.ProdutoException;

import java.sql.SQLException;
import java.util.List;

public class ProdutoService {

    private final ProdutoDAO produtoDAO;

    public ProdutoService() throws SQLException {
        this.produtoDAO = new ProdutoDAO();
    }

    public Produto cadastrar(String nome, String descricao, double preco, int quantidadeEstoque,
                              String categoria, int qtdPromo, double precoPromo, String chavePix) throws SQLException {
        validar(nome, preco, quantidadeEstoque, categoria);

        Produto produto = new Produto(nome, descricao, preco, quantidadeEstoque, categoria);
        produto.setQtdPromo(qtdPromo);
        produto.setPrecoPromo(precoPromo);
        produto.setChavePix(chavePix);

        produtoDAO.inserir(produto);
        return produto;
    }

    public void editar(int id, String nome, String descricao, double preco, int quantidadeEstoque,
                        String categoria, int qtdPromo, double precoPromo, String chavePix) throws SQLException {
        validar(nome, preco, quantidadeEstoque, categoria);

        Produto produto = buscarOuFalhar(id);
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setQuantidadeEstoque(quantidadeEstoque);
        produto.setCategoria(categoria);
        produto.setQtdPromo(qtdPromo);
        produto.setPrecoPromo(precoPromo);
        produto.setChavePix(chavePix);

        produtoDAO.atualizar(produto);
    }

    public void excluir(int id) throws SQLException {
        buscarOuFalhar(id);
        produtoDAO.excluir(id);
    }

    public Produto buscarPorId(int id) throws SQLException {
        return buscarOuFalhar(id);
    }

    public List<Produto> listar() throws SQLException {
        return produtoDAO.listar();
    }

    public void atualizarEstoque(Produto produto) throws SQLException {
        produtoDAO.atualizar(produto);
    }

    private Produto buscarOuFalhar(int id) throws SQLException {
        Produto produto = produtoDAO.buscarPorId(id);
        if (produto == null) {
            throw new ProdutoException("Produto com ID " + id + " não encontrado.");
        }
        return produto;
    }

    private void validar(String nome, double preco, int quantidadeEstoque, String categoria) {
        if (nome == null || nome.isBlank()) {
            throw new ProdutoException("O nome do produto não pode ser vazio.");
        }
        if (preco < 0) {
            throw new ProdutoException("O preço não pode ser negativo.");
        }
        if (quantidadeEstoque < 0) {
            throw new ProdutoException("A quantidade em estoque não pode ser negativa.");
        }
        if (categoria == null || categoria.isBlank()) {
            throw new ProdutoException("A categoria não pode ser vazia.");
        }
    }
}
