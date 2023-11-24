package Entidades;

import Tablero.Tablero;
import Tablero.TableroNotificable;
import EstrategiaDetonaciones.*;
import Logica.*;

public class GemaNormal extends Gema{
		
	public GemaNormal(TableroNotificable tablero,int f, int c, Color col, boolean visible,String skin) {
	 super(tablero,f, c, col, "/assets/gemas/"+skin+"/gema_normal/", visible);
	 cargarImagenesRepresentativas(rutadeLaImagen);
	}
	
	//Metodos particulares
	
	public void intercambiar(Entidad entidad) {
		entidad.intercambiar_con(this);
	}
	
	public void detonar(Tablero tablero) {
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
	
	public boolean aplica_match_con(GemaCruzada c) {
		return c.get_color() == this.get_color();
	}
}