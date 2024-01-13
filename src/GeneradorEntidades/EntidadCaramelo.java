package GeneradorEntidades;

import Entidades.Caramelo;
import Entidades.Entidad;
import Tablero.TableroNotificable;

public class EntidadCaramelo implements EntidadFactory{
    @Override
    public Entidad crearEntidad(TableroNotificable tablero, int fila, int columna, int color) {
        return new Caramelo(tablero, fila, columna, color);
    }
}
