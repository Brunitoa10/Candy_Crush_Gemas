package Score;

public class AdministradordeScore {
    protected TopScore topScore;
    protected int scoreActual;

    public AdministradordeScore()
    {
        topScore=new TopScore();
        scoreActual=0;
    }

    public int getScore()
    {
        return scoreActual;
    }

    public void agregarScore(int puntos)
    {
        scoreActual=scoreActual+puntos;
    }

    public void mejorJugador(String nombre-del-Jugador)
    {
        topScore.cargarLista();
        if(topScore.obtenerListadeJugadores().peek().get_score()<=scoreActual)
        {
          topScore.agregarJugador(nombre_del_Jugador, scoreActual);
        }
    }
}
