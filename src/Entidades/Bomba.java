package Entidades;

import Logica.*;
import Tablero.*;
import EstrategiaDetonaciones.*;

public class Bomba extends Obstaculo implements EntidadNotificable {
   protected int tiempo= tiempoInicial;
   protected NotificadorDeEntidadesConTiempo notificador;
   protected static final int tiempoInicial = 30;
   protected Logica logica;


   public Bomba(TableroNotificable tablero,int f, int c, Color col, boolean visible, NotificadorDeEntidadesConTiempo n,Logica l){
    super(tablero,f, c, "/assets/obstaculo/bomba/", col, visible); 
    cargarImagenesRepresentativas(rutadeLaImagen);
    notificador = n;
    logica = l;
    l.suscribirBombaATimer(this);
   }

    public void detonar(Tablero t) {
        notificador.desubscribirse(this);
        EstategiaDetonacion estrategiadeDetonacion= new EstrategiaDetonacionBomba();
        estrategiadeDetonacion.detonar(this, t);
    }

    public void finalizarJuegoPorExplosionDeBomba(){
        notificador.desubscribirse(this);
        logica.notificarDerrotaPorVidas();
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

    public void cargarImagenesRepresentativas(String ri){//"/assets/obstaculo/bomba/"
        imagenes = new String [4];
		imagenes[0] = ri +tiempo +"/"+tiempo+".png";
		imagenes[1] = ri +tiempo +"/"+tiempo+"-cursor.png";
		imagenes[2] = ri + "detonado.gif";
		imagenes[3] = ri + "enfocado-detonado.gif";
        
    }

    public void notificar() {
        tiempo--;
        cargarImagenesRepresentativas(rutadeLaImagen);
        if(enfocada){
            enfocar();}
        else {
            desenfocar(); // para que se actualice con el sprite adecuado
        }
        if(tiempo <= 0){
            finalizarJuegoPorExplosionDeBomba();
        }
    }

    public int getTiempo() {
        return tiempo;
    }

}
