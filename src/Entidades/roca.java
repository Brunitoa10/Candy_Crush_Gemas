package Entidades;

public class roca extends obstaculo{

	public roca(int f, int c, String ri, color col) {
		super(f, c, ri,col);
		// TODO Auto-generated constructor stub
	}

	public boolean intercambiarPosicion(int f, int c)
	{
		return false;
	}

	@Override
	public void desenfocar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean esPosibleIntercambiar(Entidad e) {
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

	@Override
	public String get_imagen_representativa() {
		// TODO Auto-generated method stub
		return null;
	}

}
