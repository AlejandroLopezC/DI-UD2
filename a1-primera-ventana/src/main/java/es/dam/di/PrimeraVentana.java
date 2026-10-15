
package es.dam.di;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Actividad 1 - Primera ventana JavaFX.
 * Formulario de alta de pacientes.
 *
 * @author Alejandro
 * @version 1.0
 */
public class PrimeraVentana extends Application {

    @Override
    public void start(Stage stage) {

        // Campos de texto
        TextField nombreField = new TextField();
        nombreField.setPromptText("Introduce el nombre");
        nombreField.setPrefWidth(250);
        nombreField.setTooltip(
                new Tooltip("Nombre del paciente"));

        TextField apellidosField = new TextField();
        apellidosField.setPromptText("Introduce los apellidos");
        apellidosField.setPrefWidth(250);
        apellidosField.setTooltip(
                new Tooltip("Apellidos del paciente"));

        // Selector de especialidad
        ComboBox<String> especialidadCombo = new ComboBox<>();
        especialidadCombo.getItems().addAll(
                "Medicina general",
                "Pediatría",
                "Dermatología",
                "Cardiología");
        especialidadCombo.setPromptText(
                "Selecciona una especialidad");
        especialidadCombo.setMaxWidth(Double.MAX_VALUE);

        // Casilla de privacidad
        CheckBox privacidadCheck = new CheckBox(
                "Acepto la política de privacidad");

        // Botones
        Button guardarButton = new Button("Guardar");
        guardarButton.setDefaultButton(true);

        Button limpiarButton = new Button("Limpiar");

        // Contador enlazado a la longitud del nombre
        Label contadorLabel = new Label();
        contadorLabel.textProperty().bind(
                Bindings.concat(
                        "Caracteres en el nombre: ",
                        nombreField.textProperty().length()));

        // Etiqueta para los mensajes
        Label mensajeLabel = new Label();
        mensajeLabel.setStyle("-fx-text-fill: red;");

        // Acción del botón Guardar
        guardarButton.setOnAction(event -> {
            if (nombreField.getText().isBlank()
                    || apellidosField.getText().isBlank()
                    || especialidadCombo.getValue() == null
                    || !privacidadCheck.isSelected()) {

                mensajeLabel.setStyle("-fx-text-fill: red;");
                mensajeLabel.setText(
                        "Completa los campos y acepta la privacidad.");

            } else {
                mensajeLabel.setStyle("-fx-text-fill: green;");
                mensajeLabel.setText(
                        "Paciente registrado correctamente.");

                System.out.println("Paciente: "
                        + nombreField.getText() + " "
                        + apellidosField.getText()
                        + " | Especialidad: "
                        + especialidadCombo.getValue());
            }
        });

        // Acción del botón Limpiar
        limpiarButton.setOnAction(event -> {
            nombreField.clear();
            apellidosField.clear();
            especialidadCombo.getSelectionModel().clearSelection();
            privacidadCheck.setSelected(false);
            mensajeLabel.setText("");
            mensajeLabel.setStyle("-fx-text-fill: red;");
        });

        // Organización de la ventana
        VBox root = new VBox(12);
        root.setStyle("-fx-padding: 20;");

        Label titulo = new Label("Alta de paciente");
        titulo.setStyle(
                "-fx-font-size: 20px; -fx-font-weight: bold;");

        HBox botones = new HBox(10, guardarButton, limpiarButton);

        root.getChildren().addAll(
                titulo,
                new Label("Nombre:"),
                nombreField,
                new Label("Apellidos:"),
                apellidosField,
                new Label("Especialidad médica:"),
                especialidadCombo,
                privacidadCheck,
                botones,
                contadorLabel,
                mensajeLabel);

        // Crear la escena y configurar la ventana
        Scene scene = new Scene(root, 420, 520);

        stage.setTitle("Alta de paciente");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Se ejecuta al cerrar la aplicación.
     * Muestra un mensaje en la consola.
     */
    @Override
    public void stop() {
        System.out.println(
                "Cerrando la aplicación de alta de pacientes.");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
