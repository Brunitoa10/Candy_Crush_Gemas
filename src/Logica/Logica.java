package Logica;

import java.awt.EventQueue;

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
		
		//Constructor
		public Logica(){
			try {
				miTablero = new Tablero(this,miGUI);
				miNivel = generadorNivel.cargar_nivel_y_tablero(1, miTablero);
				miGUI = new GUI(this, miTablero.getFila(), miTablero.getColumna());
				asociarEntidadesLogicasGraficas();
				miTablero.fijarJugador(miNivel.getFilaInicialJugador(), miNivel.getColumnaInicialJugador());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}

		public void mover_jugador(int direccion) {
			miTablero.moverJugador(direccion);
		}
		
		public void intercambiar(int direccion) {
			miTablero.intercambiar(direccion);
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