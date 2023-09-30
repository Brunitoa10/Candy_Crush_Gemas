package Logica;

import java.awt.EventQueue;

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
