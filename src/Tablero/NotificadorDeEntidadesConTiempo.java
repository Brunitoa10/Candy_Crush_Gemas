package Tablero;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Entidades.EntidadNotificable;


public class NotificadorDeEntidadesConTiempo {
    //patron observer

    protected LinkedList<EntidadNotificable> listaDeSubscriptores;
    protected ScheduledExecutorService scheduler;

    public NotificadorDeEntidadesConTiempo(){
        listaDeSubscriptores = new LinkedList<EntidadNotificable>();
        scheduler = Executors.newScheduledThreadPool(1);
    }

    public void subscribirse(EntidadNotificable e){
        listaDeSubscriptores.addLast(e);
    }

    public void desubscribirse(EntidadNotificable e){
        listaDeSubscriptores.remove(e);
    }

    private void notificarEntidades(){
        Iterator<EntidadNotificable> it = listaDeSubscriptores.iterator();
        while(it.hasNext()){
            it.next().notificar();
            System.out.println("notificar!!!!!!");
        }
    }

    public void empezarContador(){
        scheduler.scheduleAtFixedRate(() ->notificarEntidades(), 0, 1, TimeUnit.SECONDS);
    }
    
}
