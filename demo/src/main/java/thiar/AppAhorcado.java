package thiar;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AppAhorcado implements Initializable {

    VBox vBoxContenedorPrincipal;
    GridPane gridPane;
    VBox vBoxContenedorImagenes;
    HBox hBoxContenedorLetras;
    @FXML
    GridPane gridPaneContenedorLetras;
    HBox hBoxContenedorLabel;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        String abecedario = new String("abcdefghijklmnopqrstuvwxyz");
        int indiceFila = 0;
        int indiceColumna = 0;

        for (int i = 0; i < abecedario.length(); i++) {
            String letra = Character.toString(abecedario.charAt(i));
            Button boton = new Button(letra);            
            gridPaneContenedorLetras.add(boton, indiceColumna, indiceFila);
            indiceColumna++;
            if (indiceColumna == 7) {
                indiceColumna = 0;
                indiceFila++;
            }
        }
    }
    
}