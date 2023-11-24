package Logica;

import java.awt.EventQueue;
import java.util.LinkedList;
import java.util.List;

import Timer.ObservableTimer;
import Timer.TickEvent;
import Timer.TickObserver;

import java.util.PriorityQueue;

import Entidades.Bomba;
import Entidades.Entidad;
import EstrategiaMatch.Estrategias;
import GUI.GUI;
import GUI.EntidadGrafica;
import Nivel.GeneradorNivel;
import Nivel.Nivel;
import Score.AdministradordeScore;
import Score.Jugador;
import Tablero.Tablero;

public class Logica {
	// Atributos
	protected Tablero miTablero;
	protected GUI miGUI;
	protected Nivel miNivel;
	protected Thread contadorTiempo;
	private int nivelActual;
	private static final int MAX_NIVEL = 6;
	protected AdministradordeScore administradordeScore;
	protected String skin;
	ObservableTimer observableTimer = new ObservableTimer();
	

	// Constructor
	public Logica() {
		miTablero = new Tablero(this);
		miGUI = new GUI(this, miTablero.getFila(), miTablero.getColumna());
		miGUI.mostrar();
		miGUI.mostrarModosDeJuego();
		
	}

	public void inicializarJuego(){
		nivelActual = 1;
		miNivel = GeneradorNivel.cargar_nivel_y_tablero(miTablero, nivelActual, this, skin);
		administradordeScore = new AdministradordeScore(miGUI.obtenerCentralPaneles());
		miGUI.inicializarJuego();
		miTablero.asociar_entidades_logicas_y_graficas();
		miTablero.fijar_jugador(miNivel.getFilaInicialJugador(), miNivel.getColumnaInicialJugador());
		
		inicializarTiempo();
	}

	public void set_skin(String skin){
		this.skin=skin;
	}

	public void mover_jugador(int direccion) {
		miTablero.mover_jugador(direccion);
	}

	public void intercambiar(int direccion) {
		if (miTablero.intercambiar_entidades(direccion)) {
			miNivel.restarMovimientos();
			miGUI.actualizarMovimientos(miNivel.getMovimientos());
		}

	}

	public void notificarDerrotaPorMovimientos() {
		miGUI.mostrarMensajeDerrotaPorMovimientos();
	}

	public void notificarDerrotaPorTiempo() {
		miNivel.restarVidas();
		if (miNivel.getVidas() >= 1) {
			int tmpVidas = miNivel.getVidas();
			reiniciarNivel(nivelActual);
			miNivel.setVidas(tmpVidas);
			miGUI.actualizarVidas();
		} else {
			miGUI.mostrarMensajeDerrotaPorVidas();
		}
	}

	public int getScore() {
		return administradordeScore.getScore();
	}

	public void notificarDerrotaPorVidas() {
		miGUI.mostrarMensajeDerrotaPorVidas();
		if (administradordeScore.entro_en_el_top5()) {
			String nombre_del_Jugador = miGUI.obtenerNombreJugador();
			administradordeScore.mejorJugador(nombre_del_Jugador);
		}
		nivelActual = 1;
	}

	public void notificarVictoriaPorObjetivos() {
		miGUI.mostrarMensajeVictoriaPorObjetivos();
	}

	public void agregarScore(int score) {
		administradordeScore.agregarScore(score);
	}

	public String[] obtenerInfoObjetivos() {
		return miNivel.obtenerInfoObjetivos();
	}

	public String[] obtenerArrayNombresJugadores() {
		return administradordeScore.obtenerArrayNombresJugadores();
	}

	public int[] obtenerArrayScoreJugadores() {
		return administradordeScore.obtenerArrayScoreJugadores();
	}

	public PriorityQueue<Jugador> obtenerListadeJugadores() {
		return administradordeScore.obtenerListadeJugadores();
	}

	public int getCantidadDeObjetivos() {
		return miNivel.getCantidadDeObjetivos();
	}

	public void asociar_entidad_logica_y_grafica(Entidad entidad_logica) {
		EntidadGrafica entidad_grafica = miGUI.agregar_entidad(entidad_logica);
		entidad_logica.set_EntidadGrafica(entidad_grafica);
	}

	public int disminuirTiempo() {
		int tiempo = miNivel.getTiempo() - 1;
		if (tiempo == 0) {
			miGUI.mostrarMensajeDerrotaPorTiempo();
		} else {
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

	public LinkedList<Estrategias> getEstrategias()
	{
		return miTablero.getEstrategias();
	}

	public void reiniciarNivel(int nuevoNivel) {
		this.nivelActual = nuevoNivel;
        // Crear un nuevo tablero
        miTablero = new Tablero(this);
		observableTimer.desuscribirTodo();

        // Cargar el nuevo nivel en el tablero
        miNivel = GeneradorNivel.cargar_nivel_y_tablero(miTablero, nuevoNivel, this, skin);
		
        // Crear una nueva GUI con el nuevo tablero
        miGUI.reiniciarGUI(miTablero.getFila(), miTablero.getColumna());
		//miGUI.resetear(this, miTablero.getFila(), miTablero.getColumna());
		miGUI.cambiarFondo(nuevoNivel);
		miGUI.actualizarScore(administradordeScore.getScore());
        // Asociar entidades lógicas y gráficas en el nuevo tablero
        miTablero.asociar_entidades_logicas_y_graficas();
        
        // Fijar la posición inicial del jugador en el nuevo nivel
        miTablero.fijar_jugador(miNivel.getFilaInicialJugador(), miNivel.getColumnaInicialJugador());
		reanudarTiempo();
    }

	public ObservableTimer getTimer() {
		return observableTimer;
	}

	private void inicializarTiempo() {
        // Add the observer
		observableTimer.resume();
        observableTimer.addObserver(new TickObserver() {
            @Override
            public void update(TickEvent event) {
                disminuirTiempo();
            	miGUI.actualizarTimer(getTiempo());
				miGUI.refrescar();
            }
        });
    }

    public void pausarTiempo() {
        observableTimer.pause();
    }

    public void reanudarTiempo() {
        observableTimer.resume();
    }

	public void suscribirBombaATimer(Bomba bomba) {
        observableTimer.addObserver(bomba);
	}
	
    public void setEstrategias(LinkedList<Estrategias> estrategias) {
		miTablero.crearAdministradorEstrategias(estrategias);
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

	public String obtenerTipoDeGema(int tipoGema) {
		return miTablero.obtenerTipoGema(tipoGema);
	}

	public void actualizarObjetivos(List<Entidad> listaCombos) {
		miNivel.actualizarObjetivos(listaCombos);
	}

	public void iniciarSiguienteNivel() {
		nivelActual++;

		if (nivelActual < MAX_NIVEL) {
			cargarSiguienteNivel();
		} else {
			finalizarJuego();
		}	
	}

	private void cargarSiguienteNivel() {
		observableTimer.desuscribirTodo();
		miTablero = new Tablero(this);
		miNivel = GeneradorNivel.cargar_nivel_y_tablero(miTablero, nivelActual, this, skin);

		miGUI.reiniciarGUI(miTablero.getColumna(), miTablero.getFila());
		miGUI.cambiarFondo(nivelActual);
		miTablero.asociar_entidades_logicas_y_graficas();
		miTablero.fijar_jugador(miNivel.getFilaInicialJugador(), miNivel.getColumnaInicialJugador());
	}

	private void finalizarJuego() {
		if (administradordeScore.entro_en_el_top5()) {
			String nombreDelJugador = miGUI.obtenerNombreJugador();
			administradordeScore.mejorJugador(nombreDelJugador);
		}
		miGUI.mostrarMensajeFinDelJuego();
	}

	public int getNivelActual() {
		return nivelActual;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					new Logica();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
