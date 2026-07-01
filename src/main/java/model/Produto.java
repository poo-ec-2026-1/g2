package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "produtos")
public class Produto {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String nome;

    @DatabaseField
    private String descricao;

    @DatabaseField
    private double preco;

    @DatabaseField
    private int quantidadeEstoque;

    @DatabaseField
    private String categoria;

    // Construtor vazio obrigatório para ORMLite
    public Produto() {
    }

    public Produto(
            String nome,
            String descricao,
            double preco,
            int quantidadeEstoque,
            String categoria) {

        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return String.format(
            "ID: %d | Nome: %-20s | Preço: R$ %6.2f | Estoque: %3d | Categoria: %s | Descrição: %s",
            id,
            nome,
            preco,
            quantidadeEstoque,
            categoria,
            descricao
        );
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
