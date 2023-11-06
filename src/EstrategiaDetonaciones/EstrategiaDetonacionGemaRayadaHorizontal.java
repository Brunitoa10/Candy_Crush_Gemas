package EstrategiaDetonaciones;

import Entidades.Entidad;
import Logica.Color;
import Tablero.Tablero;

public class EstrategiaDetonacionGemaRayadaHorizontal implements EstategiaDetonacion{

	@Override
	public void detonar(Entidad entidad, Tablero tablero) {
		System.out.println("-------EstrategiaDetonacionGemaRayadaHorizontal----------");
	    int fila = entidad.get_fila();
	    entidad.set_color(Color.TRANSPARENTE);
	    entidad.cargarImagenesRepresentativas(entidad.get_ruta());
	    entidad.getEGrafica().notificarse_detonacion();
	    //entidad.getEGrafica().notificarse_cambio_estado();

	    // Realizar los intercambios y ajustes necesarios en la fila indicada
	    for (int columna = 0; columna < tablero.getColumna() - 1; columna++) {
	        Entidad entidadActual = tablero.get_entidad(fila, columna);
	        Entidad siguienteEntidad = tablero.get_entidad(fila, columna + 1);

	        entidadActual.set_color(siguienteEntidad.get_color());
	        entidadActual.cargarImagenesRepresentativas(siguienteEntidad.get_ruta());
	        entidadActual.getEGrafica().notificarse_detonacion();
	        //entidadActual.getEGrafica().notificarse_cambio_estado();
	    }

	    // Poner ceros en la última posición de la fila
	    Entidad entidadFinal = tablero.get_entidad(fila, tablero.getColumna() - 1);
	    entidadFinal.set_color(Color.TRANSPARENTE);
	    entidadFinal.cargarImagenesRepresentativas(entidadFinal.get_ruta());
	    //entidadFinal.getEGrafica().notificarse_cambio_estado();
	}

}
