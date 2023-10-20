package Entidades;

import Logica.Color;

public class Hielo extends Obstaculo {

	public Hielo(int f, int c, int col) {
		super(f, c, "/assets/obstaculo/gema_normal/", col);
	}
	
	public void romper() {
		    System.out.println("destruido "+ this.color + " en: "+fila+","+columna );
		    color = Color.TRANSPARENTE;
		    cargarImagenesRepresentativas(ruta);
	        entidadG.notificarse_explosion();
	}

	public void explosionAdyacente()
	{}
}