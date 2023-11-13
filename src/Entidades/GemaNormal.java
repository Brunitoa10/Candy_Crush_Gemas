package Entidades;

import Tablero.Tablero;
import Tablero.TableroNotificable;
import EstrategiaDetonaciones.*;
import Logica.*;

public class GemaNormal extends Gema{
		
	public GemaNormal(TableroNotificable tablero,int f, int c, Color col, boolean visible) {
	 super(tablero,f, c, col, "/assets/gemas/original/gema_normal/", visible);
	}
	
	//Metodos particulares
	public boolean puede_recibir(Hielo hielo) {
		return hielo.get_gema_interna().puede_recibir(this);
	}
	
	public void intercambiar(Entidad entidad) {
		entidad.intercambiar_con(this);
	}
	
	public void detonar(Tablero tablero) {
	  System.out.println("Normaaaaal");
	 EstategiaDetonacion estrategia= new EstrategiaDetonacionGemaNormal();
   	 estrategia.detonar(this,tablero);
	}
	
	//Metodos de interfaces
	public boolean es_posible_intercambiar(Entidad entidad) {
     return entidad.puede_recibir(this);
	}

	public boolean se_produce_match_con(Entidad e) {
	 return e.aplica_match_con(this);
	}	
}