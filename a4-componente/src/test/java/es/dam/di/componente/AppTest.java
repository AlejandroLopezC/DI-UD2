package es.dam.di.componente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

class AppTest {

    @BeforeAll
    static void iniciarJavaFX() {
        com.sun.javafx.application.PlatformImpl.startup(() -> {
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void propiedadesTienenValoresIniciales() throws Exception {
        ejecutarEnJavaFX(() -> {
            CampoValidado campo = new CampoValidado();

            assertEquals("Nombre", campo.getTextoEtiqueta());
            assertEquals(3, campo.getLongitudMinima());
        });
    }

    @Test
    void validaTextoSegunLongitudMinima() throws Exception {
        ejecutarEnJavaFX(() -> {
            CampoValidado campo = new CampoValidado();

            campo.getCampo().setText("Ana");
            assertTrue(campo.esValido());

            campo.getCampo().setText("Al");
            assertFalse(campo.esValido());
        });
    }

    @Test
    void permiteModificarPropiedades() throws Exception {
        ejecutarEnJavaFX(() -> {
            CampoValidado campo = new CampoValidado();

            campo.setTextoEtiqueta("Correo electrónico");
            campo.setLongitudMinima(5);

            assertEquals("Correo electrónico", campo.getTextoEtiqueta());
            assertEquals(5, campo.getLongitudMinima());
        });
    }

    @Test
    void eventoSeDisparaCuandoCambiaLaValidez() throws Exception {
        ejecutarEnJavaFX(() -> {
            CampoValidado campo = new CampoValidado();
            java.util.concurrent.atomic.AtomicInteger eventos =
                    new java.util.concurrent.atomic.AtomicInteger(0);

            campo.addEventHandler(
                    CampoValidado.VALIDEZ_CAMBIADA,
                    evento -> eventos.incrementAndGet()
            );

            campo.getCampo().setText("Ana");
            assertEquals(1, eventos.get());

            campo.getCampo().setText("Luis");
            assertEquals(1, eventos.get());

            campo.getCampo().setText("Al");
            assertEquals(2, eventos.get());
        });
    }

    private void ejecutarEnJavaFX(Runnable accion) throws Exception {
        java.util.concurrent.CountDownLatch bloqueo =
                new java.util.concurrent.CountDownLatch(1);
        java.util.concurrent.atomic.AtomicReference<Throwable> error =
                new java.util.concurrent.atomic.AtomicReference<>();

        Platform.runLater(() -> {
            try {
                accion.run();
            } catch (Throwable t) {
                error.set(t);
            } finally {
                bloqueo.countDown();
            }
        });

        bloqueo.await();

        if (error.get() != null) {
            throw new AssertionError(error.get());
        }
    }
}
