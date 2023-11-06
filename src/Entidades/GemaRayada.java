package Entidades;

import EstrategiaDetonaciones.EstrategiaDetonacionGemaRayadaHorizontal;
import EstrategiaDetonaciones.EstrategiaDetonacionGemaRayadaVertical;
import Logica.Color;
import Tablero.Tablero;

public class GemaRayada extends Gema{
	
	//direccion en la que la gema explotara
	//en caso de cambiar los valores de horizontal y vertical, adaptar el nombre de los assets en /assets/gema_rayada/
	private int direccion;
	public static final int HORIZONTAL = 5; //MODIFICADO POR BRUNO
	public static final int VERTICAL = 1;

	public GemaRayada(int f, int c, Color col, int direccion, boolean visible) {
		super(f, c,col, "/assets/gemas/gema_rayada/" + direccion, visible);
		this.direccion = direccion;
		
		// Inicializamos la estrategia de detonación según la dirección
       
	}
	
	public int getDireccion(){
		return direccion;
	}

	@Override
	public void detonar(Tablero tablero) {
		System.out.println("direccion  " + direccion);
		/*int f=fila;
		int c=columna;
        int tope=0;*/
       if(direccion==HORIZONTAL){ 
    	 System.out.println("Horizontaaaaaaaaallllllll");
    	 estrategiaDetonacion = new EstrategiaDetonacionGemaRayadaHorizontal();
   		 estrategiaDetonacion.detonar(this,tablero);
	   }else{
		   System.out.println("Verticaaaaaaaaallllllll");
		   estrategiaDetonacion = new EstrategiaDetonacionGemaRayadaVertical();
		   estrategiaDetonacion.detonar(this, tablero);
	   }// tope=tablero.getColumna();
		 /* for(int i=0;i<tope;i++)
		  {
			
            if(i==c)
			{
              System.out.println("destruido gema rayada Horizontal"+ this.color + " en: "+f+","+c );
	          color.set_color(Color.TRANSPARENTE);
	          cargarImagenesRepresentativas(ruta);
	          entidadG.notificarse_explosion();
			 // tablero.caida(this);
			}
			else
			{
				if(tablero.get_entidad(fila,i).get_color()!=0)
				{
					tablero.get_entidad(fila, i).detonar(tablero);
				}	
			}
		  }
	   }
	   else //es Vertical
	   {
          tope=tablero.getFila();
		  for(int i=0;i<tope;i++)
		  {
            if(i==f)
			{
                 System.out.println("destruido gema rayada Vertical "+ this.color + " en: "+f+","+c );
	             color.set_color(Color.TRANSPARENTE);
				 cargarImagenesRepresentativas(ruta);
	  			 entidadG.notificarse_explosion();
			}
			else
			{
				if(tablero.get_entidad(i, columna).get_color()!=0)
				{
					tablero.get_entidad(i, columna).detonar(tablero);	
			    }
		    }
		  }*/
	  // }
	}

	public boolean es_posible_intercambiar(Entidad e) {
		return e.puede_recibir(this);
	}
	
	public boolean machea(Entidad e) 
	{
		System.out.println("Gema Rayada matchea(Entidad) :: "+e.get_color()+","+get_color());
		return e.match_con(this);
	}
    
	public boolean match_con(GemaEnvuelta c) {
		return true;
	  }
  
	  public boolean match_con(GemaRayada c) {
		return true;
	  }
  
	  public boolean match_con(GemaCruzada c) {
		return true;
	  }
	  
	public void explosionAdyacente()
	{}

	public int get_score()
	{
		int score=0;
		if(direccion==HORIZONTAL)
		{
          score=45;
		}
		else
		{
			score=35;
		}
		return score;
	}
}
