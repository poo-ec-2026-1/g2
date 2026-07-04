package com.castore.produtos.repository;

import com.castore.produtos.model.Produto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProdutoRepositorio {

    private final List<Produto> produtos = new ArrayList<>();
    private int proximoId = 1;

    public Produto adicionar(Produto produto) {
        produto.setId(proximoId++);
        produtos.add(produto);
        return produto;
    }

    public boolean remover(int id) {
        return produtos.removeIf(p -> p.getId() == id);
    }

    public Optional<Produto> buscarPorId(int id) {
        return produtos.stream().filter(p -> p.getId() == id).findFirst();
    }

    public List<Produto> listarTodos() {
        return Collections.unmodifiableList(produtos);
    }

    public List<Produto> listarPorCategoria(String categoria) {
        List<Produto> resultado = new ArrayList<>();
        for (Produto p : produtos) {
            if (p.getCategoria().equalsIgnoreCase(categoria)) {
                resultado.add(p);
            }
        }
        return resultado;
    }
}
