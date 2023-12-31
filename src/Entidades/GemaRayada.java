package Entidades;

import EstrategiaDetonaciones.EstategiaDetonacion;
import EstrategiaDetonaciones.EstrategiaDetonacionGemaRayadaHorizontal;
import EstrategiaDetonaciones.EstrategiaDetonacionGemaRayadaVertical;
import Logica.Color;
import Tablero.Tablero;
import Tablero.TableroNotificable;

public class GemaRayada extends Gema{
	
	private int direccion;
	public static final int HORIZONTAL = 5;
	public static final int VERTICAL = 1;

	public GemaRayada(TableroNotificable tablero,int f, int c, Color col, int direccion, boolean visible,String skin) {
		super(tablero,f, c,col, "/assets/gemas/"+skin+"/gema_rayada/" + direccion, visible);
		this.direccion = direccion;
		cargarImagenesRepresentativas(rutadeLaImagen);
	}

	public void detonar(Tablero tablero) {
	  	EstategiaDetonacion estrategiaDetonacion;
		 if(direccion==HORIZONTAL){ 
	 	  	 estrategiaDetonacion = new EstrategiaDetonacionGemaRayadaHorizontal();
	   		 estrategiaDetonacion.detonar(this,tablero);
		 }else{
			  estrategiaDetonacion = new EstrategiaDetonacionGemaRayadaVertical();
			  estrategiaDetonacion.detonar(this, tablero);
    	 } 
	}

	public boolean es_posible_intercambiar(Entidad e) {
		return e.puede_recibir(this);
	}
	
	public void intercambiar(Entidad entidad) {
		entidad.intercambiar_con(this);
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

	public boolean se_produce_match_con(Entidad e) {
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
}
