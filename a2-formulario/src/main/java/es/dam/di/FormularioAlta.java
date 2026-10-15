
package es.dam.di;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Actividad 2 - Formulario que valida mientras se escribe.
 *
 * Ejecución: mvn clean javafx:run
 * Crea un formulario de alta de pacientes con validación en vivo.
 *
 * Decisión de diseño: avisar de los errores mientras se escribe,
 * para que el usuario pueda corregirlos antes de guardar.
 * Se renuncia a mostrar todos los errores únicamente al final.
 *
 * Vídeo de uso (1 minuto): https://drive.google.com/file/d/1ZJAhutrZRczguKY5sPS_BclfS1iSfWI4/view?usp=drive_link
 *
 * @author Alejandro
 * @version 1.0
 */
public class FormularioAlta extends Application {

    private final TextField nombreField = new TextField();
    private final TextField correoField = new TextField();
    private final TextField telefonoField = new TextField();

    private final Label errorNombre = new Label();
    private final Label errorCorreo = new Label();
    private final Label errorTelefono = new Label();
    private final Label resultadoLabel = new Label();

    private final Button guardarButton = new Button("Guardar");

    private final BooleanProperty nombreValido =
            new SimpleBooleanProperty(false);
    private final BooleanProperty correoValido =
            new SimpleBooleanProperty(false);
    private final BooleanProperty telefonoValido =
            new SimpleBooleanProperty(false);

    private Stage ventanaPrincipal;

    @Override
    public void start(Stage stage) {
        ventanaPrincipal = stage;

        VBox root = crearFormulario();
        configurarValidaciones();

        Scene scene = new Scene(root, 430, 560);

        stage.setTitle("Alta de paciente - A2");
        stage.setScene(scene);
        stage.show();
    }

    private VBox crearFormulario() {
        VBox root = new VBox(8);
        root.setStyle("-fx-padding: 20;");

        Label titulo = new Label("Alta de paciente");
        titulo.setStyle(
                "-fx-font-size: 20px; -fx-font-weight: bold;");

        nombreField.setPromptText("Nombre del paciente");
        correoField.setPromptText("ejemplo@correo.com");
        telefonoField.setPromptText("9 dígitos");

        errorNombre.setStyle("-fx-text-fill: red;");
        errorCorreo.setStyle("-fx-text-fill: red;");
        errorTelefono.setStyle("-fx-text-fill: red;");

        errorNombre.setText("Introduce al menos 2 caracteres.");
        errorCorreo.setText("Introduce un correo válido.");
        errorTelefono.setText("Introduce los 9 dígitos del teléfono.");

        guardarButton.setDefaultButton(true);
        guardarButton.disableProperty().bind(
                nombreValido.not()
                        .or(correoValido.not())
                        .or(telefonoValido.not()));

        guardarButton.setOnAction(event -> confirmarRegistro());

        HBox botones = new HBox(10, guardarButton);

        root.getChildren().addAll(
                titulo,
                new Label("Nombre:"),
                nombreField,
                errorNombre,
                new Label("Correo electrónico:"),
                correoField,
                errorCorreo,
                new Label("Teléfono:"),
                telefonoField,
                errorTelefono,
                botones,
                resultadoLabel);

        return root;
    }

    private void configurarValidaciones() {
        // El teléfono solo admite dígitos y un máximo de 9.
        // El filtro también se aplica cuando se pega texto.
        telefonoField.setTextFormatter(new TextFormatter<String>(
                change -> change.getControlNewText()
                        .matches("\\d{0,9}") ? change : null));

        nombreField.textProperty().addListener(
                (obs, anterior, nuevo) -> validarNombre(nuevo));

        correoField.textProperty().addListener(
                (obs, anterior, nuevo) -> validarCorreo(nuevo));

        telefonoField.textProperty().addListener(
                (obs, anterior, nuevo) -> validarTelefono(nuevo));

        validarNombre(nombreField.getText());
        validarCorreo(correoField.getText());
        validarTelefono(telefonoField.getText());
    }

    private void validarNombre(String texto) {
        boolean valido = texto.trim().length() >= 2;
        nombreValido.set(valido);

        if (valido) {
            errorNombre.setText("");
        } else if (texto.isBlank()) {
            errorNombre.setText("Introduce el nombre del paciente.");
        } else {
            errorNombre.setText("Escribe al menos 2 caracteres.");
        }
    }

    private void validarCorreo(String texto) {
        boolean valido = texto.matches(
                "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

        correoValido.set(valido);

        if (valido) {
            errorCorreo.setText("");
        } else {
            errorCorreo.setText(
                    "Introduce un correo como ejemplo@correo.com.");
        }
    }

    private void validarTelefono(String texto) {
        boolean valido = texto.matches("\\d{9}");
        telefonoValido.set(valido);

        if (valido) {
            errorTelefono.setText("");
        } else {
            errorTelefono.setText(
                    "Introduce los 9 dígitos, sin letras ni espacios.");
        }
    }

    private void confirmarRegistro() {
        Alert dialogo = new Alert(Alert.AlertType.CONFIRMATION);

        dialogo.initOwner(ventanaPrincipal);
        dialogo.initModality(Modality.WINDOW_MODAL);
        dialogo.setTitle("Confirmar alta");
        dialogo.setHeaderText("¿Quieres registrar al paciente?");
        dialogo.setContentText(
                "Nombre: " + nombreField.getText().trim()
                + "\nCorreo: " + correoField.getText().trim()
                + "\nTeléfono: " + telefonoField.getText());

        dialogo.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                resultadoLabel.setText(
                        "Paciente registrado: "
                        + nombreField.getText().trim()
                        + " | " + correoField.getText().trim()
                        + " | " + telefonoField.getText());
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
