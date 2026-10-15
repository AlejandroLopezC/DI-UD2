package es.dam.di.componente;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Componente reutilizable que muestra un campo de texto con etiqueta
 * y comprueba si el contenido alcanza una longitud mínima.
 *
 * @author Alejandro
 * @version 1.0
 */
public class CampoValidado extends VBox {

    private final Label etiqueta;
    private final TextField campo;

    /** Texto de la etiqueta, con valor inicial "Nombre". */
    private final StringProperty textoEtiqueta =
            new SimpleStringProperty(this, "textoEtiqueta", "Nombre");

    /** Longitud mínima requerida, con valor inicial 3. */
    private final IntegerProperty longitudMinima =
            new SimpleIntegerProperty(this, "longitudMinima", 3) {
                @Override
                public void set(int valor) {
                    if (valor < 0) {
                        throw new IllegalArgumentException(
                                "La longitud mínima no puede ser negativa");
                    }
                    super.set(valor);
                }
            };

    /** Evento propio que se lanza cuando cambia la validez del campo. */
    public static final EventType<Event> VALIDEZ_CAMBIADA =
            new EventType<>(Event.ANY, "VALIDEZ_CAMBIADA");

    private boolean validoAnterior;

    /**
     * Crea un campo validado con etiqueta y texto.
     */
    public CampoValidado() {
        etiqueta = new Label();
        campo = new TextField();

        etiqueta.textProperty().bind(textoEtiqueta);

        getChildren().addAll(etiqueta, campo);
        setSpacing(5);

        validoAnterior = esValido();

        campo.textProperty().addListener((observable, anterior, nuevo) -> {
            comprobarCambioDeValidez();
        });

        longitudMinima.addListener((observable, anterior, nuevo) -> {
            comprobarCambioDeValidez();
        });
    }

    /**
     * Devuelve la propiedad del texto de la etiqueta.
     *
     * @return propiedad del texto de la etiqueta
     */
    public StringProperty textoEtiquetaProperty() {
        return textoEtiqueta;
    }

    /**
     * Devuelve el texto de la etiqueta.
     *
     * @return texto de la etiqueta
     */
    public String getTextoEtiqueta() {
        return textoEtiqueta.get();
    }

    /**
     * Establece el texto de la etiqueta.
     *
     * @param texto texto que se mostrará
     */
    public void setTextoEtiqueta(String texto) {
        textoEtiqueta.set(texto);
    }

    /**
     * Devuelve la propiedad de la longitud mínima.
     *
     * @return propiedad de la longitud mínima
     */
    public IntegerProperty longitudMinimaProperty() {
        return longitudMinima;
    }

    /**
     * Devuelve la longitud mínima requerida.
     *
     * @return longitud mínima
     */
    public int getLongitudMinima() {
        return longitudMinima.get();
    }

    /**
     * Establece la longitud mínima requerida.
     *
     * @param longitud longitud mínima
     * @throws IllegalArgumentException si la longitud es negativa
     */
    public void setLongitudMinima(int longitud) {
        if (longitud < 0) {
            throw new IllegalArgumentException(
                    "La longitud mínima no puede ser negativa");
        }
        longitudMinima.set(longitud);
    }

    /**
     * Comprueba si el texto introducido cumple la longitud mínima.
     *
     * @return true si el contenido es válido
     */
    public boolean esValido() {
        return campo.getText().trim().length() >= getLongitudMinima();
    }

    /**
     * Devuelve la propiedad del texto introducido.
     *
     * @return propiedad del texto del campo
     */
    public StringProperty textoProperty() {
        return campo.textProperty();
    }

    /**
     * Devuelve el campo de texto interno.
     *
     * @return campo de texto
     */
    public TextField getCampo() {
        return campo;
    }

    /**
     * Comprueba si ha cambiado la validez y, en ese caso,
     * lanza el evento correspondiente.
     */
    private void comprobarCambioDeValidez() {
        boolean validoActual = esValido();

        if (validoActual != validoAnterior) {
            validoAnterior = validoActual;
            fireEvent(new Event(VALIDEZ_CAMBIADA));
        }
    }
}
