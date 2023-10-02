package Entidades;

public abstract class gemaRayada extends gema{
 protected int dir; //1=horizontal 0=vertical
	public gemaRayada(int f, int c, String ri,int dir) {
		super(f, c, ri);
		direccion=dir;
	}

public int obtenerDireccion()

{
	return direccion;
}

}
