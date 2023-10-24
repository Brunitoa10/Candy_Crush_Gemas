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
	public void detonar(Tablero miTablero) {
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
				if(miTablero.get_entidad(fila,i).get_color()!=0)
				{
					miTablero.get_entidad(fila, i).detonar(miTablero);
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
				if(miTablero.get_entidad(i, columna).get_color()!=0)
				{
			  		miTablero.get_entidad(i, columna).detonar(miTablero);	
			    }
		    }
		  }
	   }
	}

	public boolean es_posible_intercambiar(Entidad e) {
		return e.puede_recibir(this);
	}
	
	public boolean machea(Entidad e) 
	{
		return e.match_con(this);
	}

	public void explosionAdyacente()
	{}
}
