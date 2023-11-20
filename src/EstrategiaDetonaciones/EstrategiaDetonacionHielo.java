package EstrategiaDetonaciones;

import Entidades.Entidad;
import Logica.Color;
import Tablero.Tablero;

public class EstrategiaDetonacionHielo implements EstategiaDetonacion{

	@Override
	public void detonar(Entidad entidad, Tablero tablero) {
		entidad.get_gema_interna().detonar(tablero);
	    entidad.set_color(Color.TRANSPARENTE);
	    entidad.cargarImagenesRepresentativas(entidad.get_rutadeLaImagen());
	    entidad.get_EntidadGrafica().notificarse_detonacion();
	}

}
