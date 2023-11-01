package EstrategiaDetonaciones;

import Entidades.Entidad;
import Tablero.Tablero;

public class EstrategiaDetonacionGemaRayadaHorizontal implements EstategiaDetonacion{

	@Override
	public void detonar(Entidad entidad, Tablero tablero) {
		int fila = entidad.get_fila();
        for (int columna = 0; columna < tablero.getColumna(); columna++) {
            tablero.get_entidad(fila, columna).detonar(tablero);
        }
	}

}
