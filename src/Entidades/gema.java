package Entidades;
import Logica.color;
import GUI.EntidadGrafica;

public class gema extends Entidad{

	protected color color;
	
	public gema(int f, int c, String ri) {
		super(f, c, ri);
	}

	@Override
	public String getImagenRep() {
		return imagen;
	}

	@Override
	public boolean enfocar() {
	enfocada=true;
	entidadG.notificarse_cambio_estado();
	return enfocada;
	}

	@Override
	public void desenfocar() {
		entidadG.notificarse_cambio_estado();
		enfocada=false;
		
	}

	@Override
	public boolean esPosibleIntercambiar(Entidad e) {
		return e.puedeRecibir(this);
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
		fila=f;
		columna=c;
		entidadG.notificarse_cambio_estado();
	}

	@Override
	public String get_imagen_representativa() {
		// TODO Auto-generated method stub
		return null;
	}


}
