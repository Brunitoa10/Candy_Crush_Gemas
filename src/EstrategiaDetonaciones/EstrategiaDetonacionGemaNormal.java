package EstrategiaDetonaciones;

import Entidades.Entidad;
import Logica.Color;
import Tablero.Tablero;

public class EstrategiaDetonacionGemaNormal implements EstategiaDetonacion{

	@Override
	public void detonar(Entidad entidad, Tablero tablero) {
		System.out.println("------EstrategiaDetonacionGemaNormal---------");
	
	    entidad.set_color(Color.TRANSPARENTE);
	    entidad.cargarImagenesRepresentativas(entidad.get_ruta());
	    entidad.getEGrafica().notificarse_explosion();
	    entidad.getEGrafica().notificarse_cambio_estado();
	}

}
