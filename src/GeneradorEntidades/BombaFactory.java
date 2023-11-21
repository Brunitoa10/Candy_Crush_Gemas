package GeneradorEntidades;

import Entidades.Bomba;
import Entidades.Entidad;
import Logica.Color;
import Nivel.Nivel;
import Tablero.TableroJuego;

public class BombaFactory implements EntidadFactory{

	@Override
	public Entidad crear(TableroJuego tablero, int i, int j, String[] partes) {
		return new Bomba(tablero, i, j, new Color(Integer.parseInt(partes[1].trim())), true, tablero.obtenerObserver(), Nivel.getLogica());
	}

}
