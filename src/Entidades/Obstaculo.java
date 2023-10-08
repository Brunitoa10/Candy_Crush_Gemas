package Entidades;

public abstract class Obstaculo extends Entidad{

	public Obstaculo(int f, int c, String ri, int col) {
		super(f, c, ri,col);
	}

	public abstract void romper(Entidad e);
  
}
