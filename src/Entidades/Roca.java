package Entidades;

import Logica.Color;
import Tablero.Tablero;

public class Roca extends Obstaculo{

	public Roca(int f, int c) {
		super(f, c, "/assets/obstaculo/", 7);
	}

	public void romper(Tablero t) {
		    System.out.println("destruido "+ this.color + " en: "+fila+","+columna );
	        color = Color.TRANSPARENTE;
	        cargarImagenesRepresentativas(ruta);
	        entidadG.notificarse_explosion();
	}

	public void explosionAdyacente() {
		System.out.println("destruido "+ this.color + " en: "+fila+","+columna );
	        color = Color.TRANSPARENTE;
	        cargarImagenesRepresentativas(ruta);
	        entidadG.notificarse_explosion();
	}
}