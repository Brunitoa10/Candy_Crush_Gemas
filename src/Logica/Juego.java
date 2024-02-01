package Logica;
import java.awt.EventQueue;

import Entidades.Entidad;
import GUI.EntidadGrafica;
import GUI.Ventana;
import GUI.VentanaJuego;
import Nivel.GeneradorNivel;
import Nivel.Nivel;
import Tablero.Tablero;
import Tablero.TableroJuego;

public class Juego {

	protected TableroJuego tablero;
	protected VentanaJuego ventana;
	protected Nivel nivel;
	
	public Juego() {
		tablero = new Tablero(this);
		//nivel = GeneradorNivelBasico.cargar_nivel_y_tablero(1, tablero);
		nivel = GeneradorNivel.cargar_nivel_y_tablero(tablero,1);
		ventana = new Ventana(this, tablero.get_filas(), tablero.get_columnas());
		tablero.asociar_entidades_logicas_y_graficas();
		tablero.fijar_jugador(nivel.get_fila_inicial_jugador(), nivel.get_columna_inicial_jugador());
		ventana.mostrar();
	}
	
	// Operaciones para comunicación con ventana (Juego <-- Ventana)
	
	public void mover_jugador(int d) {
		tablero.mover_jugador(d);
	}
	
	public void intercambiar_entidades(int d) {
		tablero.intercambiar_entidades(d);
	}
	
	// Operaciones para comunicación con Tablero (Juego <-- Tablero)
	
	public void asociar_entidad_logica_y_grafica(Entidad entidad_logica) {
		EntidadGrafica entidad_grafica = ventana.agregar_entidad(entidad_logica);
		entidad_logica.set_entidad_grafica(entidad_grafica);
	}
	
	public static void main(String [] args) {
		EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	new Juego();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
	}
}
