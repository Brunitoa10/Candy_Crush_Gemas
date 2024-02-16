package Entidades;

import EstrategiaDetonacion.EstrategiaDetonacionPotenciadorVertical;
import Tablero.TableroJuego;
import Tablero.TableroNotificable;

public class PotenciadorVertical extends Potenciador {
	
	public PotenciadorVertical(TableroNotificable tablero, int fila, int columna, int color) {
		super(tablero, fila, columna, color, "/imagenes/potenciador/vertical/", true);
		estrategiaDetonacion = new EstrategiaDetonacionPotenciadorVertical();
	}
	
	public PotenciadorVertical(TableroNotificable tablero, int fila, int columna, int color, boolean visible) {
		super(tablero, fila, columna, color, "/imagenes/potenciador/vertical/", visible);
		estrategiaDetonacion = new EstrategiaDetonacionPotenciadorVertical();
	}

	@Override
	public void detonar(TableroJuego tablero) {
		estrategiaDetonacion.detonar(this, tablero);
	}
}
