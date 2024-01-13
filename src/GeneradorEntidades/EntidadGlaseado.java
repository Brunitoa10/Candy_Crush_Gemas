package GeneradorEntidades;

import Entidades.Entidad;
import Entidades.Glaseado;
import Tablero.TableroNotificable;

public class EntidadGlaseado implements EntidadFactory{

    @Override
    public Entidad crearEntidad(TableroNotificable tablero, int fila, int columna, int color) {
        return new Glaseado(tablero, fila, columna, color);
    }
    
}
