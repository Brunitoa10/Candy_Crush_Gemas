package EstrategiaDetonacion;

import Entidades.Entidad;
import Tablero.TableroJuego;

public interface EstrategiaDetonacion {
    public void detonar(Entidad entidad,TableroJuego tablero);
}
