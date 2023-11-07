package Entidades;

import Logica.Color;
import Tablero.Tablero;
import Tablero.TableroNotificable;


public class GemaCruzada extends Gema {

    public GemaCruzada(TableroNotificable tablero,int f, int c, Color col, boolean visible){
			super(tablero,f, c, col, "/assets/gemas/gema_cruzada/", visible);
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
	             color.set_color(Color.TRANSPARENTE);
				 cargarImagenesRepresentativas(ruta);
	  			 entidadG.notificarse_detonacion();
        }
		
		public boolean es_posible_intercambiar(Entidad entidad) {
		    return entidad.puede_recibir(this);
		}
		
		public boolean puede_recibir(GemaNormal c) {
			return true;
		}
		
		public boolean machea(Entidad e) {
			System.out.println("Gema cruzada matchea(Entidad) :: "+e.get_color()+","+get_color());
			return e.aplica_match_con(this);
		}
		
		@Override
		public boolean aplica_match_con(GemaNormal c) {
			return true;
		}
		
		public boolean aplica_match_con(GemaEnvuelta c) {
			return true;
		  }
	  
		  public boolean aplica_match_con(GemaRayada c) {
			return true;
		  }
	  
		  public boolean aplica_match_con(GemaCruzada c) {
			return true;
		  }
		  
		public int get_score()
		{
			return 100;
		}
		
		public void explosionAdyacente()
		{}

		@Override
		public boolean esRoca() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean se_produce_match_con(Entidad e) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void intercambiar(Entidad entidad) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void intercambiar_con(GemaNormal g) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void intercambiar_con(GemaRayada g) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void intercambiar_con(GemaEnvuelta g) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void intercambiar_con(GemaCruzada g) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void intercambiar_con(Roca r) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void intercambiar_con(Hielo h) {
			// TODO Auto-generated method stub
			
		}

}
