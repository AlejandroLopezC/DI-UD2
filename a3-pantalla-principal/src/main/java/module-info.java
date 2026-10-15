module es.dam.di {
    requires javafx.controls;
    requires javafx.fxml;
    requires a4.componente;

    opens es.dam.di to javafx.fxml;
    exports es.dam.di;
}
