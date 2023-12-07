package EstrategiaMatch;

import Entidades.*;
import Resultado.Resultado;

import java.util.LinkedList;

public interface Estrategias {
    public Resultado se_Cumple_la_Estrategia(LinkedList<Entidad> horizontal, LinkedList<Entidad> vertical,
            Entidad origen);

    public void set_prioridad(int prioridad);

    public int get_prioridad();

    public String get_NombreRegla();
}
