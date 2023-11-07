package Entidades;

import Logica.Color;
import Tablero.Tablero;
import Tablero.TableroNotificable;

public class GemaEnvuelta extends Gema{

	public GemaEnvuelta(TableroNotificable tablero,int f, int c, Color col, boolean visible) {
		super(tablero,f, c, col, "/assets/gemas/gema_envuelta/", visible);
	}

	
	@Override
	public void detonar(Tablero tablero) {
		int f=fila;
		int c=columna;
		int topeFila=tablero.getFila();
		int topeColumna=tablero.getColumna();

		if(f!=0)
		{
			f=fila-1;
		}

		if(c!=0)
		{
			c=columna-1;
		}

        if(topeFila-1!=f)
		{
          topeFila=fila+1;		
		}

		if(topeColumna-1!=c)
		{
		   topeColumna=columna+1;
		}

		for(int i= f; i<topeFila; i++){
		  	for(int j = c; j<topeFila; j++){
				if(i==fila && j==columna)
				{
						System.out.println("destruido gema envuelta "+ this.color + " en: "+fila+","+columna );
						color.set_color(Color.TRANSPARENTE);
						cargarImagenesRepresentativas(ruta);
						entidadG.notificarse_detonacion();
						//tablero.caida(this);
				}
				else
				{
					if(tablero.get_entidad(i, columna).get_color()!=0)
					{
						tablero.get_entidad(i, j).detonar(tablero);
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
		return e.aplica_match_con(this);
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
