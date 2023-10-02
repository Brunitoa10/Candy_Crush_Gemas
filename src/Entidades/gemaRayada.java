package Entidades;

public abstract class gemaRayada extends gema{
<<<<<<< HEAD
	
	//direccion en la que la gema explotara
	//en caso de cambiar los valores de horizontal y vertical, adaptar el nombre de los assets en /assets/gema_rayada/
	private int d;
	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;

	public gemaRayada(int f, int c, int col, int direccion) {
		super(f, c,col, "/assets/gema_rayada/" + direccion);
	}
	
	public boolean esPosibleIntercambiar(entidad e) {
		return e.puedeRecibir(this);
	}
=======
 protected int dir; //1=horizontal 0=vertical
	public gemaRayada(int f, int c, String ri,int dir) {
		super(f, c, ri);
		direccion=dir;
	}

public int obtenerDireccion()

{
	return direccion;
}

>>>>>>> d71e29f09cab0098a65cff60b9bcc80adaa5b96a
}
