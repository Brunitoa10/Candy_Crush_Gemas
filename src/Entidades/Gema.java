package Entidades;

public abstract class Gema extends Entidad{

	public Gema(int f, int c, int col, String ri) {
		super(f, c, ri,col);
	}

	public int getColor() {
       return color;
	}

	public boolean esPosibleIntercambiar(Entidad e) {
		return e.puedeRecibir(this);
	}
	
	public String getImagenRep() {
		int indice = 0;
		indice += (enfocada ? 1 : 0);
		return imagenes[indice];
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

	public void intercambiarPosicion(int f, int c) {
		fila=f;
		columna=c;
		entidadG.notificarse_intercambio_posicion();
	}

	public abstract void romper();

	public abstract void explosionAdyacente();
  
	
}