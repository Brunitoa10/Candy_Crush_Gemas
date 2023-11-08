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
	
	public boolean puede_recibir(Hielo hielo) {
		return false;
	}

	public void intercambiar(Entidad entidad) {
		// TODO Auto-generated method stub
	}

	
	public void intercambiar_con(GemaNormal g) {
		// TODO Auto-generated method stub
	}

	public void intercambiar_con(GemaRayada g) {
	// TODO Auto-generated method stub
	}

	public void intercambiar_con(GemaEnvuelta g) {
	// TODO Auto-generated method stub	
	}

	public void intercambiar_con(GemaCruzada g) {
	// TODO Auto-generated method stub
	}

	public void intercambiar_con(Roca r) {
	// TODO Auto-generated method stub
	}

	public void intercambiar_con(Hielo h) {
	// TODO Auto-generated method stub
	}

	public void cambiar_posicion(int nf, int nc) {
	// TODO Auto-generated method stub
	}

	//Metodo particular

	public boolean esAfectadaPorExplosionAdyacente()
	{
		return false;
	}
}