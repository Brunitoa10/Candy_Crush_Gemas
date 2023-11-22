package GeneradorEntidades;

import Entidades.Entidad;
import Entidades.GemaRayada;
import Logica.Color;
import Tablero.TableroJuego;

public class GemaRayadaFactory implements EntidadFactory{

	@Override
	public Entidad crear(TableroJuego tablero, int i, int j, String[] partes, String skin) {
		return new GemaRayada(tablero, i, j, new Color(Integer.parseInt(partes[1].trim()) % 10), Integer.parseInt(partes[1].trim()) / 10, true,skin);
	}

}
