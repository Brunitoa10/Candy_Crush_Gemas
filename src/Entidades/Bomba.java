package Entidades;

import Logica.Color;
import Logica.Logica;
import Tablero.NotificadorDeEntidadesConTiempo;
import Tablero.Tablero;
import Tablero.TableroNotificable;

public class Bomba extends Obstaculo implements EntidadNotificable {
   protected int tiempo= tiempoInicial;
   protected NotificadorDeEntidadesConTiempo notificador;
   protected static final int tiempoInicial = 10;
   protected Logica logica;


   public Bomba(TableroNotificable tablero,int f, int c, Color col, boolean visible, NotificadorDeEntidadesConTiempo n,Logica l){
    super(tablero,f, c, "/assets/obstaculo/bomba/", col, visible); 
    cargarImagenesRepresentativas(rutadeLaImagen);
    System.out.println("CREACION DE BOMBA");
    notificador = n;
    notificador.subscribirse(this);
    logica = l;
    
   }

    public void detonar(Tablero t) {
        System.out.println("destruido "+ this.color + " en: "+fila+","+columna );
		    color.set_color(Color.TRANSPARENTE);
	        cargarImagenesRepresentativas(rutadeLaImagen);
	        entidadGrafica.notificarse_detonacion();
    }

    public void explosionAdyacente() { 
        notificador.desubscribirse(this);
		color.set_color(Color.TRANSPARENTE);
	        cargarImagenesRepresentativas(rutadeLaImagen);
	        entidadGrafica.notificarse_detonacion();
        
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
        System.out.println("CARGAR IMAGENES REPRESENTATIVAS "+ ri +tiempo +"/"+tiempo+".png");
		imagenes[0] = ri +tiempo +"/"+tiempo+".png";
		imagenes[1] = ri +tiempo +"/"+tiempo+"-cursor.png";
		imagenes[2] = ri + "detonado.gif";
		imagenes[3] = ri + "enfocado-detonado.gif";
        
    }

    public void notificar() {
        System.out.println(tiempo);
        tiempo--;
        cargarImagenesRepresentativas(rutadeLaImagen);
        System.out.println(enfocada);
        if(enfocada){
            System.out.println("enfocada, enfocar");
            enfocar();}
        else {
            System.out.println("desenfocada");
            desenfocar(); // para que se actualice con el sprite adecuado
            }
        if(tiempo <= 0){
            finalizarJuegoPorExplosionDeBomba();
        }
    }


}
