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
    
    private void cargarImagenesRepresentativas(String path_img) {
    	imagenes = new String [2];
    	imagenes[0] = path_img + color +".png";
    	imagenes[1] = path_img + color +"-resaltado.png";
    }
}