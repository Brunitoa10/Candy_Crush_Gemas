package Nivel;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

import GeneradorEntidades.EntidadFactory;
import GeneradorEntidades.EntidadFactoryRegistry;
import Tablero.TableroJuego;

public class GeneradorNivel {
    private static final Map<String, EntidadFactory> entidadFactories = EntidadFactoryRegistry.getEntidadFactories();
    
	public static Nivel cargar_nivel_y_tablero(TableroJuego t, int nivel) {
        Nivel miNivel = new Nivel(0, 0,nivel);
        try {

            Scanner lectorTotalNivel = new Scanner(new File("src//Niveles//TotalNiveles.txt"));
             miNivel.setMaxNiveles(Integer.parseInt(lectorTotalNivel.nextLine().trim()));
            lectorTotalNivel.close();


            Scanner input = new Scanner(new File("src//Niveles//Nivel" + nivel + ".txt"));

            // Leer tamaño del tablero
            int filas = Integer.parseInt(input.nextLine().trim());
            int columnas = filas;

            // Leer posición inicial
            String[] posicionInicial = input.nextLine().split(",");
            miNivel.setFila_inicial_jugador(Integer.parseInt(posicionInicial[0].trim()));
            miNivel.setColumna_inicial_jugador(Integer.parseInt(posicionInicial[1].trim()));

            // Leer cantidad de movimientos del nivel
            miNivel.setCantidadMovimientos(Integer.parseInt(input.nextLine().trim()));
            //miNivel.setTotalMovimientos(miNivel.getMovimientos());

            // Leer tiempo del nivel
            miNivel.setCantidadTiempo(Integer.parseInt(input.nextLine().trim()));

            // Leer vidas
            miNivel.setCantidadVidas(Integer.parseInt(input.nextLine().trim()));

            // Leer terminador
            String terminador = input.nextLine();
            if (!terminador.equals("t")) {
                throw new Exception("Formato de archivo inválido. Se esperaba un terminador 't'.");
            }

            // Leer objetivos hasta encontrar la marca 'f'
           /*  int id = 0;
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
            }*/

            // Leer Estrategias hasta encontrar la marca 'f'
           /*  id = 0;
            continuar = true;
            LinkedList<Estrategias> estrategias= new LinkedList<Estrategias>();
            while (input.hasNextLine() && continuar) {
                String line = input.nextLine();
                char myChar = line.charAt(0);
                if (line.equals("f")) {
                    continuar = false;
                } else {
                    Estrategias estrategia= EstrategiaFactory.createEstrategia(myChar);
                    estrategias.addLast(estrategia);
                    id++;
                }
            }
            miNivel.setEstrategias(estrategias);
            */
            t.resetar_tablero(filas, columnas);

            for (int i = 0; i < filas; i++) {
                String[] valores = input.nextLine().split(" ");
                for (int j = 0; j < Math.min(columnas, valores.length); j++) {
                    String[] partes = valores[j].split(",");
                    if (partes.length >= 2) {
                        EntidadFactory entidadFactory = entidadFactories.get(partes[0]);
                        if (entidadFactory != null) {
                            int colorEntidad = Integer.parseInt(partes[1].trim());
                            t.agregar_entidad(entidadFactory.crearEntidad(t, i, j, colorEntidad));
                        } else {
                            System.err.println("Factoría de entidad no encontrada para " + partes[0]);
                        }
                    } else {
                        System.err.println("Formato de valor inválido: " + valores[j]);
                    }
                }
            }
            /*for (int i = 0; i < filas; i++) {
                String[] valores = input.nextLine().split(" ");
                for (int j = 0; j < columnas; j++) {
                    String[] partes = valores[j].split(",");
                    EntidadFactory entidadFactory = entidadFactories.get(partes[0]);
                    // EntidadFactory entidadCaremelo = new EntidadCaramelo(); 
                    if (entidadFactory != null) {
                        t.agregar_entidad(entidadFactory.crearEntidad(t, i, j, Integer.parseInt(partes[1].trim())));
                    }
                }
            }*/

            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return miNivel;
	}	    
}