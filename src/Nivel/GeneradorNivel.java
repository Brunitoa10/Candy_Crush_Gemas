package Nivel;


import Entidades.GemaNormal;

import java.util.Scanner;

import Tablero.Tablero;
import java.io.File;

public class GeneradorNivel {
	 public static Nivel cargar_nivel_y_tablero(Tablero t, int nivel) {
	        try {
	            Scanner input = new Scanner(new File("Niveles/Nivel" + nivel + ".txt"));

	         // Leer tamaño del tablero
	            int filas = Integer.parseInt(input.nextLine().trim());
	            int columnas = filas;

	            // Leer posición inicial
	            String[] posicionInicial = input.nextLine().split(",");
	            int posX = Integer.parseInt(posicionInicial[0].trim());
	            int posY = Integer.parseInt(posicionInicial[1].trim());
	         
	            // Leer terminador
	            String terminador = input.nextLine();
	            if (!terminador.equals("f")) {
	                throw new Exception("Formato de archivo inválido. Se esperaba un terminador 'f'.");
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
	          System.out.println("Fila :: "+filas);
	          System.out.println("Col :: "+columnas);
	          System.out.println("PosX :: "+posX);
	          System.out.println("posY :: "+posY);
	       

	         
	           return new Nivel(posX, posY);
	        } catch (Exception ex) {
	          System.out.println("GenerarNivelDefinitivo :: ME ROMPI");
	          ex.getStackTrace();
	            return null;
	        }
			
	    }	    
}
