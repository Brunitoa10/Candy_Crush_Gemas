package GeneradorEntidades;

import Entidades.Entidad;
import Entidades.GemaNormal;
import Logica.Color;
import Tablero.TableroJuego;

public class GemaNormalFactory implements EntidadFactory{
	
	@Override
	public Entidad crear(TableroJuego tablero, int i, int j, String[] partes, String skin) {
		return new GemaNormal(tablero, i, j, new Color(Integer.parseInt(partes[1].trim())), true, skin);
	}
}
