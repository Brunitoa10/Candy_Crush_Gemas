package Entidades;

import Logica.Color;

public abstract class Gema extends Entidad{

	public Gema(int f, int c, Color col, String ri, boolean visible) {
		super(f, c, ri,col, visible);
	}

	public boolean match_con(GemaNormal c) {
	  return c.get_color()==this.color.get_color();
	}

	public boolean match_con(GemaEnvuelta c) {
	  return c.get_color()==this.color.get_color();
	}

	public boolean match_con(GemaRayada c) {
	  return c.get_color()==this.color.get_color();
	}

	public boolean match_con(GemaCruzada c) {
	  return c.get_color()==this.color.get_color();
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

	public boolean puede_recibir(GemaCruzada gc) {
		return true;
	}
	
	public abstract void explosionAdyacente();
}