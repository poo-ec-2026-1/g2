package com.castore.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class Pedido {

    private static final DateTimeFormatter FORMATO_DATA_HORA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final String cliente;
    private final List<ItemPedido> itens;
    private final LocalDateTime dataHora;

    public Pedido(String cliente, List<ItemPedido> itens) {
        this.cliente = cliente;
        this.itens = Collections.unmodifiableList(itens);
        this.dataHora = LocalDateTime.now();
    }

    public String getCliente() {
        return cliente;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getDataHoraFormatada() {
        return dataHora.format(FORMATO_DATA_HORA);
    }

    public double getTotal() {
        double total = 0.0;
        for (ItemPedido item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }
}
