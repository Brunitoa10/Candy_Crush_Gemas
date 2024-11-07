package EstrategiaDetonacion;

import Entidades.Entidad;
import Tablero.TableroJuego;
import Vista.EntidadGrafica;

public class EstrategiaDetonacionNormal implements EstrategiaDetonacion{

    @Override
    public void detonar(Entidad entidad, TableroJuego tablero) {
        EntidadGrafica entidad_grafica = entidad.get_entidad_grafica();
        entidad.detonada();
	    entidad_grafica.notificarse_detonacion();
    }
    
}
