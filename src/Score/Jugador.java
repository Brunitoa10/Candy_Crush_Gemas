package Score;

import java.io.*;

public class Jugador implements Serializable
{
    protected int score;
    protected String nombre;

  public Jugador(String nombre, int score)
  {
    this.score=score;
    this.nombre=nombre;
  }    

  public void setNombre(String nombre)
  {
    this.nombre=nombre;
  }

  public void setScore(int score)
  {
     this.score=score;
  }

  public Integer get_score()
  {
    return score;
  }

  public String get_nombre()
  {
    return nombre;
  }
}
