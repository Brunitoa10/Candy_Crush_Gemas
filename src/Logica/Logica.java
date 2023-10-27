package Logica;

import java.awt.EventQueue;
import java.util.LinkedList;
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
	protected Thread contadorTiempo;
	private int nivelActual;
	
	//Constructor
	public Logica(){
		nivelActual = 1;
		miTablero = new Tablero(this);
		miNivel = GeneradorNivel.cargar_nivel_y_tablero(miTablero,nivelActual,this);
		//miGUI = new GUI(this, miTablero.getFila(), miTablero.getColumna());
		miGUI = GUI.getInstancia(this, miTablero.getFila(), miTablero.getColumna());
		asociarEntidadesLogicasGraficas();
		miTablero.fijarJugador(miNivel.getFilaInicialJugador(), miNivel.getColumnaInicialJugador());
		
		inicializarTiempo();
	}

	
	public void mover_jugador(int direccion) {
		miTablero.mover_jugador(direccion);
	}

	public void intercambiar(int direccion) {
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
        	reiniciarNivel(nivelActual);
        	miNivel.setVidas(tmpVidas);
			miGUI.actualizarVidas();
		}else {
			miGUI.mostrarMensajeDerrotaPorVidas();
		}
	}
	
	public void notificarDerrotaPorVidas() {
		System.out.println("notificarDerrotaPorVidas");
		nivelActual = 1;
		reiniciarNivel(nivelActual);
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
		Entidad e;
		EntidadGrafica eg;
		
		for (int f=0; f<miTablero.getFila(); f++) {
			for (int c=0; c<miTablero.getColumna(); c++) {
				e = miTablero.get_entidad(f, c);
				eg = miGUI.agregar_entidad(e);
				e.setEntidadGrafica(eg);
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

	public void reiniciarNivel(int n) {
		System.out.println("Logica :: Entre al reiniciar");
		nivelActual = n;
		miGUI.cambiarFondo(nivelActual);
		miGUI.actualizarVidas();
		
	    miTablero.resetar_tablero(miTablero.getFila(), miTablero.getColumna());
	   
	    miGUI.limpiarGUI();
	    
	    miNivel = GeneradorNivel.cargar_nivel_y_tablero(miTablero, nivelActual, this); // Carga el nivel actual

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
		return miTablero.get_entidad(posx, posy);
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


	public void actualizarObjetivos(LinkedList<Entidad> listaCombos) {
		miNivel.actualizarObjetivos(listaCombos);
	}
	

	public void cambiarNivel() {
	    System.out.println("Logica :: cambiarNivel");
	    nivelActual++;
	    
		if(nivelActual<6) {
	    	miGUI.dispose();;
	    	miTablero = new Tablero(this);
			miNivel = GeneradorNivel.cargar_nivel_y_tablero(miTablero,nivelActual,this);
			//miGUI = new GUI(this, miTablero.getFila(), miTablero.getColumna());	
			miGUI = GUI.getInstancia(this, miTablero.getFila(), miTablero.getColumna());
			miGUI.cambiarFondo(nivelActual);
			asociarEntidadesLogicasGraficas();
		} else {
			miGUI.mostrarMensajeFinDelJuego();
		}
		
		miTablero.fijarJugador(miNivel.getFilaInicialJugador(), miNivel.getColumnaInicialJugador());
	}


<<<<<<< HEAD
	/*public void actualizarTablero() {
		miGUI.actualizarTablero();
	}*/


	public Tablero getTablero() {
		// TODO Auto-generated method stub
		return miTablero.obtenerTablero();
=======
	public void actualiarTableroGUI() {
		miGUI.actualiarTableroGUI();
	}


	public Tablero getTablero() {
		return miTablero;
>>>>>>> e02cc1babbf0f14fd9db4e22b4078a6488bbb646
	}

}