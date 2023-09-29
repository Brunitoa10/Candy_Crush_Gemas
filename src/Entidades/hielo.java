package Entidades;

public class hielo extends obstaculo {

	public hielo(int f, int c, String ri) {
		super(f, c, ri);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getImagenRep() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean enfocar() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void desenfocar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean esPosibleIntercambiar(entidad e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean puedeRecibir(gemaNormal g) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean puedeRecibir(roca r) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean puedeRecibir(gemaEnvuelta ge) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean puedeRecibir(gemaRayada gr) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void intercambiarPosicion(int f, int c) {
		// TODO Auto-generated method stub
		
	}

}
