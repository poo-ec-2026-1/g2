package com.castore.produtos.service;

import com.castore.produtos.model.Produto;
import com.castore.produtos.repository.ProdutoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProdutoServiceTest {

    private ProdutoService service;

    @BeforeEach
    void configurar() {
        service = new ProdutoService(new ProdutoRepositorio());
    }

    @Test
    void deveCadastrarProdutoComIdGerado() {
        Produto produto = service.cadastrarProduto("Coxinha", "Salgado", 7.0, 10, "Salgado");
        assertEquals(1, produto.getId());
        assertEquals(1, service.listarProdutos().size());
    }

    @Test
    void naoDeveCadastrarProdutoComNomeVazio() {
        assertThrows(IllegalArgumentException.class,
                () -> service.cadastrarProduto("", "desc", 5.0, 10, "Doce"));
    }

    @Test
    void naoDeveCadastrarProdutoComPrecoNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> service.cadastrarProduto("Suco", "desc", -1.0, 10, "Bebida"));
    }

    @Test
    void naoDeveCadastrarProdutoComEstoqueNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> service.cadastrarProduto("Suco", "desc", 5.0, -3, "Bebida"));
    }

    @Test
    void deveCalcularPrecoComPromocaoEmBlocosCompletos() {
        Produto produto = service.cadastrarProduto(
                "Brigadeiro", "Doce", 3.0, 50, "Doce", 5, 12.0, "doces@castore.com.br");

        assertEquals(12.0, produto.calcularPreco(5), 0.001);   
        assertEquals(15.0, produto.calcularPreco(6), 0.001);   
        assertEquals(24.0, produto.calcularPreco(10), 0.001); 
    }

    @Test
    void deveCalcularPrecoNormalQuandoNaoTemPromocao() {
        Produto produto = service.cadastrarProduto("Água Mineral", "500ml", 3.5, 20, "Bebida");
        assertEquals(7.0, produto.calcularPreco(2), 0.001);
    }

    @Test
    void deveEditarProdutoExistente() {
        Produto produto = service.cadastrarProduto("Água", "500ml", 3.0, 20, "Bebida");
        service.editarProduto(produto.getId(), "Água com Gás", "500ml gaseificada", 3.5, 15, "Bebida");

        Produto atualizado = service.buscarPorId(produto.getId());
        assertEquals("Água com Gás", atualizado.getNome());
        assertEquals(3.5, atualizado.getPreco(), 0.001);
        assertEquals(15, atualizado.getQuantidadeEstoque());
    }

    @Test
    void deveExcluirProduto() {
        Produto produto = service.cadastrarProduto("Pastel", "Frito", 8.0, 10, "Salgado");
        service.excluirProduto(produto.getId());
        assertThrows(IllegalArgumentException.class, () -> service.buscarPorId(produto.getId()));
    }

    @Test
    void deveLancarErroAoBuscarIdInexistente() {
        assertThrows(IllegalArgumentException.class, () -> service.buscarPorId(999));
    }

    @Test
    void deveListarApenasProdutosComEstoqueBaixo() {
        service.cadastrarProduto("Coxinha", "Salgado", 7.0, 5, "Salgado");
        service.cadastrarProduto("Água", "500ml", 3.0, 100, "Bebida");

        assertEquals(1, service.listarComEstoqueBaixo(10).size());
    }
}
