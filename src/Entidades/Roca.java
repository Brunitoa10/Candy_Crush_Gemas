package Entidades;

public class Roca extends Obstaculo{

	public Roca(int f, int c) {
		super(f, c, "/assets/gemas/gema_normal/", 7);
	}

	public void intercambiarPosicion(int f, int c){

	}

	public boolean puedeRecibir(GemaNormal g) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean puedeRecibir(Roca r) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean puedeRecibir(GemaEnvuelta ge) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean puedeRecibir(GemaRayada gr) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getImagenRep() {
		int indice = 0;
		indice += (enfocada ? 1 : 0);
		return imagenes[indice];
	}

	@Override
	public boolean esPosibleInrecambiar() {
		return false;
	}

}