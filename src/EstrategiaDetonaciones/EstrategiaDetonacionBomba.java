package EstrategiaDetonaciones;

import Entidades.Entidad;
import Logica.Color;
import Tablero.Tablero;

public class EstrategiaDetonacionBomba implements EstategiaDetonacion{

	@Override
	public void detonar(Entidad entidad, Tablero tablero) {
		 // Detonar la entidad actual
	    entidad.set_color(Color.TRANSPARENTE);
	    entidad.cargarImagenesRepresentativas(entidad.get_rutadeLaImagen());
	    entidad.get_EntidadGrafica().notificarse_detonacion();
	}
}
