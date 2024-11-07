package EstrategiaDetonacion;

import Entidades.Entidad;
import Tablero.TableroJuego;
import Vista.EntidadGrafica;

public class EstrategiaDetonacionPotenciadorHorizontal implements EstrategiaDetonacion{

    @Override
    public void detonar(Entidad entidad, TableroJuego tablero) {
        EntidadGrafica entidad_grafica;
        Entidad entidadAux;
        int fila = entidad.get_fila();
	     
        for (int col = 0; col < tablero.get_columnas(); col++) {
            entidadAux = tablero.obtenerEntidad(fila, col);
            entidad_grafica = entidadAux.get_entidad_grafica();
            entidadAux.detonada();
            entidad_grafica.notificarse_detonacion();
        }
    }
    
}
