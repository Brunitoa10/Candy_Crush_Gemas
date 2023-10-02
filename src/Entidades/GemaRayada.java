package Entidades;

import Logica.color;
public abstract class GemaRayada extends Gema{
	
	//direccion en la que la gema explotara
	//en caso de cambiar los valores de horizontal y vertical, adaptar el nombre de los assets en /assets/gema_rayada/
	private int d;
	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;

	public GemaRayada(int f, int c, color col, int direccion) {
		super(f, c,col, "/assets/gema_rayada/" + direccion);
	}
	
	/*public boolean esPosibleIntercambiar(entidad e) {
		return e.puedeRecibir(this);
	}*/


	public int getDireccion(){
		return d;
	}

}
