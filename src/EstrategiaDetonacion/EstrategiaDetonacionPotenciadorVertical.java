package EstrategiaDetonacion;

import Entidades.Entidad;
import Tablero.TableroJuego;
import Vista.EntidadGrafica;

public class EstrategiaDetonacionPotenciadorVertical implements EstrategiaDetonacion{

    @Override
    public void detonar(Entidad entidad, TableroJuego tablero) {
        EntidadGrafica entidad_grafica;
        Entidad entidadAux;
        int col = entidad.get_columna();
	     
        for (int fila = 0; fila < tablero.get_filas(); fila++) {
            entidadAux = tablero.obtenerEntidad(fila, col);
            entidad_grafica = entidadAux.get_entidad_grafica();
            entidadAux.detonada();
            entidad_grafica.notificarse_detonacion();
        }
    }
    
}
