package Logica;

import java.awt.EventQueue;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import Entidades.Entidad;
import GUI.GUI;
import GUI.Celda;
import GUI.EntidadGrafica;
import Nivel.GeneradorNivel;
import Nivel.Nivel;
import Tablero.Tablero;


public class Logica {
	//Atributos
	protected Tablero miTablero;
	protected GUI miGUI;
	protected Nivel miNivel;
	protected Thread contadorTiempo;

	//Constructor
	public Logica(){
		miTablero = new Tablero(this,miGUI);
		miNivel = GeneradorNivel.cargar_nivel_y_tablero(miTablero,1,this);
		miGUI = new GUI(this, miTablero.getFila(), miTablero.getColumna());
		miTablero.asignarGUI(miGUI);	
		asociarEntidadesLogicasGraficas();
		miTablero.fijarJugador(miNivel.getFilaInicialJugador(), miNivel.getColumnaInicialJugador());
		
		inicializarTiempo();
	}

	
	public void mover_jugador(int direccion) {
		miTablero.moverJugador(direccion);
	}

	public void intercambiar(int direccion) {
		System.out.println(miTablero.intercambiar(direccion));
		if(miTablero.intercambiar(direccion)) {
			 miNivel.restarMovimientos();
			 miGUI.actualizarMovimientos(miNivel.getMovimientos());
		}
	}

	public void notificarDerrotaPorMovimientos() {
		miGUI.mostrarMensajeDerrotaPorMovimientos();
	}
	
	public void notificarDerrotaPorTiempo() {
		miNivel.restarVidas();
		
		if(miNivel.getVidas() >= 1) {
        	int tmpVidas = miNivel.getVidas();
        	reiniciarNivel();
        	System.out.println("Logica vidas en notificarDerrotaPorTiempo :: "+tmpVidas);
        	miNivel.setVidas(tmpVidas);
			miGUI.actualizarVidas();
        }else {
        	System.out.println("notificarDerrotaPorTiempo");
        	miGUI.mostrarMensajeDerrotaPorVidas();
        }
	}
	
	public void notificarDerrotaPorVidas() {
		System.out.println("notificarDerrotaPorVidas");
		reiniciarNivel();
	}
	
	public void notificarVictoriaPorMovimientos() {
		miGUI.mostrarMensajeVictoriaPorMovimientos();
	}
	
	public void notificarVictoriaPorObjetivos() {
		miGUI.mostrarMensajeVictoriaPorObjetivos();
	}
	
	public String[] obtenerInfoObjetivos() {
	    return miNivel.obtenerInfoObjetivos();
	}
	
	public int getCantidadDeObjetivos() {
		return miNivel.getCantidadDeObjetivos();
	}
	
	private void asociarEntidadesLogicasGraficas() {
		Entidad ent;
		EntidadGrafica egrafica;

		for (int f=0; f < miTablero.getFila(); f++) {
			for (int c=0; c < miTablero.getColumna(); c++) {
				ent = miTablero.getEntidad(f, c);
				egrafica = miGUI.agregar_entidad(ent);
				ent.setEntidadGrafica(egrafica);
			}
		}
		miGUI.setVisible(true);
	}

	public int disminuirTiempo(Timer timer) {
		int tiempo = miNivel.getTiempo()-1;
        if (tiempo == 0) {
            timer.cancel();
            miGUI.mostrarMensajeDerrotaPorTiempo();
        }else {
        	miNivel.setTiempo(tiempo);
        }
        return tiempo;
	}


	public int getTiempo() {
		return miNivel.getTiempo();
	}

	public int getMovimientos() {
		return miNivel.getMovimientos();
	}

	public void reiniciarNivel() {
		miTablero.resetearTablero(miTablero.getFila(), miTablero.getColumna());
	    miGUI.limpiarGUI();
	    miTablero.limpiarTablero();
	    miNivel = GeneradorNivel.cargar_nivel_y_tablero(miTablero, 1, this);
	    miTablero.asignarGUI(miGUI);
	    asociarEntidadesLogicasGraficas();
	    miNivel.setMovimientos(miNivel.getTotalMovimientos());
	    miGUI.actualizarMovimientos(miNivel.getMovimientos());
		miGUI.reiniciarProgreso();
	    miNivel.setTiempo(miNivel.getTotalTiempo());
	    miTablero.fijarJugador(miNivel.getFilaInicialJugador(), miNivel.getColumnaInicialJugador());
	    inicializarTiempo();
	}

	
	private void inicializarTiempo() {
		Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                disminuirTiempo(timer);
                miGUI.actualizarTiempo(getTiempo());
            }
        }, 1000, 1000); // Inicia el temporizador después de 1 segundo y se ejecuta cada 1 segundo
		
	}

	public void notificar_actualizacion_objetivos(int cant, int tipoGema) {
		miGUI.actualizarProgreso(cant, tipoGema);
	}

	public int getVidas() {
		return miNivel.getVidas();
	}

	public Entidad getEntidadDelTablero(int posx, int posy) {
		return miTablero.getEntidad(posx, posy);
	}

	public void actualizarTablero() {
		miGUI.reiniciarTablero();
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String [] args) {
		try {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					new Logica();
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}


	public String obtenerTipoDeGema(int tipoGema) {
		return miTablero.obtenerTipoGema(tipoGema);
	}


	public void actualizarObjetivos(LinkedList<Celda> l) {
		for (int pos = 0; pos<l.size()-1;pos++) {
            System.out.print(l.get(pos).getColorEntidad()+" ");
        }
		miNivel.actualizarObjetivos(l);
		//miGUI.actualizarObjetivos(miNivel.getCantObjetivo());
	}


	


}