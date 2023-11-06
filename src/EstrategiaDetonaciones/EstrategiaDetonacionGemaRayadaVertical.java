package EstrategiaDetonaciones;

import Entidades.Entidad;
import Logica.Color;
import Tablero.Tablero;

public class EstrategiaDetonacionGemaRayadaVertical implements EstategiaDetonacion{

	public void detonar(Entidad entidad, Tablero tablero) {
		System.out.println("-------EstrategiaDetonacionGemaRayadaVertical----------");
		int columna = entidad.get_columna();
		entidad.set_color(Color.TRANSPARENTE);
		entidad.cargarImagenesRepresentativas(entidad.get_ruta());
		entidad.getEGrafica().notificarse_detonacion();
		//entidad.getEGrafica().notificarse_cambio_estado();

		// Realizar los intercambios y ajustes necesarios en la columna indicada
		for (int fila = 0; fila < tablero.getFila(); fila++) {
			Entidad entidadActual = tablero.get_entidad(fila, columna);
			entidadActual.set_color(Color.TRANSPARENTE);
			entidadActual.cargarImagenesRepresentativas(entidadActual.get_ruta());
			entidadActual.getEGrafica().notificarse_detonacion();
			//entidadActual.getEGrafica().notificarse_cambio_estado();
		}
	}
}
