package GeneradorEntidades;

import Entidades.Entidad;
import Tablero.TableroNotificable;

public interface EntidadFactory {
    public abstract Entidad crearEntidad(TableroNotificable tablero, int fila, int columna, int color);
}
