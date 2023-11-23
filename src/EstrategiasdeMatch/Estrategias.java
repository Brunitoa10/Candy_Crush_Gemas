import Entidades.*;
import java.util.LinkedList;


public interface Estrategias {
    public abstract Resultado se_Cumple_la_Estrategia(LinkedList<Entidad> horizontal, LinkedList<Entidad> vertical, Entidad origen);
}
