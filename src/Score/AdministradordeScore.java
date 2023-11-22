package Score;

import java.util.PriorityQueue;

import Paneles.*;

public class AdministradordeScore {
    protected TopScore topScore;
    protected int scoreActual;
    protected CentralPaneles centralPaneles;

    public AdministradordeScore(CentralPaneles centralPaneles)
    {
        topScore=new TopScore();
        scoreActual=0;
        this.centralPaneles=centralPaneles;
    }

    public int getScore()
    {
        return scoreActual;
    }

    public void agregarScore(int puntos)
    {
        scoreActual=scoreActual+puntos;
        centralPaneles.actualizarScore(scoreActual);
    }

    public boolean entro_en_el_top5()
    {
        return (scoreActual>0 && (topScore.obtenerListadeJugadores().size()<5 || topScore.obtenerListadeJugadores().peek().get_score()<=scoreActual));
    }

    public PriorityQueue<Jugador> obtenerListadeJugadores()
    {
      return topScore.obtenerListadeJugadores();
    }
    
    public void mejorJugador(String nombre_del_Jugador)
    {
      topScore.agregarJugador(nombre_del_Jugador, scoreActual);
    }
}
