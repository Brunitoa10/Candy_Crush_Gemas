package Entidades;

import EstrategiaDetonaciones.EstrategiaDetonacionGemaRayadaHorizontal;
import EstrategiaDetonaciones.EstrategiaDetonacionGemaRayadaVertical;
import Logica.Color;
import Tablero.Tablero;
import Tablero.TableroNotificable;

public class GemaRayada extends Gema{
	
	private int direccion;
	public static final int HORIZONTAL = 5;
	public static final int VERTICAL = 1;

	public GemaRayada(TableroNotificable tablero,int f, int c, Color col, int direccion, boolean visible) {
		super(tablero,f, c,col, "/assets/gemas/gema_rayada/" + direccion, visible);
		this.direccion = direccion;
	}
	
	public int getDireccion(){
		return direccion;
	}

	@Override
	public void detonar(Tablero tablero) {
		System.out.println("direccion  " + direccion);
		
       if(direccion==HORIZONTAL){ 
    	 System.out.println("Horizontaaaaaaaaallllllll");
    	 estrategiaDetonacion = new EstrategiaDetonacionGemaRayadaHorizontal();
   		 estrategiaDetonacion.detonar(this,tablero);
	   }else{
		   System.out.println("Verticaaaaaaaaallllllll");
		   estrategiaDetonacion = new EstrategiaDetonacionGemaRayadaVertical();
		   estrategiaDetonacion.detonar(this, tablero);
	   } 
	}

	public boolean es_posible_intercambiar(Entidad e) {
		return e.puede_recibir(this);
	}
	
	
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

	@Override
	public boolean se_produce_match_con(Entidad e) {
		return e.aplica_match_con(this);
	}

	@Override
	public void intercambiar(Entidad entidad) {
		entidad.intercambiar_con(this);
	}

	@Override
	public void intercambiar_con(GemaNormal g) {
		intercambiar_entidad_y_entidad(this, g);
	}

	@Override
	public void intercambiar_con(GemaRayada g) {
		intercambiar_entidad_y_entidad(this, g);
	}

	@Override
	public void intercambiar_con(GemaEnvuelta g) {
		intercambiar_entidad_y_entidad(this, g);
	}

	@Override
	public void intercambiar_con(GemaCruzada g) {
		intercambiar_entidad_y_entidad(this, g);
	}

	@Override
	public void intercambiar_con(Roca r) {
		intercambiar_entidad_y_entidad(this, r);
	}

	@Override
	public void intercambiar_con(Hielo h) {
		intercambiar_entidad_y_entidad(this, h);
	}
}
