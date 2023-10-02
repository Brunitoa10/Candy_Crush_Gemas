package Logica;

import java.awt.EventQueue;

import Entidades.Entidad;
import Entidades.entidad;
import GUI.EntidadGrafica;
import GUI.GUI;
import GUI.entidadGrafica;
import Nivel.generadorNivel;
import Nivel.nivel;
import Tablero.Tablero;



public class Logica {
	//Atributos
	protected Tablero miTablero;
	protected GUI miGUI;
	protected nivel miNivel;

	//Constructor
	public Logica(){
		miTablero = new Tablero(this);
		miNivel = generadorNivel.cargar_nivel_y_tablero("/Niveles/Nivel1.txt", miTablero);
		miGUI = new GUI(this, miTablero.getFila(), miTablero.getColumna());
		asociarEntidadesLogicasGraficas();
		miTablero.fijarJugador(miNivel.getFilaInicialJugador(), miNivel.getColumnaInicialJugador());
	}

	public void mover_jugador(int direccion) {
		miTablero.mover_jugador(direccion);
	}
	
	public void intercambiar(int direccion) {
		miTablero.intercambiar(direccion);
	}
	
	private void asociarEntidadesLogicasGraficas() {
		entidad entidad;
		entidadGrafica egrafica;
		
		for (int f=0; f < miTablero.getFila(); f++) {
			for (int c=0; c < miTablero.getColumna(); c++) {
				entidad = miTablero.get_entidad(f, c);
				egrafica = miGUI.agregar_entidad(entidad);
				entidad.setEntidadGrafica(egrafica);
			}
		}
		miGUI.setVisible(true);
	}
	/**
	 * Launch the application.
	 */
	public static void main(String [] args) {
		EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	new Logica();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
	}
}
