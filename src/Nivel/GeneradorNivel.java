package Nivel;



import Entidades.GemaEnvuelta;
import Entidades.GemaNormal;
import Entidades.GemaRayada;
import Entidades.Roca;
import Logica.*;

import java.util.*;

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
			miNivel.setTotalMovimientos(miNivel.getMovimientos());
			//Leo tiempo del nivel
			miNivel.setTiempo(Integer.parseInt(input.nextLine().trim()));
			
			//Leo vidas
			miNivel.setVidas(Integer.parseInt(input.nextLine().trim()));
			
			// Leer terminador
			String terminador = input.nextLine();
			if (!terminador.equals("t")) {
				throw new Exception("Formato de archivo inválido. Se esperaba un terminador 't'.");
			}

			// Leer tipo y cantidad de gemas a destruir

			//Mejorar
			
			// Leer objetivos hasta encontrar la marca 'f'
			int id = 0;
			while (input.hasNextLine()) {
				String line = input.nextLine();
				if (line.equals("f")) {
					break;
				}
				String[] gemaData = line.split(",");
				Objetivos objetivo = new Objetivos(Integer.parseInt(gemaData[0].trim()), Integer.parseInt(gemaData[1].trim()));
				miNivel.agregarObjetivo(id,objetivo);
				id++;
			}


			t.resetar_tablero(filas, columnas);

			
			for (int i = 0; i < filas; i++) {
		        String[] valores = input.nextLine().split(" ");
		        for (int j = 0; j < columnas; j++) {
		            String[] partes = valores[j].split(",");
		            if (partes[0].equals("n")) {
		                t.agregar_entidad(new GemaNormal(i, j, new Color(Integer.parseInt(partes[1].trim())), true));
		            }else {
		            	if(partes[0].equals("r")) {
		            		t.agregar_entidad(new Roca(i, j, true));
		            	}else{
		            		if(partes[0].equals("p")) {
		            			t.agregar_entidad(new GemaRayada(i, j,new Color(Integer.parseInt(partes[1].trim())%10),Integer.parseInt(partes[1].trim())/10, true));
		            		}else{
		            		if(partes[0].equals("e")) {
		            			t.agregar_entidad(new GemaEnvuelta(i, j,new Color(Integer.parseInt(partes[1].trim())/10), true));
		            		}
		            	}
		            	}
		            }
		        }
		    }
			
		

			input.close();

		} catch (Exception ex) {
			System.out.println("GenerarNivelDefinitivo :: ME ROMPI");
			ex.getStackTrace();
		}
		return miNivel;
	}	    
}