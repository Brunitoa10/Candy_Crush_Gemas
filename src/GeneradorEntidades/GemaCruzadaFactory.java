package GeneradorEntidades;

import Entidades.Entidad;
import Entidades.GemaCruzada;
import Logica.Color;
import Tablero.TableroJuego;

public class GemaCruzadaFactory implements EntidadFactory{

	@Override
	public Entidad crear(TableroJuego tablero, int i, int j, String[] partes, String skin) {
		return new GemaCruzada(tablero, i, j, new Color(Integer.parseInt(partes[1].trim())), true,skin);
	}

}
