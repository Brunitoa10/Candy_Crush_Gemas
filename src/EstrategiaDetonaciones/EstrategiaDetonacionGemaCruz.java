package EstrategiaDetonaciones;

import Entidades.Entidad;
import Logica.Color;
import Tablero.Tablero;

public class EstrategiaDetonacionGemaCruz implements EstategiaDetonacion{

	@Override
	 public void detonar(Entidad entidad, Tablero tablero) {
        detonarEntidadesEnFila(entidad, tablero);
        detonarEntidadesEnColumna(entidad, tablero);
    }

    private void detonarEntidadesEnFila(Entidad entidad, Tablero tablero) {
        int fila = entidad.get_fila();
        for (int columna = 0; columna < tablero.getColumna(); columna++) {
            detonarEntidad(tablero.get_entidad(fila, columna));
        }
    }

    private void detonarEntidadesEnColumna(Entidad entidad, Tablero tablero) {
        int columna = entidad.get_columna();
        for (int fila = 0; fila < tablero.getFila(); fila++) {
            detonarEntidad(tablero.get_entidad(fila, columna));
        }
    }

    private void detonarEntidad(Entidad entidad) {
        entidad.set_color(Color.TRANSPARENTE);
        entidad.cargarImagenesRepresentativas(entidad.get_rutadeLaImagen());
        entidad.get_EntidadGrafica().notificarse_detonacion();
    }

}
