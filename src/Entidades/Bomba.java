package Entidades;

import Logica.*;
import Tablero.*;
import Timer.ObservableTimer;
import Timer.TickEvent;
import Timer.TickObserver;
import EstrategiaDetonaciones.*;

public class Bomba extends Obstaculo implements TickObserver {
    protected int tiempo = tiempoInicial;
    protected ObservableTimer notificador;
    protected static final int tiempoInicial = 30;
    protected Logica logica;

    public Bomba(TableroNotificable tablero, int f, int c, Color col, boolean visible, ObservableTimer n, Logica l) {
        super(tablero, f, c, "/assets/obstaculo/bomba/", col, visible);
        cargarImagenesRepresentativas(rutadeLaImagen);
        notificador = n;
        logica = l;
        l.suscribirBombaATimer(this);
    }

    public void detonar(Tablero t) {
        notificador.desuscribirse(this);
        EstategiaDetonacion estrategiadeDetonacion = new EstrategiaDetonacionBomba();
        estrategiadeDetonacion.detonar(this, t);
    }

    public void finalizarJuegoPorExplosionDeBomba() {
        notificador.desuscribirse(this);
        logica.notificarDerrotaPorVidas();
    }

    public int get_score() {
        return color.get_score();
    }

    public boolean esAfectadaPorExplosionAdyacente() {
        return true;
    }

    @Override
    public boolean se_produce_match_con(Entidad e) {
        return false;
    }

    public void cargarImagenesRepresentativas(String ri) {
        imagenes_representativas = new String[5];
        imagenes_representativas[0] = ri + tiempo + "/" + tiempo + ".png";
        imagenes_representativas[1] = ri + tiempo + "/" + tiempo + "-cursor.png";
        imagenes_representativas[2] = ri + "detonado.gif";
        imagenes_representativas[3] = ri + "enfocado-detonado.gif";

    }

    public void notificar() {

    }

    public int getTiempo() {
        return tiempo;
    }

    @Override
    public void update(TickEvent event) {
        tiempo--;
        cargarImagenesRepresentativas(rutadeLaImagen);
        if (enfocada) {
            enfocar();
        } else {
            desenfocar();
        }
        if (tiempo <= 0) {
            finalizarJuegoPorExplosionDeBomba();
        }
    }

    public boolean es_posible_intercambiar(Entidad e) {
        return false;
    }

}
