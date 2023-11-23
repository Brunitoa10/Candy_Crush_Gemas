package Nivel;


import GeneradorEntidades.*;

import Logica.*;

import java.util.*;

import Tablero.TableroJuego;

import java.io.File;

public class GeneradorNivel {
    private static final Map<String, EntidadFactory> entidadFactories = EntidadFactoryRegistry.getEntidadFactories();
    
	public static Nivel cargar_nivel_y_tablero(TableroJuego t, int nivel,Logica l, String skin) {
		Nivel miNivel = new Nivel(0, 0, l);
        try {
            Scanner input = new Scanner(new File("src//Niveles//Nivel" + nivel + ".txt"));

            // Leer tamaño del tablero
            int filas = Integer.parseInt(input.nextLine().trim());
            int columnas = filas;

            // Leer posición inicial
            String[] posicionInicial = input.nextLine().split(",");
            miNivel.setPosicionJugador(Integer.parseInt(posicionInicial[0].trim()), Integer.parseInt(posicionInicial[1].trim()));

            // Leer cantidad de movimientos del nivel
            miNivel.setMovimientos(Integer.parseInt(input.nextLine().trim()));
            miNivel.setTotalMovimientos(miNivel.getMovimientos());

            // Leer tiempo del nivel
            miNivel.setTiempo(Integer.parseInt(input.nextLine().trim()));

            // Leer vidas
            miNivel.setVidas(Integer.parseInt(input.nextLine().trim()));

            // Leer terminador
            String terminador = input.nextLine();
            if (!terminador.equals("t")) {
                throw new Exception("Formato de archivo inválido. Se esperaba un terminador 't'.");
            }

            // Leer objetivos hasta encontrar la marca 'f'
            int id = 0;
            boolean continuar = true;

            while (input.hasNextLine() && continuar) {
                String line = input.nextLine();
                
                if (line.equals("f")) {
                    continuar = false;
                } else {
                    String[] gemaData = line.split(",");
                    Objetivos objetivo = new Objetivos(Integer.parseInt(gemaData[0].trim()), Integer.parseInt(gemaData[1].trim()));
                    miNivel.agregarObjetivo(id, objetivo);
                    id++;
                }
            }

            t.resetar_tablero(filas, columnas);

            for (int i = 0; i < filas; i++) {
                String[] valores = input.nextLine().split(" ");
                for (int j = 0; j < columnas; j++) {
                    String[] partes = valores[j].split(",");
                    EntidadFactory entidadFactory = entidadFactories.get(partes[0]);
                    if (entidadFactory != null) {
                        t.agregar_entidad(entidadFactory.crear(t, i, j, partes,skin));
                    }
                }
            }

            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return miNivel;
	}	    
}