package Entidades;

import Logica.Color;

public class Hielo extends Obstaculo {

	public Hielo(int f, int c, int col) {
		super(f, c, "/assets/obstaculo/gema_normal/", col);
	}
	
	public boolean esPosibleIntercambiar(Entidad e) {
		return false;
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

	public void romper() {
		    System.out.println("destruido "+ this.color + " en: "+fila+","+columna );
		    color = Color.TRANSPARENTE;
		    cargarImagenesRepresentativas(ruta);
	        entidadG.notificarse_explosion();
	}

	public void explosionAdyacente()
	{}
}