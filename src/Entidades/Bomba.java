package Entidades;

import Logica.Color;
import Tablero.Tablero;

public class Bomba extends Obstaculo {
   protected int tiempo;

   public Bomba(int f, int c, String ri, Color col, boolean visible, int segundos)
   {
    super(f, c, ri, col, visible);
    tiempo=segundos;
   }

    public void detonar(Tablero t) {
        System.out.println("destruido "+ this.color + " en: "+fila+","+columna );
		    color.set_color(Color.TRANSPARENTE);
	        cargarImagenesRepresentativas(ruta);
	        entidadG.notificarse_detonacion();
    }

    public void explosionAdyacente() {
        System.out.println("destruido "+ this.color + " en: "+fila+","+columna );
		color.set_color(Color.TRANSPARENTE);
	        cargarImagenesRepresentativas(ruta);
	        entidadG.notificarse_detonacion();
    }

    public int get_score() {
        return color.get_score();
    }
    
}
