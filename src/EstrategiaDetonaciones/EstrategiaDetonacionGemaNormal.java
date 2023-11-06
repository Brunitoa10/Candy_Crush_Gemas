package EstrategiaDetonaciones;

import Entidades.Entidad;
import Logica.Color;
import Tablero.Tablero;

public class EstrategiaDetonacionGemaNormal implements EstategiaDetonacion{

	@Override
	public void detonar(Entidad entidad, Tablero tablero) {
		System.out.println("------EstrategiaDetonacionGemaNormal---------");

	    int fila = entidad.get_fila();
	    int columna = entidad.get_columna();
	    Entidad entidadActual = null;
	    // Realizar los intercambios y ajustes necesarios
	    for (int i = fila; i > 0; i--) {
	    	entidadActual = tablero.get_entidad(i, columna); 
	    	entidadActual.set_color(tablero.get_entidad(i-1, columna).get_color());
	    	entidadActual.cargarImagenesRepresentativas(tablero.get_entidad(i-1, columna).get_ruta());
	    	entidadActual.getEGrafica().notificarse_detonacion();
	    	//entidadActual.getEGrafica().notificarse_cambio_estado();
	    }

	    // Poner ceros en la fila superior
	    entidadActual = tablero.get_entidad(0, columna);
	    
	    entidadActual.set_color(Color.TRANSPARENTE);
	    entidadActual.cargarImagenesRepresentativas(entidadActual.get_ruta());
	    //entidadActual.getEGrafica().notificarse_cambio_estado();
	}

}
