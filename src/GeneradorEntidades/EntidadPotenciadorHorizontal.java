package GeneradorEntidades;

import Entidades.Entidad;
import Entidades.PotenciadorHorizontal;
import Tablero.TableroNotificable;

public class EntidadPotenciadorHorizontal implements EntidadFactory{

    @Override
    public Entidad crearEntidad(TableroNotificable tablero, int fila, int columna, int color) {
        return new PotenciadorHorizontal(tablero, fila, columna, color);
    }
    
}
