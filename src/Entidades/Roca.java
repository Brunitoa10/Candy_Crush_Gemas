package Entidades;


public class Roca extends Obstaculo{

	public Roca(int f, int c) {
		super(f, c, "/assets/obstaculo/", 7);
	}

	@Override
	public boolean es_posible_intercambiar(Entidad e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean puede_recibir(GemaNormal c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean puede_recibir(Hielo g) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean puede_recibir(GemaRayada p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean puede_recibir(GemaEnvuelta p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean machea(Entidad e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean match_con(GemaNormal c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean match_con(GemaRayada p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean match_con(GemaEnvuelta p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean match_con(Hielo g) {
		// TODO Auto-generated method stub
		return false;
	}
}