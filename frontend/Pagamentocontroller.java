package com.castore.controller;

import com.castore.model.GrupoPagamento;
import com.castore.model.ItemPedido;
import com.castore.model.Pedido;
import com.castore.util.Navegador;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class PagamentoController {

    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    @FXML
    private Label labelDataHora;

    @FXML
    private VBox containerBlocos;

    @FXML
    private Label labelTotalGeral;

    public void inicializar(Pedido pedido) {
        labelDataHora.setText("Pedido feito em: " + pedido.getDataHoraFormatada());
        labelTotalGeral.setText(String.format(PT_BR, "%.2f", pedido.getTotal()));

        Map<String, GrupoPagamento> grupos = new LinkedHashMap<>();
        for (ItemPedido item : pedido.getItens()) {
            String chave = item.getProduto().getChavePix();
            grupos.computeIfAbsent(chave,
                    k -> new GrupoPagamento(chave, item.getProduto().getCategoria())
            ).adicionar(item);
        }

        containerBlocos.getChildren().clear();
        grupos.values().forEach(grupo -> containerBlocos.getChildren().add(criarBlocoPix(grupo)));
    }

    private VBox criarBlocoPix(GrupoPagamento grupo) {
        VBox bloco = new VBox(8);
        bloco.getStyleClass().add("bloco-pix");
        bloco.setPadding(new Insets(16));

        Label titulo = new Label("Destinatário Categoria: " + grupo.getCategoria() + "s");
        titulo.getStyleClass().add("bloco-titulo");
        bloco.getChildren().add(titulo);

        for (ItemPedido item : grupo.getItens()) {
            HBox linha = new HBox();
            linha.getStyleClass().add("item-linha");

            Label descricao = new Label(item.getQuantidade() + "x " + item.getProduto().getNome());
            Region espaco = new Region();
            HBox.setHgrow(espaco, Priority.ALWAYS);
            Label valor = new Label(String.format(PT_BR, "R$ %.2f", item.getSubtotal()));

            linha.getChildren().addAll(descricao, espaco, valor);
            bloco.getChildren().add(linha);
        }

        Label subtotal = new Label(String.format(PT_BR, "Subtotal: R$ %.2f", grupo.getSubtotal()));
        subtotal.getStyleClass().add("total-bloco");

        HBox linhaPix = new HBox(10);
        linhaPix.getStyleClass().add("pix-copia");

        TextField campoPix = new TextField(grupo.getChavePix());
        campoPix.setEditable(false);
        campoPix.getStyleClass().add("pix-input");
        HBox.setHgrow(campoPix, Priority.ALWAYS);

        Button botaoCopiar = new Button("Copiar Chave");
        botaoCopiar.setOnAction(e -> copiarPix(grupo.getChavePix()));

        linhaPix.getChildren().addAll(campoPix, botaoCopiar);
        bloco.getChildren().addAll(subtotal, linhaPix);

        return bloco;
    }

    private void copiarPix(String chave) {
        ClipboardContent conteudo = new ClipboardContent();
        conteudo.putString(chave);
        Clipboard.getSystemClipboard().setContent(conteudo);

        new Alert(Alert.AlertType.INFORMATION, "Chave Pix copiada para a área de transferência!")
                .showAndWait();
    }

    @FXML
    private void voltar() {
        try {
            Navegador.irPara("/com/castore/fxml/loja.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void finalizar() {
        new Alert(Alert.AlertType.INFORMATION, "Pedido finalizado com sucesso! Bom apetite.").showAndWait();
        try {
            Navegador.irPara("/com/castore/fxml/identificacao.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
