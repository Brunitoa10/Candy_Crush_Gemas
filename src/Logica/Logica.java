package Logica;

import java.awt.EventQueue;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import Entidades.Entidad;
import GUI.GUI;
import GUI.EntidadGrafica;
import Nivel.GeneradorNivel;
import Nivel.Nivel;
import Score.AdministradordeScore;
import Score.Jugador;
import Tablero.Tablero;
import Score.*;

public class Logica {
	// Atributos
	protected Tablero miTablero;
	protected GUI miGUI;
	protected Nivel miNivel;
	protected Thread contadorTiempo;
	private int nivelActual;
	private static final int MAX_NIVEL = 6;
	protected AdministradordeScore administradordeScore;
	private ScheduledExecutorService executorService;
    private ScheduledFuture<?> future;

	// Constructor
	public Logica() {
		nivelActual = 1;
		miTablero = new Tablero(this);
		miNivel = GeneradorNivel.cargar_nivel_y_tablero(miTablero, nivelActual, this);
		miGUI = new GUI(this, miTablero.getFila(), miTablero.getColumna());
		miTablero.asociar_entidades_logicas_y_graficas();
		miTablero.fijar_jugador(miNivel.getFilaInicialJugador(), miNivel.getColumnaInicialJugador());
		miGUI.mostrar();
		administradordeScore = new AdministradordeScore(miGUI.obtenerCentralPaneles());

		inicializarTiempo();
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
			System.out.println(administradordeScore.getScore());
		} else {
			miGUI.mostrarMensajeDerrotaPorVidas();
		}
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

	public void reiniciarNivel(int nuevoNivel) {
        miGUI.dispose();
		
        // Crear un nuevo tablero
        miTablero = new Tablero(this);

        // Cargar el nuevo nivel en el tablero
        miNivel = GeneradorNivel.cargar_nivel_y_tablero(miTablero, nuevoNivel, this);

	
        // Crear una nueva GUI con el nuevo tablero
        miGUI = new GUI(this, miTablero.getFila(), miTablero.getColumna());
		//miGUI.resetear(this, miTablero.getFila(), miTablero.getColumna());
		miGUI.cambiarFondo(nuevoNivel);
		miGUI.actualizarScore(administradordeScore.getScore());
        // Asociar entidades lógicas y gráficas en el nuevo tablero
        miTablero.asociar_entidades_logicas_y_graficas();
        
        // Fijar la posición inicial del jugador en el nuevo nivel
        miTablero.fijar_jugador(miNivel.getFilaInicialJugador(), miNivel.getColumnaInicialJugador());
		inicializarTiempo();
    }

	private void inicializarTiempo() {
        executorService = Executors.newSingleThreadScheduledExecutor();

        future = executorService.scheduleAtFixedRate(() -> {
            disminuirTiempo();
            miGUI.actualizarTimer(getTiempo());
        }, 1, 1, TimeUnit.SECONDS);  // Inicia el temporizador después de 1 segundo y se ejecuta cada 1 segundo
    }

    public void pausarTiempo() {
        if (future != null && !future.isCancelled()) {
            future.cancel(false);
        }
    }

    public void reanudarTiempo() {
        if (executorService != null && (future == null || future.isCancelled())) {
            future = executorService.scheduleAtFixedRate(() -> {
                disminuirTiempo();
                miGUI.actualizarTimer(getTiempo());
            }, 1, 1, TimeUnit.SECONDS);
        }
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
	public static void main(String[] args) {
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

	public void iniciarSiguienteNivel() {
		System.out.println("Logica :: iniciarSiguienteNivel");
		nivelActual++;

		if (nivelActual < MAX_NIVEL) {
			cargarSiguienteNivel();
		} else {
			finalizarJuego();
		}

		
	}

	private void cargarSiguienteNivel() {
		miGUI.dispose();
		miTablero = new Tablero(this);
		miNivel = GeneradorNivel.cargar_nivel_y_tablero(miTablero, nivelActual, this);
		miGUI = new GUI(this, miTablero.getFila(), miTablero.getColumna());
		
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

}
