package Entidades;

import Tablero.Tablero;

public abstract class Obstaculo extends Entidad{

	public Obstaculo(int f, int c, String ri, int col) {
		super(f, c, ri,col);
	}

	public abstract void detonar(Tablero t);

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
	public boolean machea(Entidad e) {
		return false;
	}

	public boolean match_con(GemaNormal c) {
		return false;
	  }
  
	  public boolean match_con(GemaEnvuelta c) {
		return false;
	  }
  
	  public boolean match_con(GemaRayada c) {
		return false;
	  }
  
	  public boolean match_con(Roca r) {
		return false;
	  }
}
