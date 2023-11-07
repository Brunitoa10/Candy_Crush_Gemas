package Entidades;

import Logica.Color;
import Tablero.TableroNotificable;

public abstract class Gema extends Entidad{

	public Gema(TableroNotificable tablero,int f, int c, Color col, String ri, boolean visible) {
		super(tablero,f, c, ri,col, visible);
	}

	@Override
	public boolean aplica_match_con(GemaNormal c) {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean aplica_match_con(GemaRayada p) {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean aplica_match_con(GemaEnvuelta p) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean aplica_match_con(GemaCruzada p) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean aplica_match_con(Roca r) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean puede_recibir(GemaNormal gm) {
		return true;
	}

	public boolean puede_recibir(Roca r) {
		return false;
	}

	public boolean puede_recibir(GemaEnvuelta ge) {
		return true;
	}

	public boolean puede_recibir(GemaRayada gr) {
		return true;
	}

	public boolean puede_recibir(GemaCruzada gc) {
		return true;
	}

	public boolean esAfectadaPorExplosionAdyacente()
	{
		return false;
	}
}