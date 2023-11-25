package EstrategiaDetonaciones;

import java.util.LinkedList;

import Entidades.Entidad;
import Logica.Color;
import Tablero.Tablero;

public class EstrategiaDetonacionGemaRayadaHorizontal implements EstategiaDetonacion{
	@Override

	public void detonar(Entidad entidad, Tablero tablero) {
		LinkedList<Entidad> entidades=new LinkedList<>();
	    int fila = entidad.get_fila();
		int columna_adyacente=entidad.get_columna();
	    entidad.set_color(Color.TRANSPARENTE);
	    entidad.cargarImagenesRepresentativas(entidad.get_rutadeLaImagen());
	    entidad.get_EntidadGrafica().notificarse_detonacion();

		if (esPosicionValida(tablero, fila, columna_adyacente - 1) && tablero.get_entidad(fila, columna_adyacente - 1).esAfectadaPorExplosionAdyacente()) {
			tablero.get_entidad(fila, columna_adyacente - 1).detonar(tablero);
		}
		
		if (esPosicionValida(tablero, fila, columna_adyacente + 1) && tablero.get_entidad(fila, columna_adyacente + 1).esAfectadaPorExplosionAdyacente()) {
			tablero.get_entidad(fila, columna_adyacente + 1).detonar(tablero);
		}
	    // Realizar los intercambios y ajustes necesarios en la fila indicada
	    for (int columna = 0; columna < tablero.getColumna(); columna++) {
	    	Entidad entidadActual = tablero.get_entidad(fila, columna);
			entidades.addLast(entidadActual);
	    }

		tablero.detono(entidades);
		for (Entidad ent : entidades) {
			if (ent.get_fila()==entidad.get_fila() && ent.get_columna()==entidad.get_columna()) {
				entidad.set_color(Color.TRANSPARENTE);
				entidad.cargarImagenesRepresentativas(entidad.get_rutadeLaImagen());
				entidad.get_EntidadGrafica().notificarse_detonacion();
			}
			else{
				ent.detonar(tablero);
			}
		}
	}
	 private boolean esPosicionValida(Tablero tablero, int fila, int columna) {
        return fila >= 0 && fila < tablero.getFila() && columna >= 0 && columna < tablero.getColumna();
    }
}
