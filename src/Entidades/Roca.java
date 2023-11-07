package Entidades;

import EstrategiaDetonaciones.EstrategiaDetonacionGemaNormal;
import Logica.Color;
import Tablero.Tablero;

public class Roca extends Obstaculo{

	public Roca(int f, int c, boolean visible) {
		super(f, c, "/assets/obstaculo/", new Color(Color.ROCA), visible);
	}

	public void detonar(Tablero tablero) {
		estrategiaDetonacion = new EstrategiaDetonacionGemaNormal();
		estrategiaDetonacion.detonar(this,tablero);
	}

	public int get_score()
	{
		return color.get_score();
	}

	@Override
	public boolean esRoca() {
		return true;
	}
}