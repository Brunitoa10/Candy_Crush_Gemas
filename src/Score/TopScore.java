package Score;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class TopScore
 {
    private List<Jugador> listadeJugadores;
    private static final int MAX_ELEMENTOS = 5;

    public TopScore() {
        listadeJugadores = new ArrayList<>();
    }

    public void agregarJugador(String cadena, int numero) throws FullListException {
        if(listadeJugadores.size()!=MAX_ELEMENTOS)
        {
        Jugador ganador = new Jugador(cadena, numero);
        listadeJugadores.add(ganador);
        }
        else throw new FullListException ("Ya hay 5 jugadores");
    }

    public List<Jugador> obtenerListadeJugadores()
    {
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
            listadeJugadores = (List<Jugador>) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //SIEMPRE CARGEN ANTES DE AGREGAR Y GUARDEN DESPUES 
}