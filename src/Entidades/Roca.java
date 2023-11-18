package Entidades;

import EstrategiaDetonaciones.*;
import Logica.Color;
import Tablero.Tablero;
import Tablero.TableroNotificable;

public class Roca extends Obstaculo{

	public Roca(TableroNotificable tablero,int f, int c, boolean visible) {
		super(tablero,f, c, "/assets/obstaculo/", new Color(Color.ROCA), visible);
		cargarImagenesRepresentativas(rutadeLaImagen);
	}

	public void detonar(Tablero tablero) {
		EstategiaDetonacion estrategiaDetonacion = new EstrategiaDetonacionRocas();
		estrategiaDetonacion.detonar(this,tablero);
	}

	public int get_score()
	{
		return color.get_score();
	}

	public boolean esAfectadaPorExplosionAdyacente() {
		return true;
	}
}