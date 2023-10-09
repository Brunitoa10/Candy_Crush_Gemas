package Logica;

import java.awt.EventQueue;
import java.util.Timer;
import java.util.TimerTask;

import Entidades.Entidad;
import GUI.GUI;
import GUI.EntidadGrafica;
import Nivel.GeneradorNivel;
import Nivel.Nivel;
import Tablero.Tablero;


public class Logica {
	//Atributos
	protected Tablero miTablero;
	protected GUI miGUI;
	protected Nivel miNivel;
	protected GeneradorNivel generadorNivel;
	protected Thread contadorTiempo; // Hilo para contar el tiempo

	//Constructor
	public Logica(){
		miTablero = new Tablero(this,miGUI);
		miNivel = generadorNivel.cargar_nivel_y_tablero(miTablero,1,this);
		miGUI = new GUI(this, miTablero.getFila(), miTablero.getColumna());
		miTablero.asignarGUI(miGUI);	
		asociarEntidadesLogicasGraficas();
		miTablero.fijarJugador(miNivel.getFilaInicialJugador(), miNivel.getColumnaInicialJugador());
		
		
		Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                disminuirTiempo(timer);
                miGUI.actualizarTiempo(getTiempo());
            }
        }, 1000, 1000); // Inicia el temporizador después de 1 segundo y se ejecuta cada 1 segundo
	}

	public void mover_jugador(int direccion) {
		miTablero.moverJugador(direccion);
	}

	public void intercambiar(int direccion) {
		miTablero.intercambiar(direccion);
		miNivel.restarMovimientos();
		miGUI.actualizarMovimientos(miNivel.getMovimientos());
	}

	public void notificarDerrotaPorMovimientos() {
		miGUI.mostrarMensajeDerrotaPorMovimientos();
		/*if(miNivel.getVidas() > 0) {
			int tmpVidas = miNivel.getVidas();
			reiniciarNivel();
			miNivel.setVidas(tmpVidas);
		}else {
			miGUI.mostrarMensajeDerrotaPorVidas();
		}*/
	}

	public void reiniciarNivel() {
		miNivel.reiniciarNivel(miTablero,miGUI,miNivel,this, generadorNivel);
		asociarEntidadesLogicasGraficas();
	}


	public void notificarDerrotaPorVidas() {
		miGUI.mostrarMensajeDerrotaPorVidas();
	}
	
	public void notificarDerrotaPorTiempo() {
		miGUI.mostrarMensajeDerrotaPorTiempo();
	}
	
	public void notificarVictoriaPorObjetivos() {
		miGUI.mostrarMensajeVictoriaPorMovimientos();
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
		int tiempo = miNivel.getTiempo();
		tiempo--;
        if (tiempo <= 0) {
            timer.cancel();
            miGUI.mostrarMensajeDerrotaPorTiempo();
        }
        miNivel.setTiempo(tiempo);
        return tiempo;
	}


	public int getTiempo() {
		return miNivel.getTiempo();
	}

	public int getMovimientos() {
		return miNivel.getMovimientos();
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


}