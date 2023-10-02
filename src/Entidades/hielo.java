package Entidades;

import Logica.color;

public class hielo extends obstaculo {

	public hielo(int f, int c, color col) {
		super(f, c, col, "/assets/gema_normal/");
	}
     
	 public void intercambiarPosicion(int f, int c){

	}

	@Override
	public void desenfocar() {
		// TODO Auto-generated method stub
		
	}

	
	public boolean esPosibleIntercambiar(entidad e) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean puedeRecibir(gemaNormal g) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean puedeRecibir(roca r) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean puedeRecibir(gemaEnvuelta ge) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean puedeRecibir(gemaRayada gr) {
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
		imagenes[0] = ri + color.HIELO +".png";
		imagenes[1] = ri + color.HIELO +"-resaltado.png";
	}

}