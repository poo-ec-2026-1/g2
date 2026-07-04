package com.castore.controller;

import com.castore.util.Navegador; 
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class IdentificacaoController {

    @FXML
    private TextField campoNome;

    @FXML
    private Label labelErro;

    @FXML
    private void iniciarPedido() {
        String nome = campoNome.getText() == null ? "" : campoNome.getText().trim();

        if (nome.isEmpty()) {
            mostrarErro("Por favor, preencha seu nome.");
            return;
        }

        try {
            LojaController controller = Navegador.irPara("/com/castore/fxml/loja.fxml");
            controller.inicializar(nome);
        } catch (IOException e) {
            mostrarErro("Não foi possível abrir o cardápio.");
            e.printStackTrace();
        }
    }

    private void mostrarErro(String mensagem) {
        labelErro.setText(mensagem);
        labelErro.setVisible(true);
        labelErro.setManaged(true);
    }
}
