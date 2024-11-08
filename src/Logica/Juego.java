package Logica;

import Entidades.Entidad;
import Nivel.GeneradorNivel;
import Nivel.Nivel;
import Tablero.Tablero;
import Tablero.TableroJuego;
import Vista.EntidadGrafica;
import Vista.Ventana;
import Vista.VentanaJuego;

public class Juego {

	protected TableroJuego tablero;
	protected VentanaJuego ventana;
	protected Nivel nivel;
	protected int nivelJuego;

	public Juego() {
		tablero = new Tablero(this);
		nivelJuego = 1;
		nivel = GeneradorNivel.cargar_nivel_y_tablero(tablero,nivelJuego);
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
	
	public Nivel getNivelActual(){
		return nivel;
	}
}
