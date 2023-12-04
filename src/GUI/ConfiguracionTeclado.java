package GUI;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import Logica.Logica;

public class ConfiguracionTeclado {
    private Map<Integer, Runnable> accionesTeclado;

    public ConfiguracionTeclado(Logica miLogica, GUI miGUI) {
        accionesTeclado = new HashMap<>();
        configurarAccionesTeclado(miLogica, miGUI);
    }

    private void configurarAccionesTeclado(Logica miLogica, GUI miGUI) {
        // Acciones del cursor
        accionesTeclado.put(KeyEvent.VK_LEFT, () -> miLogica.mover_jugador(GUI.IZQUIERDA));
        accionesTeclado.put(KeyEvent.VK_RIGHT, () -> miLogica.mover_jugador(GUI.DERECHA));
        accionesTeclado.put(KeyEvent.VK_UP, () -> miLogica.mover_jugador(GUI.ARRIBA));
        accionesTeclado.put(KeyEvent.VK_DOWN, () -> miLogica.mover_jugador(GUI.ABAJO));

        // Acciones de intercambio
        accionesTeclado.put(KeyEvent.VK_W, () -> {
            if (!miGUI.getBloquear_intercambios()) {
                miLogica.intercambiar(GUI.ARRIBA);
            }
        });
        accionesTeclado.put(KeyEvent.VK_S, () -> {
            if (!miGUI.getBloquear_intercambios()) {
                miLogica.intercambiar(GUI.ABAJO);
            }
        });
        accionesTeclado.put(KeyEvent.VK_A, () -> {
            if (!miGUI.getBloquear_intercambios()) {
                miLogica.intercambiar(GUI.IZQUIERDA);
            }
        });
        accionesTeclado.put(KeyEvent.VK_D, () -> {
            if (!miGUI.getBloquear_intercambios()) {
                miLogica.intercambiar(GUI.DERECHA);
            }
        });
        accionesTeclado.put(KeyEvent.VK_R, () -> {
            if (!miGUI.getBloquear_intercambios()) {
                miGUI.mostrarPuntajes();
            }
        });
        accionesTeclado.put(KeyEvent.VK_V, () -> {
            if (!miGUI.getBloquear_intercambios()) {
                miGUI.animar_intercambio((Celda) miGUI.getPanelTablero().getComponent(2));
            }
        });
    }

    public Runnable obtenerAccion(int codigoTecla) {
        return accionesTeclado.get(codigoTecla);
    }
}
