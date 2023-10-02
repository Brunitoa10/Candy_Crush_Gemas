package Entidades;
import Logica.color;
import GUI.entidadGrafica;

public class gema extends entidad{

	protected color color;
	
	public gema(int f, int c, int col, String ri) {
		super(f, c, ri);
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
	public boolean esPosibleIntercambiar(entidad e) {
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
    
    private void cargarImagenesRepresentativas(String path_img) {
    	imagenes = new String [2];
    	imagenes[0] = path_img + color +".png";
    	imagenes[1] = path_img + color +"-resaltado.png";
    }
}