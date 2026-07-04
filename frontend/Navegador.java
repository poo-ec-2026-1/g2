package com.castore.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class Navegador {

    private static Stage stagePrincipal;

    private Navegador() {
    }

    public static void configurarStage(Stage stage) {
        stagePrincipal = stage;
    }

    public static <T> T irPara(String caminhoFxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(Navegador.class.getResource(caminhoFxml));
        Parent raiz = loader.load();

        if (stagePrincipal.getScene() == null) {
            Scene cena = new Scene(raiz, 850, 650);
            cena.getStylesheets().add(
                    Navegador.class.getResource("/com/castore/css/style.css").toExternalForm());
            stagePrincipal.setScene(cena);
        } else {
            stagePrincipal.getScene().setRoot(raiz);
        }

        return loader.getController();
    }
}
