package GeneradorEntidades;

import Entidades.Entidad;
import Tablero.TableroJuego;

public interface EntidadFactory {
	public Entidad crear(TableroJuego tablero, int i, int j, String[] partes);
}
