package Entidades;

public interface intercambiable {
	public boolean esPosibleIntercambiar(entidad e);
	public boolean puedeRecibir(gemaNormal g);
	public boolean puedeRecibir(roca r);
	public boolean puedeRecibir(gemaEnvuelta ge);
	public boolean puedeRecibir(gemaRayada gr);
	public void intercambiarPosicion(int f, int c);
}
