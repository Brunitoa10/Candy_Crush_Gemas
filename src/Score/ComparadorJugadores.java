package Score;
import java.util.Comparator;

public class ComparadorJugadores implements Comparator<Jugador> {
    public int compare(Jugador jugador1, Jugador jugador2) {
        return Integer.compare(jugador1.get_score(), jugador2.get_score());
    }
}
