package model;

import java.util.ArrayList;
import java.util.List;

public class GrupoPagamentoPix {

    private final String chavePix;
    private final String categoria;
    private final List<ItemVenda> itens = new ArrayList<>();

    public GrupoPagamentoPix(String chavePix, String categoria) {
        this.chavePix = chavePix;
        this.categoria = categoria;
    }

    public void adicionar(ItemVenda item) {
        itens.add(item);
    }

    public String getChavePix() {
        return chavePix;
    }

    public String getCategoria() {
        return categoria;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public double getSubtotal() {
        double total = 0.0;
        for (ItemVenda item : itens) {
            total += item.calcularSubtotal();
        }
        return total;
    }
}
