package EstrategiaDetonaciones;

import java.util.LinkedList;

import Entidades.Entidad;
import Logica.Color;
import Tablero.Tablero;

public class EstrategiaDetonacionGemaRayadaVertical implements EstategiaDetonacion{

	public void detonar(Entidad entidad, Tablero tablero) {
		LinkedList<Entidad> entidades=new LinkedList<>();
		int columna = entidad.get_columna();
		int fila_adycaente=entidad.get_fila();
		entidad.set_color(Color.TRANSPARENTE);
		entidad.cargarImagenesRepresentativas(entidad.get_rutadeLaImagen());
		entidad.get_EntidadGrafica().notificarse_detonacion();

		if (esPosicionValida(tablero, fila_adycaente - 1, columna) && tablero.get_entidad(fila_adycaente - 1, columna).esAfectadaPorExplosionAdyacente()) {
			tablero.get_entidad(fila_adycaente - 1, columna).detonar(tablero);
		}
		
		if (esPosicionValida(tablero, fila_adycaente + 1, columna) && tablero.get_entidad(fila_adycaente + 1, columna).esAfectadaPorExplosionAdyacente()) {
			tablero.get_entidad(fila_adycaente + 1, columna).detonar(tablero);
		}
		// Realizar los intercambios y ajustes necesarios en la columna indicada
		for (int fila = 0; fila < tablero.getFila(); fila++) {
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
