package EstrategiaDetonaciones;

import Entidades.Entidad;
import Logica.Color;
import Tablero.Tablero;

public class EstrategiaDetonacionGemaRayadaVertical implements EstategiaDetonacion{

	public void detonar(Entidad entidad, Tablero tablero) {
		int columna = entidad.get_columna();
		int fila_adyacente = entidad.get_fila();
		entidad.set_color(Color.TRANSPARENTE);
		entidad.cargarImagenesRepresentativas(entidad.get_rutadeLaImagen());
		entidad.get_EntidadGrafica().notificarse_detonacion();
	
		detonarEntidadEnPosicion(tablero, fila_adyacente - 1, columna);
		detonarEntidadEnPosicion(tablero, fila_adyacente + 1, columna);
	
		// Realizar los intercambios y ajustes necesarios en la columna indicada
		for (int fila = 0; fila < tablero.getFila(); fila++) {
			detonarEntidadEnPosicion(tablero, fila, columna);
		}
	}
	
	private void detonarEntidadEnPosicion(Tablero tablero, int fila, int columna) {
		if (esPosicionValida(tablero, fila, columna) && tablero.get_entidad(fila, columna).esAfectadaPorExplosionAdyacente()) {
			tablero.get_entidad(fila, columna).detonar(tablero);
		}
	}
	
	private boolean esPosicionValida(Tablero tablero, int fila, int columna) {
		return fila >= 0 && fila < tablero.getFila() && columna >= 0 && columna < tablero.getColumna();
	}
	
	/*public void detonar(Entidad entidad, Tablero tablero) {
		int columna = entidad.get_columna();
		int fila_adycaente=entidad.get_fila();
		entidad.set_color(Color.TRANSPARENTE);
		entidad.cargarImagenesRepresentativas(entidad.get_rutadeLaImagen());
		entidad.get_EntidadGrafica().notificarse_detonacion();

		if(tablero.get_entidad(fila_adycaente-1, columna).esAfectadaPorExplosionAdyacente())
		{
			tablero.get_entidad(fila_adycaente-1, columna).detonar(tablero);
		}

		if(tablero.get_entidad(fila_adycaente+1, columna).esAfectadaPorExplosionAdyacente())
		{
			tablero.get_entidad(fila_adycaente+1, columna).detonar(tablero);
		}

		// Realizar los intercambios y ajustes necesarios en la columna indicada
		for (int fila = 0; fila < tablero.getFila(); fila++) {
			Entidad entidadActual = tablero.get_entidad(fila, columna);
			entidadActual.set_color(Color.TRANSPARENTE);
			entidadActual.cargarImagenesRepresentativas(entidadActual.get_rutadeLaImagen());
			entidadActual.get_EntidadGrafica().notificarse_detonacion();
		}
	}*/
}
