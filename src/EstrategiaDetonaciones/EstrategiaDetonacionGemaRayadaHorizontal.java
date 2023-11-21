package EstrategiaDetonaciones;

import Entidades.Entidad;
import Logica.Color;
import Tablero.Tablero;

public class EstrategiaDetonacionGemaRayadaHorizontal implements EstategiaDetonacion{
	@Override
	public void detonar(Entidad entidad, Tablero tablero) {
		System.out.println("-------EstrategiaDetonacionGemaRayadaHorizontal----------");
	    int fila = entidad.get_fila();
		int columna_adyacente=entidad.get_columna();
	    entidad.set_color(Color.TRANSPARENTE);
	    entidad.cargarImagenesRepresentativas(entidad.get_rutadeLaImagen());
	    entidad.get_EntidadGrafica().notificarse_detonacion();

		if(tablero.get_entidad(fila, columna_adyacente-1).esAfectadaPorExplosionAdyacente())
		{
			tablero.get_entidad(fila, columna_adyacente-1).detonar(tablero);
		}

		if(tablero.get_entidad(fila, columna_adyacente+1).esAfectadaPorExplosionAdyacente())
		{
			tablero.get_entidad(fila, columna_adyacente+1).detonar(tablero);
		}

	    // Realizar los intercambios y ajustes necesarios en la fila indicada
	    for (int columna = 0; columna < tablero.getColumna(); columna++) {
	    	Entidad entidadActual = tablero.get_entidad(fila, columna);
			entidadActual.set_color(Color.TRANSPARENTE);
			entidadActual.cargarImagenesRepresentativas(entidadActual.get_rutadeLaImagen());
			entidadActual.get_EntidadGrafica().notificarse_detonacion();
	    }
	}
}
