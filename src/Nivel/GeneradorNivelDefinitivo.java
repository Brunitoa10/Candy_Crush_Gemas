package Nivel;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Entidades.GemaNormal;
import GUI.GUI;
import Logica.Logica;
import Tablero.Tablero;

public class GeneradorNivelDefinitivo {
	/*public static void cargarNivelYTablero(int nivel) {
		try {
			 Scanner input = new Scanner(new File("Niveles/Nivel" + nivel + ".txt"));
			while (input.hasNextLine()) {
				String line = input.nextLine();
				System.out.println(line);
			}
			input.close();
		} catch (Exception ex) {
			System.out.println("GeneradorNivelDefinitivo :: El nivel no existe");
		}
	}
	public static void main(String[] args) {
       
         cargarNivelYTablero(1);
        // Puedes usar el objeto 'nivel' según sea necesario en tu aplicación
    }*/
	 public static NivelPiolita cargarNivelYTablero2(int nivel, Tablero t) {
	        try {
	            Scanner input = new Scanner(new File("Niveles/Nivel" + nivel + ".txt"));

	            // Leer tamaño del tablero
	            int filas = input.nextInt();
	            int columnas = input.nextInt();
	            input.nextLine(); // Consumir el salto de línea

	            // Leer movimientos disponibles y tiempo disponible
	            int movimientosDisponibles = Integer.parseInt(input.nextLine().trim());
	            int tiempoDisponible = Integer.parseInt(input.nextLine().trim());
	            
	            // Leer objetivos
	            List<String> objetivos = new ArrayList<>();
	            while (true) {
	                String linea = input.nextLine();
	                if (linea.equals("f")) {
	                    break;
	                }
	                objetivos.add(linea);
	            }

	         // Leer posición inicial
	            String[] posicionInicial = input.nextLine().split(",");
	            int posX = Integer.parseInt(posicionInicial[0].trim());
	            int posY = Integer.parseInt(posicionInicial[1].trim());

	         // Leer matriz del tablero
	            int[][] matriz = new int[filas][columnas];
	            for (int i = 0; i < filas; i++) {
	                for (int j = 0; j < columnas; j++) {
	                    matriz[i][j] = input.nextInt();
	                }
	            }

	            input.close();

	            // Inicializar el tablero
	            t.resetearTablero(filas, columnas);

	            // Agregar celdas vacías
	            for (int i = 0; i < filas; i++) {
	                for (int j = 0; j < columnas; j++) {
	                    t.agregarCeldaVacia(i, j);
	                }
	            }

	            // Fijar la posición del jugador
	            t.fijarJugador(posX, posY);

	            // Colocar las gemas según la matriz
	            for (int i = 0; i < filas; i++) {
	                for (int j = 0; j < columnas; j++) {
	                    int tipoGema = matriz[i][j];
	                    if (tipoGema != 0) {
	                        GemaNormal gema = new GemaNormal(i, j, tipoGema);
	                        t.setEntidad(gema);
	                    }
	                }
	            }

	            // Crear y retornar un objeto Nivel con la información
	            return new NivelPiolita(filas, columnas, movimientosDisponibles, tiempoDisponible, objetivos, posX, posY, matriz);

	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return null;
	        }
	    }	    
}
