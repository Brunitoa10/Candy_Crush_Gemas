package Entidades;

public abstract class Gema extends Entidad{

	public Gema(int f, int c, int col, String ri) {
		super(f, c,col,ri);
	}

	public int getColor() {
       return color;
	}

	public boolean puedeRecibir(GemaNormal gm) {
		return true;
	}


	public boolean puedeRecibir(Roca r) {
		return false;
	}


	public boolean puedeRecibir(GemaEnvuelta ge) {
		return true;
	}


	public boolean puedeRecibir(GemaRayada gr) {
		return true;
	}

	

	
}