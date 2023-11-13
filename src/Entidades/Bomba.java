package Entidades;

import Logica.Color;
import Tablero.NotificadorDeEntidadesConTiempo;
import Tablero.Tablero;
import Tablero.TableroNotificable;

public class Bomba extends Obstaculo implements EntidadNotificable {
   protected int tiempo;
   protected NotificadorDeEntidadesConTiempo notificador;
   protected static final int tiempoInicial = 10;

   public Bomba(TableroNotificable tablero,int f, int c, Color col, boolean visible, NotificadorDeEntidadesConTiempo n){
    
    super(tablero,f, c, "/assets/obstaculo/bomba/", col, visible); 
    System.out.println("CREACION DE BOMBA");
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

    private void actualizarImagenRep(){
        System.out.println("actualizar imagen rep");
        System.out.println(rutadeLaImagen);
        System.out.println(tiempo);
        imagenes[0] = rutadeLaImagen + tiempo +".png";
        imagenes[1] = rutadeLaImagen +tiempo +"-cursor.png";
    

    }

    public void cargarImagenesRepresentativas(String ri){
        imagenes = new String [4];
        System.out.println("CARGAR IMAGENES REPRESENTATIVAS "+ri + tiempo+".png");
		imagenes[0] = ri + tiempoInicial+".png";
		imagenes[1] = ri + tiempoInicial +"-cursor.png";
		imagenes[2] = ri + "detonado.gif";
		imagenes[3] = ri + "enfocado-detonado.gif";
    }

    public void notificar() {
        tiempo--;
        actualizarImagenRep();
        rutadeLaImagen = "/assets/obstaculo/bomba/"+tiempo+".png";
        if(tiempo <= 0){
            finalizarJuegoPorExplosionDeBomba();
        }
    }

}
