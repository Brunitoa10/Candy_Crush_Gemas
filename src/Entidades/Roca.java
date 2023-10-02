package Entidades;
import Logica.color;

public class Roca extends Obstaculo{

	public Roca(int f, int c, color col, String ri) {
		super(f, c, col, ri);
	}

	public void intercambiarPosicion(int f, int c){

	}

	public void desenfocar() {
		// TODO Auto-generated method stub
		
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
		imagenes[0] = ri + color.ROCA +".png";
		imagenes[1] = ri + color.ROCA +"-resaltado.png";
	}

	@Override
	public String getImagenRep() {
		int indice = 0;
		indice += (enfocada ? 1 : 0);
		return imagenes[indice];
	}

}