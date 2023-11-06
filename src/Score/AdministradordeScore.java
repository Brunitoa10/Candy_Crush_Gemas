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

    public void mejorJugador()
    {
        topScore.cargarLista();
        if(topScore.obtenerListadeJugadores().peek().get_score()<=scoreActual)
        {
          //metodo que crea ventana en la gui para ingresar su nombre y devuelve un String<---acaNachito
          //topScore.agregarJugador(aca iria el return de ese metodo, scoreActual);
        }
    }
}
