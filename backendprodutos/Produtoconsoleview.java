package com.castore.produtos.view;

import com.castore.produtos.model.Produto;

import java.util.List;

/**
 * Responsável apenas por exibir informações no console. Antes, os
 * `System.out.println` estavam espalhados dentro dos métodos estáticos de
 * Produto, misturando dado + regra + exibição na mesma classe. Isolando a
 * exibição aqui, essa view poderia ser substituída por uma tela JavaFX sem
 * tocar em nenhuma linha do ProdutoService.
 */
public class ProdutoConsoleView {

    public void exibirSucesso(String mensagem) {
        System.out.println("✅ " + mensagem);
    }

    public void exibirErro(String mensagem) {
        System.out.println("❌ " + mensagem);
    }

    public void exibirRemocao(String mensagem) {
        System.out.println("🗑️ " + mensagem);
    }

    public void exibirAtualizacao(String mensagem) {
        System.out.println("✏️ " + mensagem);
    }

    public void exibirListaProdutos(List<Produto> produtos) {
        if (produtos.isEmpty()) {
            System.out.println("📭 Nenhum produto cadastrado.");
            return;
        }

        System.out.println("\n========== 📦 PRODUTOS CADASTRADOS ==========");
        for (Produto produto : produtos) {
            System.out.println(produto);
        }
        System.out.println("=============================================\n");
    }
}
