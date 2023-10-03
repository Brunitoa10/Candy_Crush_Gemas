package Entidades;

import Logica.Color;

public class Hielo extends Obstaculo {

	public Hielo(int f, int c, int col) {
		super(f, c, "/assets/gema_normal/", col);
	}
     
	 public void intercambiarPosicion(int f, int c){

	}

	@Override
	public void desenfocar() {
		// TODO Auto-generated method stub
		
	}

	
	public boolean esPosibleIntercambiar(Entidad e) {
		// TODO Auto-generated method stub
		return false;
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

	protected void cargar_imagenes_representativas(String ri) {
		imagenes = new String [2];
		imagenes[0] = ri + color +".png";
		imagenes[1] = ri + color +"-cursor.png";
	}

	@Override
	public boolean esPosibleInrecambiar() {
		return false;
	}

}