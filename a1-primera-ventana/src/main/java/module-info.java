module es.dam.di {
    requires javafx.controls;
    requires javafx.fxml;

    opens es.dam.di to javafx.fxml;
    exports es.dam.di;
}
