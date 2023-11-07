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

    public boolean entro_en_el_top5()
    {
        return (topScore.obtenerListadeJugadores().size()<5 || topScore.obtenerListadeJugadores().peek().get_score()<=scoreActual);
    }
    
    public void mejorJugador(String nombre_del_Jugador)
    {
          topScore.agregarJugador(nombre_del_Jugador, scoreActual);
    }
}
