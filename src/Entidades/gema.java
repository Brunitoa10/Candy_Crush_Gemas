package Entidades;
import Logica.color;
import GUI.entidadGrafica;

public class gema extends entidad{

	public gema(int f, int c, color col, String ri) {
		super(f, c, ri,col);
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
	
	public String getImagenRep() {
		int indice = 0;
		indice += (enfocada ? 1 : 0);
		return imagenes[indice];
	}


	public boolean puedeRecibir(gemaNormal gm) {
		return true;
	}


	public boolean puedeRecibir(roca r) {
		return false;
	}


	public boolean puedeRecibir(gemaEnvuelta ge) {
		return true;
	}


	public boolean puedeRecibir(gemaRayada gr) {
		return true;
	}

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

}