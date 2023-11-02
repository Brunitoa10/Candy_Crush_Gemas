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
	    entidad.getEGrafica().notificarse_explosion();
	    entidad.getEGrafica().notificarse_cambio_estado();
	    for (int fila = 0; fila < tablero.getFila(); fila++) {
	        tablero.get_entidad(fila, columna).set_color(Color.TRANSPARENTE);
	        tablero.get_entidad(fila, columna).cargarImagenesRepresentativas(tablero.get_entidad(fila, columna).get_ruta());
	        tablero.get_entidad(fila, columna).getEGrafica().notificarse_explosion();
	        tablero.get_entidad(fila, columna).getEGrafica().notificarse_cambio_estado();
	    }
	}
}
