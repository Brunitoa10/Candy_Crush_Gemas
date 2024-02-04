package GUI;

import java.awt.*;
import javax.swing.*;
import Logica.Juego;


public class Etiquetas {
    private JLabel lblMovimientos;
    private JLabel lblTiempo;
    private JLabel lblVidas;
    private Juego juego;
    private JPanel panel_derecho;


    public Etiquetas(Juego juego){
        this.juego = juego;
        crear_etiquetas();
    }

    private void crear_etiquetas(){
        lblMovimientos = new JLabel("Movimientos: "+juego.getNivelActual().getCantidadMovimientos());
        lblTiempo = new JLabel("Tiempo: "+juego.getNivelActual().getCantidadTiempo());
        lblVidas = new JLabel("Vidas: "+juego.getNivelActual().getCantidadVidas());

         // Crear un nuevo JPanel para las etiquetas del lado derecho
         panel_derecho = new JPanel();
         panel_derecho.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5)); // Alineado a la izquierda con espacio horizontal y vertical
 
         // Agregar las etiquetas al panel derecho
         panel_derecho.add(lblMovimientos);
         panel_derecho.add(lblTiempo);
         panel_derecho.add(lblVidas);
    }

    public JPanel getPanelDerecho() {
        return panel_derecho;
    }

    public void actualizarEtiquetaMovimientos(int movimientos) {
        lblMovimientos.setText("Movimientos: " + movimientos);
    }

    public void actualizarEtiquetaVidas(int vidas) {
        lblVidas.setText("Vidas: " + vidas);
    }

    public void actualizarEtiquetaTiempo(int tiempo) {
        lblTiempo.setText("Tiempo: " + tiempo + "s");
    }
}
