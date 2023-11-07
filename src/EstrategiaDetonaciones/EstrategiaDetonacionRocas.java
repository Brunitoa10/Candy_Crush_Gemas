package EstrategiaDetonaciones;

import Entidades.Entidad;
import Logica.Color;
import Tablero.Tablero;

public class EstrategiaDetonacionRocas implements EstategiaDetonacion {
	@Override
	public void detonar(Entidad entidad, Tablero tablero) {
	    int fila = entidad.get_fila();
	    int columna = entidad.get_columna();

	    // Detonar la entidad actual
	    entidad.set_color(Color.TRANSPARENTE);
	    entidad.cargarImagenesRepresentativas(entidad.get_ruta());
	    entidad.getEGrafica().notificarse_detonacion();
	   
	    // Realizar los intercambios y ajustes necesarios
	    /*for (int i = fila; i > 0; i--) {
	        swapEntidades(tablero, i, columna, i - 1, columna);
	    }*/
	    
	    Entidad entidadTopeColumna = tablero.get_entidad(0, columna);
	    entidadTopeColumna.set_color(Color.TRANSPARENTE);
	    entidadTopeColumna.cargarImagenesRepresentativas(entidadTopeColumna.get_imagen_representativa());
	    
	}

	/*private void swapEntidades(Tablero tablero, int f1, int c1, int f2, int c2) {
	    Entidad entidad1 = tablero.get_entidad(f1, c1);
	    Entidad entidad2 = tablero.get_entidad(f2, c2);
	    
	    entidad2.cambiar_posicion(f1, c1);
	    entidad1.set_color(entidad2.get_color());
	    entidad1.cargarImagenesRepresentativas(entidad2.get_ruta());
	    
	}*/
}
