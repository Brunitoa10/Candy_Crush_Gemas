package GeneradorEntidades;

import Entidades.Entidad;
import Entidades.GemaEnvuelta;
import Logica.Color;
import Tablero.TableroJuego;

public class GemaEnvueltaFactory implements EntidadFactory{

	@Override
	public Entidad crear(TableroJuego tablero, int i, int j, String[] partes) {
		return new GemaEnvuelta(tablero, i, j, new Color(Integer.parseInt(partes[1].trim()) / 10), true);
	}

}
