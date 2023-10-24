package Entidades;

public abstract class Gema extends Entidad{

	public Gema(int f, int c, int col, String ri) {
		super(f, c, ri,col);
	}

	public int getColor() {
       return color;
	}

	public boolean match_con(GemaNormal c) {
	  return c.get_color()==this.getColor();
	}

	public boolean match_con(GemaEnvuelta c) {
	  return c.get_color()==this.getColor();
	}

	public boolean match_con(GemaRayada c) {
	  return c.get_color()==this.getColor();
	}

	public boolean match_con(Roca r) {
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


	public abstract void explosionAdyacente();
  
	
}