package Entidades;

import Logica.Color;
import Tablero.Tablero;

public class GemaNormal extends Gema{
		
		public GemaNormal(int f, int c, int col) {
			super(f, c, col, "/assets/gemas/gema_normal/");
		}


		public void romper(Tablero t) {
			//System.out.println("destruido "+this.getClass().getName()+ " "+ this.color + " en: "+fila+","+columna );
	   		color = Color.TRANSPARENTE;
	   		cargarImagenesRepresentativas(ruta);
	  		entidadG.notificarse_explosion();
		}

		public void explosionAdyacente()
		{}
}