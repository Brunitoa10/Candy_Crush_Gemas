package Entidades;

import Logica.Color;
import Tablero.NotificadorDeEntidadesConTiempo;
import Tablero.Tablero;
import Tablero.TableroNotificable;

public class Bomba extends Obstaculo implements EntidadNotificable {
   protected int tiempo;
   protected NotificadorDeEntidadesConTiempo notificador;

   public Bomba(TableroNotificable tablero,int f, int c, Color col, boolean visible, NotificadorDeEntidadesConTiempo n){
    
    super(tablero,f, c, "/assets/obstaculo/bomba/", col, visible); 
    System.out.println(rutadeLaImagen);
    tiempo=10;
    notificador = n;
    notificador.subscribirse(this);
   }

    public void detonar(Tablero t) {
        System.out.println("destruido "+ this.color + " en: "+fila+","+columna );
		    color.set_color(Color.TRANSPARENTE);
	        cargarImagenesRepresentativas(rutadeLaImagen);
	        entidadGrafica.notificarse_detonacion();
    }

    public void explosionAdyacente() {
        System.out.println("destruido "+ this.color + " en: "+fila+","+columna );
        notificador.desubscribirse(this);
		color.set_color(Color.TRANSPARENTE);
	        cargarImagenesRepresentativas(rutadeLaImagen);
	        entidadGrafica.notificarse_detonacion();
        
    }

    public void finalizarJuegoPorExplosionDeBomba(){

    }

    public int get_score() {
        return color.get_score();
    }

	public boolean esAfectadaPorExplosionAdyacente()
	{
		return true;
	}

	@Override
	public boolean se_produce_match_con(Entidad e) {
	 return false;
	}

    public void notificar() {
        System.out.println("NOTIFICAR!!!!!!!!!!!!!!!!");
        tiempo--;
        rutadeLaImagen = "/assets/obstaculo/bomba/"+tiempo+".png";
        if(tiempo <= -1){//-1 por el efecto dramatico de que la bomba llegue a 0
            finalizarJuegoPorExplosionDeBomba();
        }
    }

}
