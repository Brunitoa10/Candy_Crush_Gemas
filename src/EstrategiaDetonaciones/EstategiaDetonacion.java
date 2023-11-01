package EstrategiaDetonaciones;

import Entidades.Entidad;
import Tablero.Tablero;

public interface EstategiaDetonacion {
	void detonar(Entidad entidad, Tablero tablero);
}
