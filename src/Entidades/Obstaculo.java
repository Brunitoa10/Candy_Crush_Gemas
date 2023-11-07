package Entidades;


import Logica.*;
import Tablero.TableroNotificable;

public abstract class Obstaculo extends Entidad{

	public Obstaculo(TableroNotificable tablero,int f, int c, String ri, Color col, boolean visible) {
		super(tablero,f, c, ri,col, visible);
	}

	//Interfaz de intercambiable
	public boolean esPosibleIntercambiar(Entidad e) {
		return false;
	}
  
	@Override
	public boolean es_posible_intercambiar(Entidad e) {
		return false;
	}

	@Override
	public boolean puede_recibir(GemaNormal c) {
		return false;
	}

	@Override
	public boolean puede_recibir(GemaRayada p) {
		return false;
	}

	@Override
	public boolean puede_recibir(GemaEnvuelta p) {
		return false;
	}

	@Override
	public boolean puede_recibir(GemaCruzada p) {
		return false;
	}

	//interfaz de matcheable
	public boolean se_produce_match_con(Entidad e) {
		return false;
	}

	public boolean aplica_match_con(GemaNormal c) {
		return false;
	  }
  
	  public boolean aplica_match_con(GemaEnvuelta c) {
		return false;
	  }
  
	  public boolean aplica_match_con(GemaRayada c) {
		return false;
	  }
  
	   public boolean aplica_match_con(GemaCruzada c) {
		return false;
	  }

	  public boolean aplica_match_con(Roca r) {
		return false;
	  }

	public void intercambiar(Entidad entidad) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void intercambiar_con(GemaNormal g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void intercambiar_con(GemaRayada g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void intercambiar_con(GemaEnvuelta g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void intercambiar_con(GemaCruzada g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void intercambiar_con(Roca r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void intercambiar_con(Hielo h) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cambiar_posicion(int nf, int nc) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'cambiar_posicion'");
	}
}
