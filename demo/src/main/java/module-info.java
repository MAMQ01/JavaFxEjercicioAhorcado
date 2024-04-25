module thiar {
    requires javafx.controls;
    requires javafx.fxml;

    opens thiar to javafx.fxml;
    exports thiar;
}
