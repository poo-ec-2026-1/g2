package com.castore.produtos.service;

import com.castore.produtos.model.Produto;
import com.castore.produtos.repository.ProdutoRepositorio;

import java.util.List;
import java.util.Optional;

public class ProdutoService {

    private final ProdutoRepositorio repositorio;

    public ProdutoService(ProdutoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Produto cadastrarProduto(String nome, String descricao, double preco,
                                     int quantidadeEstoque, String categoria) {
        return cadastrarProduto(nome, descricao, preco, quantidadeEstoque, categoria, 0, 0.0, null);
    }

    public Produto cadastrarProduto(String nome, String descricao, double preco, int quantidadeEstoque,
                                     String categoria, int qtdPromo, double precoPromo, String chavePix) {
        validarDados(nome, preco, quantidadeEstoque, categoria);
        Produto produto = new Produto(0, nome, descricao, preco, quantidadeEstoque,
                categoria, qtdPromo, precoPromo, chavePix);
        return repositorio.adicionar(produto);
    }

    public void editarProduto(int id, String novoNome, String novaDescricao, double novoPreco,
                               int novaQuantidade, String novaCategoria) {
        validarDados(novoNome, novoPreco, novaQuantidade, novaCategoria);
        Produto produto = buscarOuFalhar(id);
        produto.setNome(novoNome);
        produto.setDescricao(novaDescricao);
        produto.setPreco(novoPreco);
        produto.setQuantidadeEstoque(novaQuantidade);
        produto.setCategoria(novaCategoria);
    }

    public void excluirProduto(int id) {
        buscarOuFalhar(id);
        repositorio.remover(id);
    }

    public Produto buscarPorId(int id) {
        return buscarOuFalhar(id);
    }

    public List<Produto> listarProdutos() {
        return repositorio.listarTodos();
    }

    public List<Produto> listarPorCategoria(String categoria) {
        return repositorio.listarPorCategoria(categoria);
    }

    /** Produtos com estoque abaixo do limite informado — útil para alertar sobre reposição. */
    public List<Produto> listarComEstoqueBaixo(int limite) {
        return repositorio.listarTodos().stream()
                .filter(p -> p.getQuantidadeEstoque() < limite)
                .toList();
    }

    private Produto buscarOuFalhar(int id) {
        Optional<Produto> encontrado = repositorio.buscarPorId(id);
        if (encontrado.isEmpty()) {
            throw new IllegalArgumentException("Produto com ID " + id + " não encontrado.");
        }
        return encontrado.get();
    }

    private void validarDados(String nome, double preco, int quantidadeEstoque, String categoria) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do produto não pode ser vazio.");
        }
        if (preco < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        if (quantidadeEstoque < 0) {
            throw new IllegalArgumentException("A quantidade em estoque não pode ser negativa.");
        }
        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("A categoria não pode ser vazia.");
        }
    }
}
