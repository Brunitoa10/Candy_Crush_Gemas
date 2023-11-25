package Entidades;


import Logica.*;
import Tablero.TableroNotificable;

public abstract class Obstaculo extends Entidad{

	public Obstaculo(TableroNotificable tablero,int f, int c, String ri, Color col, boolean visible) {
		super(tablero,f, c, ri,col, visible);
	}

	//Interfaz de intercambiable

	@Override
	public boolean puede_recibir(GemaNormal c) {
		return true;
	}

	@Override
	public boolean puede_recibir(GemaRayada p) {
		return true;
	}

	@Override
	public boolean puede_recibir(GemaEnvuelta p) {
		return true;
	}

	@Override
	public boolean puede_recibir(GemaCruzada p) {
		return true;
	}

	public boolean puede_recibir(Hielo p) {
		return true;
	}

	public boolean puede_recibir(Bomba p) {
		return false;
	}
	
	public boolean puede_recibir(Roca p) {
		return false;
	}
	
	//interfaz de matcheable
	public boolean se_produce_match_con(Entidad e) {
		return false;
	}

	public boolean aplica_match_con(GemaNormal c) {
		return false;
	  }
  
	  public boolean aplica_match_con(GemaEnvuelta c) {
		return false;
	  }
  
	  public boolean aplica_match_con(GemaRayada c) {
		return false;
	  }
  
	   public boolean aplica_match_con(GemaCruzada c) {
		return false;
	  }

	  public boolean aplica_match_con(Roca r) {
		return false;
	  }

	  public void intercambiar(Entidad entidad) {
		entidad.intercambiar(this);
	}

	//Interfaz de intercambiable

	public void intercambiar_con(GemaNormal gemaNormal) {
	  

	}

	public void intercambiar_con(GemaRayada gemaRayada) {
	  
	}

	public void intercambiar_con(GemaEnvuelta gemaEnvuelta) {
	    
	}

	public void intercambiar_con(GemaCruzada gemaCruzada) {
	    
	}

	public void intercambiar_con(Roca roca) {
	
	}

	public void intercambiar_con(Bomba bomba) {
	
	}

	public void intercambiar_con(Hielo hielo) {
	  
	}

	public Entidad get_gema_interna()
	{ 
		return this;
	}
}
