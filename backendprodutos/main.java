package com.castore.produtos;

import com.castore.produtos.model.Produto;
import com.castore.produtos.repository.ProdutoRepositorio;
import com.castore.produtos.service.ProdutoService;
import com.castore.produtos.view.ProdutoConsoleView;

public class Main {

    public static void main(String[] args) {
        ProdutoRepositorio repositorio = new ProdutoRepositorio();
        ProdutoService service = new ProdutoService(repositorio);
        ProdutoConsoleView view = new ProdutoConsoleView();

        Produto coxinha = service.cadastrarProduto(
                "Coxinha", "Salgado frito de frango", 7.00, 40, "Salgado", 3, 18.00, "salgados@castore.com.br");
        view.exibirSucesso("Produto \"" + coxinha.getNome() + "\" adicionado com sucesso! (ID: " + coxinha.getId() + ")");

        Produto agua = service.cadastrarProduto("Água Mineral", "Garrafa 500ml", 3.50, 60, "Bebida");
        view.exibirSucesso("Produto \"" + agua.getNome() + "\" adicionado com sucesso! (ID: " + agua.getId() + ")");

        view.exibirListaProdutos(service.listarProdutos());

        service.editarProduto(coxinha.getId(), "Coxinha Grande",
                "Salgado frito de frango, tamanho grande", 8.00, 35, "Salgado");
        view.exibirAtualizacao("Produto ID " + coxinha.getId() + " atualizado com sucesso!");

        try {
            service.cadastrarProduto("", "Sem nome", 5.0, 10, "Doce");
        } catch (IllegalArgumentException e) {
            view.exibirErro(e.getMessage());
        }

        try {
            service.excluirProduto(agua.getId());
            view.exibirRemocao("Produto \"" + agua.getNome() + "\" excluído com sucesso!");
        } catch (IllegalArgumentException e) {
            view.exibirErro(e.getMessage());
        }

        view.exibirListaProdutos(service.listarProdutos());
    }
}
