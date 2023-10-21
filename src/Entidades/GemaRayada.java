package Entidades;

import Logica.Color;
import Tablero.Tablero;

public class GemaRayada extends Gema{
	
	//direccion en la que la gema explotara
	//en caso de cambiar los valores de horizontal y vertical, adaptar el nombre de los assets en /assets/gema_rayada/
	private int d;
	public static final int HORIZONTAL = 5; //MODIFICADO POR BRUNO
	public static final int VERTICAL = 1;

	public GemaRayada(int f, int c, int col, int direccion) {
		super(f, c,col, "/assets/gemas/gema_rayada/" + direccion);
		d = direccion;
	}
	
	public int getDireccion(){
		return d;
	}

	@Override
	public boolean es_posible_intercambiar(Entidad e) {
		// TODO Auto-generated method stub
		return e.puede_recibir(this);
	}

	@Override
	public boolean puede_recibir(GemaNormal c) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean puede_recibir(Hielo g) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean puede_recibir(GemaRayada p) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean puede_recibir(GemaEnvuelta p) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean machea(Entidad e) {
		// TODO Auto-generated method stub
		return e.match_con(this);
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

		/*public void romper(Tablero miTablero) {
		System.out.println("direccion  " + d);
		int f=fila;
		int c=columna;
        int tope=0;
       if(d==5) //es Horizontal
	   {
          tope=miTablero.getColumna();
		  for(int i=0;i<tope;i++)
		  {
			
            if(i==c)
			{
              System.out.println("destruido gema rayada Horizontal"+ this.color + " en: "+f+","+c );
	          color = Color.TRANSPARENTE;
	          cargarImagenesRepresentativas(ruta);
	          entidadG.notificarse_explosion();
			}
			else
			{
				if(miTablero.getEntidad(fila,i).obtenerColor()!=0)
				{
					miTablero.getEntidad(fila, i).romper(miTablero);
				}	
			}
		  }
	   }
	   else //es Vertical
	   {
          tope=miTablero.getFila();
		  for(int i=0;i<tope;i++)
		  {
            if(i==f)
			{
                 System.out.println("destruido gema rayada Vertical "+ this.color + " en: "+f+","+c );
	             color = Color.TRANSPARENTE;
				 cargarImagenesRepresentativas(ruta);
	  			 entidadG.notificarse_explosion();;
			}
			else
			{
				if(miTablero.getEntidad(i, columna).obtenerColor()!=0)
				{
			  		miTablero.getEntidad(i, columna).romper(miTablero);	
			    }
		    }
		  }
	   }
	}*/


}
