package Entidades;

import Logica.Color;
import Tablero.Tablero;


public class GemaCruzada extends Gema {

    public GemaCruzada(int f, int c, int col)
     {
			super(f, c, col, "/assets/gemas/gema_cruzada/");
		}
		
		public void detonar(Tablero tablero)
        {
            int topecolumna=tablero.getFila();
            int topefila=tablero.getColumna();

            for(int i=0;i<topefila;i++)
		    { 
			    if(i!=columna && tablero.get_entidad(fila,i).get_color()!=Color.TRANSPARENTE)
			    {
                 tablero.get_entidad(fila,i).detonar(tablero);
			    }
		    }
            for(int j=0;j<topecolumna;j++)
		    {
                if(j!=fila && tablero.get_entidad(j,columna).get_color()!=Color.TRANSPARENTE)
			    {
                 tablero.get_entidad(j,columna).detonar(tablero);
			    }
		    }

                 System.out.println("destruido gema Cruzada "+ this.color + " en: " + fila + columna);
	             color = Color.TRANSPARENTE;
				 cargarImagenesRepresentativas(ruta);
	  			 entidadG.notificarse_explosion();
        }
		
		public boolean es_posible_intercambiar(Entidad entidad) {
		    return entidad.puede_recibir(this);
		}
		
		public boolean puede_recibir(GemaNormal c) {
			return true;
		}
		
		public boolean machea(Entidad e) {
			System.out.println("Gema cruzada matchea(Entidad) :: "+e.get_color()+","+get_color());
			return e.match_con(this);
		}
		
		@Override
		public boolean match_con(GemaNormal c) {
			return true;
		}
		
		public int get_score()
		{
			return 100;
		}
		public void explosionAdyacente()
		{}

}
