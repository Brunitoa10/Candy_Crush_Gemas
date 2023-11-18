package Entidades;

import Logica.Color;
import Tablero.Tablero;
import Tablero.TableroNotificable;

public class Hielo extends Obstaculo {
   protected Entidad caramelo_interno;

	public Hielo(TableroNotificable tablero,int f, int c, Color col, boolean visible, Entidad ent) {
		super(tablero,f, c, "/assets/obstaculo/gema_normal/", col, visible);
		caramelo_interno=ent;
		cargarImagenesRepresentativas(rutadeLaImagen);
	}
	
	public void detonar(Tablero  t) {
		    System.out.println("destruido "+ this.color + " en: "+fila+","+columna );
		    color.set_color(Color.TRANSPARENTE);
		    cargarImagenesRepresentativas(rutadeLaImagen);
	        entidadGrafica.notificarse_detonacion();
	}

    public void set_gema_interna(Entidad e)
	{
    	caramelo_interno=e;
	}

	public int get_score()
	{
		return color.get_score() + caramelo_interno.get_score();
	}

	public boolean esAfectadaPorExplosionAdyacente()
	{
		return false;
	}

	public Entidad get_gema_interna() {
		return caramelo_interno;
	}

	public void intercambiar_con(Hielo hielo) {
		intercambiar_hielo_y_hielo(this, hielo);
	 }
}