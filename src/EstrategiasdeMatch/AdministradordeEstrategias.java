import java.util.Iterator;
import java.util.PriorityQueue;

public class AdministradordeEstrategias {
    protected PriorityQueue<Par_de_Estrategia> estrategias;

    public AdministradordeEstrategias()
    {
        estrategias= new PriorityQueue<Par_de_Estrategia>();
    }

    public void agregarEstrategia(int prioridad, Estrategia estrategia)
    {
        estrategias.add(new Par_de_Estrategia(prioridad, estrategia));
    }

    public Estrategia obtenerEstrategia()
    {
        return estrategias.remove().get_estrategia();
    }

    public boolean esValido(int vertical, int horizontal)
    {
        Iterator<Par_de_Estrategia> aux= estrategias.iterator();
        boolean valido=false;
        while(!valido && aux.hasNext())
        {
            if(aux.next().get_estrategia().cumple_con_la_Estrategia(vertical,horizontal))
            {
                valido=true;
            }
        }
        return valido;
    }
}
