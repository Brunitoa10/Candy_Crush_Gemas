package Entidades;

import Logica.Color;
import Tablero.Tablero;

public class Hielo extends Obstaculo {

	public Hielo(int f, int c, Color col, boolean visible) {
		super(f, c, "/assets/obstaculo/gema_normal/", col, visible);
	}
	
	public void detonar(Tablero  t) {
		    System.out.println("destruido "+ this.color + " en: "+fila+","+columna );
		    color.set_color(Color.TRANSPARENTE);
		    cargarImagenesRepresentativas(ruta);
	        entidadG.notificarse_detonacion();
	}

	public void explosionAdyacente()
	{}

	public int get_score()
	{
		return color.get_score();
	}
}