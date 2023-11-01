package EstrategiaDetonaciones;

import Entidades.Entidad;
import Tablero.Tablero;

public class EstrategiaDetonacionGemaRayadaVertical implements EstategiaDetonacion{

	@Override
	public void detonar(Entidad entidad, Tablero tablero) {
		int columna = entidad.get_columna();
		for (int fila = 0; fila < tablero.getFila(); fila++) {
			tablero.get_entidad(fila, columna).detonar(tablero);
		}
	}

}
