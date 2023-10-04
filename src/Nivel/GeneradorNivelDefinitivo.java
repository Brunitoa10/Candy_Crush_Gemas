package Nivel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Entidades.Gema;
import Entidades.GemaNormal;
import Entidades.Roca;
import Logica.Color;
import Tablero.Tablero;

public class GeneradorNivelDefinitivo {
	public static Nivel cargar_nivel_y_tablero(Tablero t, String archivo) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));

            int[] tamano = leerTamano(br);
            t.resetearTablero(tamano[0], tamano[1]);

            int movimientos = leerEntero(br, "Movimientos Disponibles:");
            int tiempo = leerEntero(br, "Tiempo Disponible:");

            // Ignorar líneas de objetivos
            for (int i = 0; i < 3; i++) {
                br.readLine();
            }

            cargarMatriz(t, br);

            br.readLine(); // Ignorar la línea de "Posicion Inicial"
            String[] posicionInicial = br.readLine().split(": ")[1].split(",");
            int filaInicial = Integer.parseInt(posicionInicial[0].trim());
            int columnaInicial = Integer.parseInt(posicionInicial[1].trim());

            br.close();

            t.printTable();
            return new Nivel(filaInicial, columnaInicial);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int[] leerTamano(BufferedReader br) throws IOException {
        br.readLine(); // Ignorar "Tamaño del Tablero"
        String[] tamano = br.readLine().split("x");
        int filas = Integer.parseInt(tamano[0].trim());
        int columnas = Integer.parseInt(tamano[1].trim());
        return new int[]{filas, columnas};
    }

    private static int leerEntero(BufferedReader br, String mensaje) throws IOException {
        br.readLine(); // Ignorar la línea de mensaje
        return Integer.parseInt(br.readLine().split(": ")[1].trim());
    }

    private static void cargarMatriz(Tablero t, BufferedReader br) throws Exception {
        for (int i = 0; i < t.getFila(); i++) {
            String[] gemas = br.readLine().split(" ");
            for (int j = 0; j < t.getColumna(); j++) {
                int tipoGema = Integer.parseInt(gemas[j]);
                if (tipoGema != 0) {
                    t.agregarEntidad(new GemaNormal(i, j, tipoGema));
                } else {
                    t.agregarCeldaVacia(i, j);
                }
            }
        }
    }
}
