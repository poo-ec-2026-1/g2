package com.castore;

import com.castore.util.Navegador;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stagePrincipal) throws Exception {
        Navegador.configurarStage(stagePrincipal);
        Navegador.irPara("/com/castore/fxml/identificacao.fxml");

        stagePrincipal.setTitle("CA Store — Sistema de Vendas");
        stagePrincipal.setMinWidth(700);
        stagePrincipal.setMinHeight(600);
        stagePrincipal.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

