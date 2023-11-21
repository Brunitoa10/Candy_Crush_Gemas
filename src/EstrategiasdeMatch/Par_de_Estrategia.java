

import java.io.*;

public class Par_de_Estrategia implements Serializable {

    protected int prioridad;
    protected Estrategia estrategia;

    public Par_de_Estrategia(int prioridad, Estrategia estrategia)
    {
      this.prioridad=prioridad;
      this.estrategia=estrategia;
    }
    
    public int get_prioridad()
    {
        return prioridad;
    }

    public Estrategia get_estrategia()
    {
        return estrategia;
    }

    public void set_prioridad(int prioridad)
    {
        this.prioridad=prioridad;
    }

    public void set_Estrategia(Estrategia estrategia)
    {
        this.estrategia=estrategia;
    }
}
