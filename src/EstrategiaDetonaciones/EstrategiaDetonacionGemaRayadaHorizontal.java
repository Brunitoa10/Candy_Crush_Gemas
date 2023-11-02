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
        entidad.getEGrafica().notificarse_explosion();
        entidad.getEGrafica().notificarse_cambio_estado();
		for (int columna = 0; columna < tablero.getColumna(); columna++) {
            tablero.get_entidad(fila, columna).set_color(Color.TRANSPARENTE);
            tablero.get_entidad(fila, columna).cargarImagenesRepresentativas(tablero.get_entidad(fila, columna).get_ruta());
            tablero.get_entidad(fila, columna).getEGrafica().notificarse_explosion();
            tablero.get_entidad(fila, columna).getEGrafica().notificarse_cambio_estado();
        }
	}

}
