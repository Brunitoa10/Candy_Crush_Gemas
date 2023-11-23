package EstrategiaMatch;

import Entidades.*;
import java.util.List;

public class Resultado {
    protected Entidad resultado;
    protected List<Entidad> gemas_a_romper;

    public Resultado(List<Entidad> gemas_a_romper, Entidad resultado)
    {
      this.resultado=resultado;
      this.gemas_a_romper=gemas_a_romper;
    }

    public List<Entidad> get_Gemas_a_romper()
    {
        return gemas_a_romper;
    }

    public Entidad get_resultado()
    {
        return resultado;
    }

    public void set_resultado(Entidad resultado)
    {
        this.resultado=resultado;
    }

    public void set_gemas_a_romper(List<Entidad> gemas_a_romper)
    {
        this.gemas_a_romper=gemas_a_romper;
    }
}
