package com.castore.controller;

import com.castore.model.Carrinho;
import com.castore.model.Pedido;
import com.castore.model.Produto;
import com.castore.service.ProdutoService;
import com.castore.service.ProdutoServiceEmMemoria;
import com.castore.util.Navegador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LojaController {

    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    @FXML
    private Label labelCliente;

    @FXML
    private ListView<Produto> listaProdutos;

    private final ProdutoService produtoService = new ProdutoServiceEmMemoria();
    private final Carrinho carrinho = new Carrinho();
    private String nomeCliente;

    /** Chamado pelo IdentificacaoController ao navegar para esta tela. */
    public void inicializar(String nomeCliente) {
        this.nomeCliente = nomeCliente;
        labelCliente.setText(nomeCliente);
        carregarProdutos();
    }

    private void carregarProdutos() {
        List<Produto> produtos = produtoService.listarProdutos();
        ObservableList<Produto> itens = FXCollections.observableArrayList(produtos);
        listaProdutos.setItems(itens);
        listaProdutos.setCellFactory(lv -> new ProdutoCell());
    }

    @FXML
    private void voltar() {
        try {
            Navegador.irPara("/com/castore/fxml/identificacao.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void fecharPedido() {
        if (carrinho.estaVazio()) {
            new Alert(Alert.AlertType.WARNING, "Adicione pelo menos um produto ao carrinho.").showAndWait();
            return;
        }

        Pedido pedido = new Pedido(nomeCliente, carrinho.gerarItensPedido());

        try {
            PagamentoController controller = Navegador.irPara("/com/castore/fxml/pagamento.fxml");
            controller.inicializar(pedido);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ProdutoCell extends ListCell<Produto> {
        private final HBox raiz = new HBox();
        private final VBox info = new VBox(4);
        private final Label nome = new Label();
        private final Label preco = new Label();
        private final HBox seletor = new HBox(10);
        private final Button botaoMenos = new Button("-");
        private final Label quantidade = new Label("0");
        private final Button botaoMais = new Button("+");

        ProdutoCell() {
            raiz.getStyleClass().add("produto-card");
            info.getStyleClass().add("produto-info");
            nome.getStyleClass().add("produto-nome");
            preco.getStyleClass().add("produto-preco");
            seletor.getStyleClass().add("seletor-qtd");
            seletor.setAlignment(Pos.CENTER);
            quantidade.getStyleClass().add("qtd-valor");
            quantidade.setMinWidth(24);
            quantidade.setAlignment(Pos.CENTER);

            info.getChildren().addAll(nome, preco);

            Region espaco = new Region();
            HBox.setHgrow(espaco, Priority.ALWAYS);

            seletor.getChildren().addAll(botaoMenos, quantidade, botaoMais);

            raiz.getChildren().addAll(info, espaco, seletor);
            raiz.setPadding(new Insets(10));
            raiz.setSpacing(10);
            raiz.setAlignment(Pos.CENTER_LEFT);

            botaoMenos.setOnAction(e -> alterar(getItem(), -1));
            botaoMais.setOnAction(e -> alterar(getItem(), 1));
        }

        private void alterar(Produto produto, int delta) {
            if (produto == null) {
                return;
            }
            carrinho.alterarQuantidade(produto, delta);
            quantidade.setText(String.valueOf(carrinho.obterQuantidade(produto)));
        }

        @Override
        protected void updateItem(Produto produto, boolean vazio) {
            super.updateItem(produto, vazio);

            if (vazio || produto == null) {
                setGraphic(null);
                return;
            }

            nome.setText(produto.getNome());
            preco.setText(formatarPreco(produto));
            quantidade.setText(String.valueOf(carrinho.obterQuantidade(produto)));
            setGraphic(raiz);
        }

        private String formatarPreco(Produto produto) {
            String unitario = String.format(PT_BR, "R$ %.2f", produto.getPrecoUnit());
            if (produto.temPromocao()) {
                String promo = String.format(PT_BR,
                        "Leve %d por R$ %.2f", produto.getQtdPromo(), produto.getPrecoPromo());
                return unitario + " | " + promo;
            }
            return unitario + " | Preço normal";
        }
    }
}
