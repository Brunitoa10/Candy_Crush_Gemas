package Score;

import java.io.*;
import java.util.PriorityQueue;

public class TopScore
 {
    private PriorityQueue<Jugador> listadeJugadores;
    private static final int MAX_ELEMENTOS = 5;

    public TopScore() {
        listadeJugadores = new PriorityQueue<>(5,new ComparadorJugadores<Jugador>());
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
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("mejoresJugadores.dat"))) {
            salida.writeObject(listadeJugadores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void cargarLista() {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("mejoresJugadores.dat"))) {
            listadeJugadores = (PriorityQueue<Jugador>) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}