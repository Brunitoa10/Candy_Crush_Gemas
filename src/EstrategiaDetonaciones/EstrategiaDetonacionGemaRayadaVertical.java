package EstrategiaDetonaciones;

import Entidades.Entidad;
import Logica.Color;
import Tablero.Tablero;

public class EstrategiaDetonacionGemaRayadaVertical implements EstategiaDetonacion{

	public void detonar(Entidad entidad, Tablero tablero) {
		System.out.println("-------EstrategiaDetonacionGemaRayadaVertical----------");
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
	}
}
