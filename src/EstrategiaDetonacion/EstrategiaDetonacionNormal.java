package EstrategiaDetonacion;

import Entidades.Entidad;
import GUI.EntidadGrafica;
import Tablero.TableroJuego;

public class EstrategiaDetonacionNormal implements EstrategiaDetonacion{

    @Override
    public void detonar(Entidad entidad, TableroJuego tablero) {
        EntidadGrafica entidad_grafica = entidad.get_entidad_grafica();
        entidad.detonada();
	    entidad_grafica.notificarse_detonacion();
    }
    
}
