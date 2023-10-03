package Entidades;
import Logica.Color;

public class Roca extends Obstaculo{

	public Roca(int f, int c, int col, String ri) {
		super(f, c, "/assets/gema_normal/", col);
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
	
	protected void cargar_imagenes_representativas(String ri) {
		imagenes = new String [2];
		imagenes[0] = ri + color +".png";
		imagenes[1] = ri + color +"-resaltado.png";
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