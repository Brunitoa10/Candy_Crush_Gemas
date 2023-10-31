package Entidades;

import Logica.Color;
import Tablero.Tablero;

public class Roca extends Obstaculo{

	public Roca(int f, int c) {
		super(f, c, "/assets/obstaculo/", new Color(7));
	}

	public void detonar(Tablero t) {
		    System.out.println("destruido "+ this.color + " en: "+fila+","+columna );
	        color.set_color(Color.TRANSPARENTE);
	        cargarImagenesRepresentativas(ruta);
	        entidadG.notificarse_explosion();
	}

	public void explosionAdyacente() {
		System.out.println("destruido "+ this.color + " en: "+fila+","+columna );
		color.set_color(Color.TRANSPARENTE);
	        cargarImagenesRepresentativas(ruta);
	        entidadG.notificarse_explosion();
	}

	public int get_score()
	{
		return color.get_score();
	}
}