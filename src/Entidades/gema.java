package Entidades;
import Logica.color;
import GUI.entidadGrafica;

public class gema extends entidad{

	public gema(int f, int c, color col, String ri) {
		super(f, c, ri,color);
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

}