package es.dam.di;

import es.dam.di.componente.CampoValidado;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Actividad 3 - Pantalla principal de la clínica.
 *
 * Integra cabecera, navegación, agenda de citas y alta rápida
 * mediante un componente reutilizable de campo validado.
 *
 * Ejecución: mvn clean javafx:run
 *
 * @author Alejandro
 * @version 1.2
 */
public class PantallaPrincipal extends Application {

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        // Cabecera superior
        Label titulo = new Label("CLÍNICA");
        titulo.getStyleClass().add("titulo-clinica");

        Label subtitulo = new Label("Gestión de pacientes y citas");
        subtitulo.getStyleClass().add("subtitulo");

        HBox cabecera = new HBox(20, titulo, subtitulo);
        cabecera.getStyleClass().add("cabecera");
        cabecera.setAlignment(Pos.CENTER_LEFT);
        root.setTop(cabecera);

        // Navegación lateral
        Label tituloNavegacion = new Label("NAVEGACIÓN");
        tituloNavegacion.getStyleClass().add("titulo-navegacion");

        Button inicio = new Button("Inicio");
        Button pacientes = new Button("Pacientes");
        Button citas = new Button("Citas");

        inicio.setMaxWidth(Double.MAX_VALUE);
        pacientes.setMaxWidth(Double.MAX_VALUE);
        citas.setMaxWidth(Double.MAX_VALUE);

        VBox navegacion = new VBox(
                14, tituloNavegacion, inicio, pacientes, citas
        );
        navegacion.getStyleClass().add("navegacion");
        root.setLeft(navegacion);

        // Búsqueda de citas
        Label tituloAgenda = new Label("Agenda de citas");
        tituloAgenda.getStyleClass().add("titulo-seccion");

        Label textoBusqueda = new Label("Buscar una cita");

        TextField buscador = new TextField();
        buscador.setPromptText(
                "Nombre del paciente o motivo de la cita..."
        );
        HBox.setHgrow(buscador, Priority.ALWAYS);

        Button botonBuscar = new Button("Buscar");

        HBox filaBusqueda = new HBox(10, buscador, botonBuscar);
        filaBusqueda.setAlignment(Pos.CENTER_LEFT);

        VBox tarjetaBusqueda = new VBox(
                10, textoBusqueda, filaBusqueda
        );
        tarjetaBusqueda.getStyleClass().add("tarjeta");

        // Listado de citas
        Label tituloListado = new Label("Próximas citas");
        tituloListado.getStyleClass().add("titulo-seccion");

        Label mensajeCitas = new Label(
                "Aquí se mostrará el listado de citas."
        );

        VBox tarjetaCitas = new VBox(
                12, tituloListado, mensajeCitas
        );
        tarjetaCitas.getStyleClass().add("tarjeta");
        VBox.setVgrow(tarjetaCitas, Priority.ALWAYS);

        // Alta rápida: componente reutilizable de A4
        Label tituloAlta = new Label("Alta rápida de paciente");
        tituloAlta.getStyleClass().add("titulo-seccion");

        CampoValidado campoNuevoPaciente = new CampoValidado();
        campoNuevoPaciente.setTextoEtiqueta("Nombre del paciente");
        campoNuevoPaciente.setLongitudMinima(3);
        campoNuevoPaciente.getCampo().setPromptText(
                "Introduce el nombre del paciente"
        );
        campoNuevoPaciente.setMaxWidth(350);

        Button botonAlta = new Button("Validar nombre");

        // Resultado visible dentro de la tarjeta
        Label resultadoValidacion = new Label(
                "Introduce un nombre de al menos 3 caracteres."
        );
        resultadoValidacion.setWrapText(true);

        VBox tarjetaAlta = new VBox(
                12,
                tituloAlta,
                campoNuevoPaciente,
                botonAlta,
                resultadoValidacion
        );
        tarjetaAlta.getStyleClass().add("tarjeta");
        tarjetaAlta.setAlignment(Pos.CENTER_LEFT);

        // Contenedor central
        VBox contenido = new VBox(
                18,
                tituloAgenda,
                tarjetaBusqueda,
                tarjetaCitas,
                tarjetaAlta
        );
        contenido.getStyleClass().add("contenido");
        root.setCenter(contenido);

        // Barra de estado inferior
        Label estado = new Label(
                "Sistema de gestión de clínica · Preparado"
        );
        estado.getStyleClass().add("estado-texto");

        HBox barraEstado = new HBox(estado);
        barraEstado.getStyleClass().add("barra-estado");
        barraEstado.setAlignment(Pos.CENTER_LEFT);
        root.setBottom(barraEstado);

        // Validación del nombre al pulsar el botón
        botonAlta.setOnAction(event -> {
            String nombre = campoNuevoPaciente
                    .getCampo()
                    .getText()
                    .trim();

            if (campoNuevoPaciente.esValido()) {
                String mensaje =
                        "Nombre válido: " + nombre;

                resultadoValidacion.setText(mensaje);
                estado.setText(mensaje);
            } else {
                String mensaje =
                        "Nombre no válido. Introduce al menos 3 caracteres.";

                resultadoValidacion.setText(mensaje);
                estado.setText(mensaje);
            }
        });

        // Escena y hoja de estilos
        Scene scene = new Scene(root, 1000, 650);

        scene.getStylesheets().add(
                getClass()
                        .getResource("/clinica.css")
                        .toExternalForm()
        );

        stage.setTitle("Clínica - Pantalla principal");
        stage.setMinWidth(800);
        stage.setMinHeight(500);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Inicia la aplicación JavaFX.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        launch(args);
    }
}