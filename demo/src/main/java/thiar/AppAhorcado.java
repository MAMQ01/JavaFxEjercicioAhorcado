package thiar;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AppAhorcado implements Initializable {

    @FXML
    private VBox vBoxContenedorImagenes;
    @FXML
    private HBox hBoxContenedorLetras;
    @FXML
    private GridPane gridPaneContenedorLetras;
    @FXML
    private HBox hBoxContenedorLabel;

    private ArrayList<Button> botonesPresionados = new ArrayList<>();
    private ArrayList<Character> letrasPulsadas = new ArrayList<>();
    private int fallos;
    private Label labelPalabraSecreta = new Label();
    private String formatoSecreto = "";
    private String secreta = "THIAR";
    private final int MAX_FALLOS = 6;

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
                comprobarLetra(boton.getText());
            });
        }
    }

    public void initializeLabel() {
        vBoxContenedorImagenes.getChildren().clear();
        for (int i = 0; i < secreta.length(); i++) {
            formatoSecreto += "_ ";
        }
        actualizarPalabraSecreta();
        hBoxContenedorLabel.getChildren().add(labelPalabraSecreta);
    }

    public void initializeImagenes() {
        String direccionImg = "/img/Hangman-" + 0 + ".png";
        Image imagen = new Image(getClass().getResourceAsStream(direccionImg));
        ImageView imageView = new ImageView(imagen);
        vBoxContenedorImagenes.getChildren().add(imageView);
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
            fallos++;
            String direccionImg = "/img/Hangman-" + fallos + ".png";
            Image imagen = new Image(getClass().getResourceAsStream(direccionImg));
            ImageView imageView = new ImageView(imagen);
            vBoxContenedorImagenes.getChildren().clear();
            vBoxContenedorImagenes.getChildren().add(imageView);
        }

        boolean todasAdivinadas = false;
        for (int i = 0; i < secreta.length(); i++) {
            if (letrasPulsadas.contains(secreta.charAt(i))) {
                todasAdivinadas = true;
                break;
            } else {
                todasAdivinadas = false;
            }
        }

        for (int i = 0; i < secreta.length(); i++) {
            if (!letrasPulsadas.contains(secreta.charAt(i))) {
                todasAdivinadas = false;
                break;
            }
        }

        if (todasAdivinadas && acertada) {
            mostrarAlertaVictoria();
            reiniciarJuego();
            preguntarRepetirJuego();
        }
        if (fallos >= MAX_FALLOS) {
            mostrarAlertaPerdida();
            reiniciarJuego();
            preguntarRepetirJuego();
        }

    }

    public void actualizarPalabraSecreta() {
        labelPalabraSecreta.setText(formatoSecreto.trim().toUpperCase());
        labelPalabraSecreta.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 20px;");
    }

    public void mostrarAlertaPerdida() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        Image icono = new Image(getClass().getResourceAsStream("/img/ahorcado.png"));
        ImageView imageView = new ImageView(icono);
        alerta.setGraphic(imageView);
        alerta.setTitle("Perdiste");
        alerta.setHeaderText(null);
        alerta.setContentText("Perdiste la palabra era: " + secreta);
        alerta.showAndWait();
    }

    public void mostrarAlertaVictoria() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        Image icono = new Image(getClass().getResourceAsStream("/img/repetir.png"));
        ImageView imageView = new ImageView(icono);
        alerta.setGraphic(imageView);
        alerta.setTitle("Ganaste");
        alerta.setHeaderText("Has ganado!");
        alerta.setContentText("Adivinaste la palabra!" + secreta);
        alerta.showAndWait();
    }

    public void reiniciarJuego() {
        for (Button boton : botonesPresionados) {
            boton.setDisable(false);
            boton.setStyle("");
        }
        formatoSecreto = "";
        for (int i = 0; i < secreta.length(); i++) {
            formatoSecreto += "_ ";
        }
        actualizarPalabraSecreta();
        fallos = 0;
        botonesPresionados.clear();
        letrasPulsadas.clear();
        vBoxContenedorImagenes.getChildren().clear();
        initializeImagenes();
    }

    public void preguntarRepetirJuego() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        Image icono = new Image(getClass().getResourceAsStream("/img/mensaje.png"));
        ImageView imageView = new ImageView(icono);
        alerta.setGraphic(imageView);
        alerta.setTitle("¿Repetir juego?");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Deseas jugar de nuevo o salir?");

        ButtonType repetirJuego = new ButtonType("Repetir juego");
        ButtonType salir = new ButtonType("Salir");

        alerta.getButtonTypes().setAll(repetirJuego, salir);

        alerta.showAndWait().ifPresent(response -> {
            if (response == repetirJuego) {
                reiniciarJuego();
            } else if (response == salir) {
                confirmacionSalir();
            }
        });
    }

    public void confirmacionSalir() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        Image icono = new Image(getClass().getResourceAsStream("/img/exit.png"));
        ImageView imageView = new ImageView(icono);
        alerta.setGraphic(imageView);
        alerta.setTitle("Confirmación");
        alerta.setContentText("¿Seguro deseas realizar esa accion?");
        Optional<ButtonType> respuesta = alerta.showAndWait();
        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            Platform.exit();
        } else {
            preguntarRepetirJuego();
        }
    }

}