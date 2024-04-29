package thiar;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AppAhorcado implements Initializable {

    VBox vBoxContenedorPrincipal;
    GridPane gridPane;
    @FXML
    VBox vBoxContenedorImagenes;
    @FXML
    StackPane stackPaneImagenes;
    @FXML
    HBox hBoxContenedorLetras;
    @FXML
    GridPane gridPaneContenedorLetras;
    @FXML
    HBox hBoxContenedorLabel;

    ArrayList<Button> botonesPresionados = new ArrayList<>();
    ArrayList<Character> letrasPulsadas = new ArrayList<>();
    int fallos;
    Label labelPalabraSecreta = new Label();
    String formatoSecreto = "";
    String secreta = "THIAR";
    final int MAX_FALLOS = 6;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        initializeBotones();
        initializeLabel();
        initializeImagenes();

    }

    public void initializeBotones() {
        String abecedario = new String("abcdefghijklmnñopqrstuvwxyz");
        int indiceFila = 0;
        int indiceColumna = 0;
        gridPaneContenedorLetras.setPadding(new Insets(20));

        for (int i = 0; i < abecedario.length(); i++) {
            String letra = Character.toString(abecedario.charAt(i));
            Button boton = new Button(letra.toUpperCase());
            gridPaneContenedorLetras.add(boton, indiceColumna, indiceFila);
            indiceColumna++;
            boton.setPrefSize(40, 40);
            if (indiceColumna == 7) {
                indiceColumna = 0;
                indiceFila++;
            }
            boton.setOnAction(evento -> {
                estaPresionando(boton);
                System.out.println(evento.getSource());
                comprobarLetra(boton.getText());
            });
        }
    }

    public void initializeLabel() {
        for (int i = 0; i < secreta.length(); i++) {
            formatoSecreto+="_ ";
        }
        actualizarPalabraSecreta();
        hBoxContenedorLabel.getChildren().add(labelPalabraSecreta);
    }

    public void initializeImagenes() {
        
    }

    public void estaPresionando(Button boton) {
        if (!boton.isPressed()) {
            boton.setDisable(true);
            botonesPresionados.add(boton);
            boton.setStyle("-fx-opacity: 0.5;");
            boton.setStyle("-fx-background-color: black;");
        }
    }

    public void comprobarLetra(String c) {

        boolean acertada = false;
        letrasPulsadas.add(c.charAt(0));
        c = c.toUpperCase();
        if (secreta.contains(c)) {
            formatoSecreto = "";
            for (int i = 0; i < secreta.length(); i++) {
                if (letrasPulsadas.contains(secreta.charAt(i))) {
                    formatoSecreto = formatoSecreto + secreta.charAt(i) + " ";
                    acertada = true;
                } else {
                    formatoSecreto = formatoSecreto + "_ ";
                }
            }
            actualizarPalabraSecreta();
        } else {
            String direccionImg = "/img/Hangman-" + fallos + ".png";
            System.out.println(fallos);
            Image imagen = new Image(getClass().getResourceAsStream(direccionImg));
            ImageView imageView = new ImageView(imagen);
            vBoxContenedorImagenes.getChildren().clear();
            vBoxContenedorImagenes.getChildren().add(imageView);
            fallos+=1;
            
            if (fallos > MAX_FALLOS) {
                stackPaneImagenes.getChildren().clear();
            }
        }

        if (acertada) {
            System.out.println("has ganado");
        }
        if (fallos >= stackPaneImagenes.getChildren().size()) {
            System.out.println("has fallado: " + fallos + " por lo tanto perdiste");
        }

        System.out.println("palabra secreta: " + labelPalabraSecreta.getText());
        System.out.println("formato secreto: " + formatoSecreto);
        System.out.println("palabra secreta: " + secreta);
        
    }

    public void actualizarPalabraSecreta() {
        labelPalabraSecreta.setText(formatoSecreto.trim().toUpperCase());
    }
    
}














//Error que no entendia, deje la linea de abajo dentro del for i 
//y me decia que mi version java 21 fallaba al tener en el pom 13 pero cuando cambie 13 a 21
//ejecutaba pero no cargaba la aplicacion.