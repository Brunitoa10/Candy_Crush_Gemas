package Entidades;

import Logica.Color;

public class GemaNormal extends Gema{
		
		public GemaNormal(int f, int c, int col) {
			super(f, c, col, "/assets/gemas/gema_normal/");
		}


		public void romper() {
			System.out.println("destruido "+ this.color + " en: "+fila+","+columna );
	   		color = Color.TRANSPARENTE;
	   		cargarImagenesRepresentativas(ruta);
	  		entidadG.notificarse_explosion();
		}


}