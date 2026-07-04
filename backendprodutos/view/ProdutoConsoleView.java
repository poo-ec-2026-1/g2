package com.castore.produtos.view;

import com.castore.produtos.model.Produto;

import java.util.List;

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
