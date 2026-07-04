package com.castore.produtos.model;

import java.util.Objects;

public class Produto {

    private int id;
    private String nome;
    private String descricao;
    private double preco;
    private int quantidadeEstoque;
    private String categoria; 

    private int qtdPromo;
    private double precoPromo;
    private String chavePix;
    public Produto(String nome, String descricao, double preco, int quantidadeEstoque, String categoria) {
        this(0, nome, descricao, preco, quantidadeEstoque, categoria, 0, 0.0, null);
    }

    public Produto(int id, String nome, String descricao, double preco, int quantidadeEstoque,
                   String categoria, int qtdPromo, double precoPromo, String chavePix) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.categoria = categoria;
        this.qtdPromo = qtdPromo;
        this.precoPromo = precoPromo;
        this.chavePix = chavePix;
    }

    public boolean temPromocao() {
        return qtdPromo > 0;
    }

    public double calcularPreco(int quantidade) {
        if (quantidade <= 0) {
            return 0.0;
        }
        if (temPromocao() && quantidade >= qtdPromo) {
            int blocos = quantidade / qtdPromo;
            int restante = quantidade % qtdPromo;
            return (blocos * precoPromo) + (restante * preco);
        }
        return quantidade * preco;
    }

    @Override
    public String toString() {
        return String.format(
            "ID: %d | Nome: %-20s | Preço: R$ %6.2f | Estoque: %3d | Categoria: %s | Descrição: %s",
            id, nome, preco, quantidadeEstoque, categoria, descricao
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto)) return false;
        return id == ((Produto) o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // ========================
    //      GETTERS/SETTERS
    // ========================
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public int getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(int quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public int getQtdPromo() { return qtdPromo; }
    public void setQtdPromo(int qtdPromo) { this.qtdPromo = qtdPromo; }

    public double getPrecoPromo() { return precoPromo; }
    public void setPrecoPromo(double precoPromo) { this.precoPromo = precoPromo; }

    public String getChavePix() { return chavePix; }
    public void setChavePix(String chavePix) { this.chavePix = chavePix; }
}
