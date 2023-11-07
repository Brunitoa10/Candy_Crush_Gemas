package EstrategiaDetonaciones;

import Entidades.Entidad;
import Logica.Color;
import Tablero.Tablero;

public class EstrategiaDetonacionGemaNormal implements EstategiaDetonacion{

	public void detonar(Entidad entidad, Tablero tablero) {
	    System.out.println("------EstrategiaDetonacionGemaNormal---------");

	    int fila = entidad.get_fila();
	    int columna = entidad.get_columna();
	    
	    entidad.set_color(Color.TRANSPARENTE);
	    entidad.cargarImagenesRepresentativas(entidad.get_ruta());
	    entidad.getEGrafica().notificarse_detonacion();
	 
	    // Verificar y detonar rocas
	    EstrategiaDetonacionRocas estrategiaRocas = new EstrategiaDetonacionRocas();
	    if (tablero.hayRocaEn(fila - 1, columna)) {
	       estrategiaRocas.detonar(tablero.get_entidad(fila - 1, columna), tablero);
	    }
	    if (tablero.hayRocaEn(fila + 1, columna)) {
	    	estrategiaRocas.detonar(tablero.get_entidad(fila + 1, columna), tablero);
	    }
	    if (tablero.hayRocaEn(fila, columna - 1)) {
	    	estrategiaRocas.detonar(tablero.get_entidad(fila, columna - 1), tablero);
	    }
	    if (tablero.hayRocaEn(fila, columna + 1)) {
	    	estrategiaRocas.detonar(tablero.get_entidad(fila, columna + 1), tablero);
	    }
	}

}
	