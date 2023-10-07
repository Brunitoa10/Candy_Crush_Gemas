package Entidades;

public class Roca extends Obstaculo{

	public Roca(int f, int c) {
		super(f, c, "/assets/gemas/gema_normal/", 7);
	}

	public boolean puedeRecibir(GemaNormal g) {
		return false;
	}

	public boolean puedeRecibir(Roca r) {
		return false;
	}

	public boolean puedeRecibir(GemaEnvuelta ge) {
		return false;
	}

	public boolean puedeRecibir(GemaRayada gr) {
		return false;
	}

	@Override
	public String getImagenRep() {
		int indice = 0;
		indice += (enfocada ? 1 : 0);
		return imagenes[indice];
	}

	public boolean esPosibleIntercambiar(Entidad e) {
		return false;
	}

}