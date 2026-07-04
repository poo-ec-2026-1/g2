package com.castore.model;

import java.util.Objects;

package com.castore.model;

import java.util.Objects;

public class Produto {

    private final int id;
    private final String nome;
    private final double precoUnit;
    private final int qtdPromo;
    private final double precoPromo;
    private final String categoria;
    private final String chavePix;

    public Produto(int id, String nome, double precoUnit, int qtdPromo,
                    double precoPromo, String categoria, String chavePix) {
        this.id = id;
        this.nome = nome;
        this.precoUnit = precoUnit;
        this.qtdPromo = qtdPromo;
        this.precoPromo = precoPromo;
        this.categoria = categoria;
        this.chavePix = chavePix;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPrecoUnit() {
        return precoUnit;
    }

    public int getQtdPromo() {
        return qtdPromo;
    }

    public double getPrecoPromo() {
        return precoPromo;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getChavePix() {
        return chavePix;
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
            return (blocos * precoPromo) + (restante * precoUnit);
        }
        return quantidade * precoUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto)) return false;
        Produto produto = (Produto) o;
        return id == produto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Produto{id=" + id + ", nome='" + nome + "'}";
    }
}
public class Produto {

    private final int id;
    private final String nome;
    private final double precoUnit;
    private final int qtdPromo;
    private final double precoPromo;
    private final String categoria;
    private final String chavePix;

    public Produto(int id, String nome, double precoUnit, int qtdPromo,
                    double precoPromo, String categoria, String chavePix) {
        this.id = id;
        this.nome = nome;
        this.precoUnit = precoUnit;
        this.qtdPromo = qtdPromo;
        this.precoPromo = precoPromo;
        this.categoria = categoria;
        this.chavePix = chavePix;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPrecoUnit() {
        return precoUnit;
    }

    public int getQtdPromo() {
        return qtdPromo;
    }

    public double getPrecoPromo() {
        return precoPromo;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getChavePix() {
        return chavePix;
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
            return (blocos * precoPromo) + (restante * precoUnit);
        }
        return quantidade * precoUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto)) return false;
        Produto produto = (Produto) o;
        return id == produto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Produto{id=" + id + ", nome='" + nome + "'}";
    }
}

