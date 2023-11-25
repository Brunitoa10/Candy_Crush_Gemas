package EstrategiaDetonaciones;

import java.util.LinkedList;

import Entidades.Entidad;
import Logica.Color;
import Tablero.Tablero;

public class EstrategiaDetonacionGemaEnvuelta implements EstategiaDetonacion{

	@Override
	public void detonar(Entidad entidad, Tablero tablero) {
		LinkedList<Entidad> entidades= new LinkedList<>();
		int fila = entidad.get_fila();
		int columna = entidad.get_columna();

		// Verificar y detonar las 8 posiciones circundantes
		for (int i = fila - 1; i <= fila + 1; i++) {
			for (int j = columna - 1; j <= columna + 1; j++) {
				if (tablero.en_rango(i, j)) {
					entidades.addLast(tablero.get_entidad(i, j));
				}
			}
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

}
