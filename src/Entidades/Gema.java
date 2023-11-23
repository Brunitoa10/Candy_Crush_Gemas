package Entidades;

import Logica.Color;
import Tablero.TableroNotificable;

public abstract class Gema extends Entidad{

	public Gema(TableroNotificable tablero,int f, int c, Color col, String ri, boolean visible) {
		super(tablero,f, c, ri,col, visible);
	}

	//Metodos de la interfaz matcheable

	public boolean aplica_match_con(GemaNormal c) {
		return false;
	}

	public boolean aplica_match_con(GemaRayada p) {
		return false;
	}

	public boolean aplica_match_con(GemaEnvuelta p) {
		return false;
	}

	public boolean aplica_match_con(GemaCruzada p) {
		return false;
	}

	public boolean aplica_match_con(Roca r) {
		return false;
	}

	//Metodos de la interfaz Intercambiable

	public boolean puede_recibir(GemaNormal gemaNormal) {
		return true;
	}

	public boolean puede_recibir(GemaEnvuelta gemaEnvuelta) {
		return true;
	}

	public boolean puede_recibir(GemaRayada gemaRayada) {
		return true;
	}

	public boolean puede_recibir(GemaCruzada gemaCruzada) {
		return true;
	}

	public boolean puede_recibir(Bomba bomba) {
		return false;
	}

	public boolean puede_recibir(Roca roca) {
		return false;
	}
	
	public void intercambiar_con(GemaNormal gemaNormal) {
	   intercambiar_entidad_y_entidad(this, gemaNormal);

	}

	public void intercambiar_con(GemaRayada gemaRayada) {
	   intercambiar_entidad_y_entidad(this, gemaRayada);
	}

	public void intercambiar_con(GemaEnvuelta gemaEnvuelta) {
	    intercambiar_entidad_y_entidad(this, gemaEnvuelta);	
	}

	public void intercambiar_con(GemaCruzada gemaCruzada) {
	    intercambiar_entidad_y_entidad(this, gemaCruzada);
	}

	public void intercambiar_con(Roca roca) {
	
	}

	public void intercambiar_con(Bomba bomba) {
	
	}

	public void intercambiar_con(Hielo hielo) {
	   intercambiar_gema_y_hielo(this, hielo);
	}

	//Metodo particular

	public boolean esAfectadaPorExplosionAdyacente()
	{
		return false;
	}

	public Entidad get_gema_interna()
	{ 
		return this;
	}
}