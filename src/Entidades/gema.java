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

	public color getColor() {
       return color;
	}

	@Override
	public boolean esPosibleIntercambiar(Entidad e) {
		return e.puedeRecibir(this);
	}

	@Override
	public boolean puedeRecibir(gemaNormal gm) {
		return true;
	}

	@Override
	public boolean puedeRecibir(roca r) {
		return false;
	}

	@Override
	public boolean puedeRecibir(gemaEnvuelta ge) {
		return true;
	}

	@Override
	public boolean puedeRecibir(gemaRayada gr) {
		return true;
	}

	@Override
	public void intercambiarPosicion(int f, int c) {
		fila=f;
		columna=c;
		entidadG.notificarse_intercambio_posicion();
	}

	@Override
	public String get_imagen_representativa() {
		return imagen;
	}

    private boolean tieneJuego(int x, int y)
	{
      for(int i=1,i<3,i++)
	  {
		if()
	  }
	}
}
