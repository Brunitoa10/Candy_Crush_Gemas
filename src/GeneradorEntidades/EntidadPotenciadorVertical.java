package GeneradorEntidades;

import Entidades.Entidad;
import Entidades.PotenciadorVertical;
import Tablero.TableroNotificable;

public class EntidadPotenciadorVertical implements EntidadFactory{

    @Override
    public Entidad crearEntidad(TableroNotificable tablero, int fila, int columna, int color) {
        return new PotenciadorVertical(tablero, fila, columna, color);
    }
    
}
