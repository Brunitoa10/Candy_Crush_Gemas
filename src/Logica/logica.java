package Logica;

import java.awt.EventQueue;

import Entidades.Entidad;
import GUI.EntidadGrafica;
import GUI.GUI;
import Nivel.generadorNivel;
import Nivel.nivel;
import Tablero.tablero;



public class logica {
	//Atributos
	protected tablero miTablero;
	protected GUI miGUI;
	protected nivel miNivel;

	//Constructor
	public logica(){
		miTablero = new tablero(this);
		miNivel = generadorNivel.cargar_nivel_y_tablero("/Niveles/Nivel1.txt", miTablero);
		miGUI = new GUI(this, miTablero.getFila(), miTablero.getColumna());
		asociarEntidadesLogicasGraficas();
		miTablero.fijarJugador(miNivel.getFilaInicialJugador(), miNivel.getColumnaInicialJugador());
	}

	public void mover_jugador(int d) {
		miTablero.mover_jugador(d);
	}
	
	public void intercambiar(int d) {
		miTablero.intercambiar(d);
	}
	
	private void asociarEntidadesLogicasGraficas() {
		Entidad entidad;
		EntidadGrafica egrafica;
		
		for (int f=0; f < miTablero.getFila(); f++) {
			for (int c=0; c < miTablero.getColumna(); c++) {
				entidad = miTablero.get_entidad(f, c);
				egrafica = miGUI.agregar_entidad(entidad);
				entidad.set_entidad_grafica(egrafica);
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
                	new logica();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
	}
}
