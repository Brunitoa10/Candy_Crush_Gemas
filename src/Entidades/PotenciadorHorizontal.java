package Entidades;

import EstrategiaDetonacion.EstrategiaDetonacionPotenciadorHorizontal;
import Tablero.TableroJuego;
import Tablero.TableroNotificable;

public class PotenciadorHorizontal extends Potenciador {
	
	public PotenciadorHorizontal(TableroNotificable tablero, int fila, int columna, int color) {
		super(tablero, fila, columna, color, "/imagenes/potenciador/horizontal/", true);
		estrategiaDetonacion = new EstrategiaDetonacionPotenciadorHorizontal();
	}
	
	public PotenciadorHorizontal(TableroNotificable tablero, int fila, int columna, int color, boolean visible) {
		super(tablero, fila, columna, color, "/imagenes/potenciador/horizontal/", visible);
		estrategiaDetonacion = new EstrategiaDetonacionPotenciadorHorizontal();
	}

	@Override
	public void detonar(TableroJuego tablero) {
		estrategiaDetonacion.detonar(this, tablero);
	}	
}