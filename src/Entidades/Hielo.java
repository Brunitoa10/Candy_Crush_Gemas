package Entidades;

import Logica.Color;
import Tablero.Tablero;
import Tablero.TableroNotificable;

public class Hielo extends Obstaculo {
   protected Entidad caramelo_interno;

	public Hielo(TableroNotificable tablero,int f, int c, Color col, boolean visible, Entidad ent) {
		super(tablero,f, c, "/assets/obstaculo/gema_normal/", col, visible);
		caramelo_interno=ent;
	}
	
	public void detonar(Tablero  t) {
		    System.out.println("destruido "+ this.color + " en: "+fila+","+columna );
		    color.set_color(Color.TRANSPARENTE);
		    cargarImagenesRepresentativas(rutadeLaImagen);
	        entidadGrafica.notificarse_detonacion();
	}

    public void set_caramelo_interno(Entidad e)
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

	public Entidad get_caramelo_interno() {
		return caramelo_interno;
	}

	@Override
	public boolean puede_recibir(Hielo h) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean puede_recibir(Roca r) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean puede_recibir(Bomba b) {
		// TODO Auto-generated method stub
		return false;
	}
}