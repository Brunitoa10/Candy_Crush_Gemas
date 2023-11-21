package GeneradorEntidades;

import Entidades.Entidad;
import Entidades.Roca;
import Tablero.TableroJuego;

public class RocaFactory implements EntidadFactory{

	@Override
	public Entidad crear(TableroJuego tablero, int i, int j, String[] partes) {
		return new Roca(tablero, i, j, true);
	}

}
