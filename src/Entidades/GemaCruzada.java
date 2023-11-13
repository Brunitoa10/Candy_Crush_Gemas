package Entidades;

import EstrategiaDetonaciones.EstategiaDetonacion;
import EstrategiaDetonaciones.EstrategiaDetonacionGemaCruz;
import Logica.Color;
import Tablero.Tablero;
import Tablero.TableroNotificable;


public class GemaCruzada extends Gema {

    public GemaCruzada(TableroNotificable tablero,int f, int c, Color col, boolean visible)
	{
	  super(tablero,f, c, col, "/assets/gemas/original/gema_cruzada/"+col, visible);
	}
	
	//Metodos Particulares
	public void detonar(Tablero tablero)
    {
	 System.out.println("Cruzadaaaaaaaaaaaaaaa");
	 EstategiaDetonacion estrategia= new EstrategiaDetonacionGemaCruz();
   	 estrategia.detonar(this,tablero);
    }
		
	public boolean es_posible_intercambiar(Entidad entidad) 
	{
     return entidad.puede_recibir(this);
	}
	
	public int get_score() {
	 return  100;
  	}
		
	public void explosionAdyacente()
	{}

	public boolean puede_recibir(Hielo hielo) {
		return hielo.get_gema_interna().puede_recibir(this);
	}
	
	public void intercambiar(Entidad entidad) {
		entidad.intercambiar_con(this);
	}
	//Metodos redefinidos de la interfaz matcheable
	public boolean machea(Entidad e) 
	{
	 System.out.println("Gema cruzada matchea(Entidad) :: "+e.get_color()+","+get_color());
	 return e.aplica_match_con(this);
	}
	
	public boolean se_produce_match_con(Entidad e) {
	 return e.aplica_match_con(this);
	}

	public boolean aplica_match_con(GemaNormal c) {
	 return c.get_color()==this.get_color();
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
