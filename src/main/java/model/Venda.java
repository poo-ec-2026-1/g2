package model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Venda {

    private int id;
    private List<ItemVenda> itens;
    private double valorTotal;
    private boolean finalizada;
    private LocalDateTime dataHora;

    private static List<Venda> listaVendas = new ArrayList<>();
    private static int contadorId = 1;

    public Venda() {
        this.id = contadorId++;
        this.itens = new ArrayList<>();
        this.valorTotal = 0.0;
        this.finalizada = false;
        this.dataHora = null;
    }

    public void adicionarItem(Produto produto, int quantidade) {
        if (finalizada) {
            throw new VendaException("Não é possível adicionar itens a uma venda já finalizada.");
        }

        if (quantidade <= 0) {
            throw new VendaException("Quantidade inválida.");
        }

        ItemVenda existente = buscarItemPorProduto(produto);
        int quantidadeJaNoCarrinho = (existente != null) ? existente.getQuantidade() : 0;
        int quantidadeFinal = quantidadeJaNoCarrinho + quantidade;

        if (quantidadeFinal > produto.getQuantidadeEstoque()) {
            int disponivel = Math.max(produto.getQuantidadeEstoque() - quantidadeJaNoCarrinho, 0);
            throw new VendaException("Estoque insuficiente para \"" + produto.getNome()
                    + "\". Disponível: " + disponivel + ".");
        }

        if (existente != null) {
            existente.setQuantidade(quantidadeFinal);
        } else {
            itens.add(new ItemVenda(produto, quantidade));
        }

        calcularTotal();
    }
    public void removerItem(int index) {
        if (finalizada) {
            throw new VendaException("Não é possível remover itens de uma venda já finalizada.");
        }

        if (index < 0 || index >= itens.size()) {
            throw new VendaException("Item não encontrado no carrinho.");
        }

        itens.remove(index);
        calcularTotal();
    }

    public double calcularTotal() {
        double total = 0.0;
        for (ItemVenda item : itens) {
            total += item.calcularSubtotal();
        }
        this.valorTotal = total;
        return this.valorTotal;
    }

    public void finalizarVenda() {
        if (finalizada) {
            throw new VendaException("Esta venda já foi finalizada.");
        }

        if (itens.isEmpty()) {
            throw new VendaException("Não é possível finalizar uma venda sem itens.");
        }

        for (ItemVenda item : itens) {
            Produto p = item.getProduto();
            int novoEstoque = p.getQuantidadeEstoque() - item.getQuantidade();
            p.setQuantidadeEstoque(novoEstoque);
        }

        this.finalizada = true;
        this.dataHora = LocalDateTime.now();
        calcularTotal();

        listaVendas.add(this);
    }

    private ItemVenda buscarItemPorProduto(Produto produto) {
        for (ItemVenda item : itens) {
            if (item.getProduto().getId() == produto.getId()) {
                return item;
            }
        }
        return null;
    }

    public static List<Venda> getListaVendas() {
        return listaVendas;
    }

    public int getId() {
        return id;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String dataFormatada = (dataHora != null)
                ? dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                : "-";

        sb.append(String.format("Venda #%d | Status: %s | Data: %s | Total: R$ %.2f%n",
                id, finalizada ? "Finalizada" : "Em aberto", dataFormatada, valorTotal));

        for (ItemVenda item : itens) {
            sb.append("   - ").append(item).append("\n");
        }

        return sb.toString();
    }
}
