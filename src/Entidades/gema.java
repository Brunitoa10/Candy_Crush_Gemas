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

<<<<<<< HEAD
	public color getColor() {
       return color;
	}
	
	public String getImagenRep() {
		int indice = 0;
		indice += (enfocada ? 1 : 0);
		return imagenes[indice];
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

/*    private boolean tieneJuego(int x, int y)
	{
      for(int i=1,i<3,i++)
	  {
		if()
	  }
      
      
	}
*/    
    private void cargarImagenesRepresentativas(String path_img) {
    	imagenes = new String [2];
    	imagenes[0] = path_img + color +".png";
    	imagenes[1] = path_img + color +"-resaltado.png";
    }
=======
>>>>>>> d71e29f09cab0098a65cff60b9bcc80adaa5b96a
}