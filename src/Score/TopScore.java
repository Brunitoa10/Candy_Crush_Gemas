package Score;

import java.io.*;
import java.util.PriorityQueue;
import java.util.Scanner;

public class TopScore
 {
    private PriorityQueue<Jugador> listadeJugadores;
    private static final int MAX_ELEMENTOS = 5;
    private static final String Archivo_de_guardado = "TOP5.csv";
    
    public TopScore() {
        listadeJugadores = new PriorityQueue<>(5,new ComparadorJugadores());
    }

    public void agregarJugador(String cadena, int numero) {
        cargarLista();
        if(listadeJugadores.size()==MAX_ELEMENTOS)
        {
            listadeJugadores.remove();
        }
            Jugador ganador = new Jugador(cadena, numero);
            listadeJugadores.add(ganador);
        guardarLista();
    }

    public PriorityQueue<Jugador> obtenerListadeJugadores()
    {
        cargarLista();
        return listadeJugadores;
    }

    public void guardarLista() {
        System.out.println("Estoy guardando la lista");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Archivo_de_guardado, false))) {
            for (Jugador jugador : listadeJugadores) {
                writer.write(jugador.get_nombre() + "," + jugador.get_score() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public void cargarLista() {
        listadeJugadores.clear(); // Limpiar la lista antes de cargarla nuevamente
        try (Scanner scanner = new Scanner(new File(Archivo_de_guardado))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] partes = linea.split(",");
                String nombre_del_Jugador = partes[0].trim();
                int puntuacion = Integer.parseInt(partes[1].trim());
                listadeJugadores.add(new Jugador(nombre_del_Jugador, puntuacion));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
}