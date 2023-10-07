package Nivel;


import Entidades.GemaNormal;
import Logica.Logica;

import java.util.Scanner;

import Tablero.Tablero;
import java.io.File;
public class GeneradorNivel {
	public static Nivel cargar_nivel_y_tablero(Tablero t, int nivel,Logica l) {
		Nivel miNivel = new Nivel(0,0,l);
		try {
			Scanner input = new Scanner(new File("src//Niveles//Nivel" + nivel + ".txt"));

			// Leer tamaño del tablero
			int filas = Integer.parseInt(input.nextLine().trim());
			int columnas = filas;

			// Leer posición inicial
			String[] posicionInicial = input.nextLine().split(",");

			miNivel.setPosicionJugador(Integer.parseInt(posicionInicial[0].trim()), Integer.parseInt(posicionInicial[1].trim()));
			
			//Leo cantidad de movimientos del nivel
			miNivel.setMovimientos(Integer.parseInt(input.nextLine().trim()));
			
			//Leo tiempo del nivel
			miNivel.setTiempo(Integer.parseInt(input.nextLine().trim()));

			// Leer terminador
			String terminador = input.nextLine();
			if (!terminador.equals("t")) {
				throw new Exception("Formato de archivo inválido. Se esperaba un terminador 't'.");
			}

			// Leer tipo y cantidad de gemas a destruir

			//Mejorar
			
			// Leer objetivos hasta encontrar la marca 'f'
			while (input.hasNextLine()) {
				String line = input.nextLine();
				if (line.equals("f")) {
					break;
				}
				String[] gemaData = line.split(",");
				miNivel.agregarObjetivo(Integer.parseInt(gemaData[0].trim()), Integer.parseInt(gemaData[1].trim()));
			}


			t.resetearTablero(filas, columnas);

			// Leer y establecer gemas
			for (int i = 0; i < filas; i++) {
				String[] valores = input.nextLine().split(" ");
				for (int j = 0; j < columnas; j++) {
					t.agregarEntidad(new GemaNormal(i, j, Integer.parseInt(valores[j].trim())));
				}
			}
			input.close();
			System.out.println("------------------------------------------------");
			System.out.println("Fila :: "+filas);
			System.out.println("Col :: "+columnas);
			System.out.println("PosX :: "+miNivel.getFilaInicialJugador());
			System.out.println("posY :: "+miNivel.getColumnaInicialJugador());
			System.out.println("Movimientos :: "+miNivel.getMovimientos());
			System.out.println("Tiempo :: "+miNivel.getTiempo());
			for(int i = 0; i < miNivel.sizeObjetivos(); i++) {
				System.out.println("CantGemas :: "+miNivel.getObjetivo(i).getCantGemas()+" tipoGema :: "+miNivel.getObjetivo(i).getTipoGema());
			}
			System.out.println("------------------------------------------------");
			
		} catch (Exception ex) {
			System.out.println("GenerarNivelDefinitivo :: ME ROMPI");
			ex.getStackTrace();
		}
		return miNivel;
	}	    
}
