import java.util.LinkedList;

import Entidades.Entidad;
import Entidades.GemaRayada;
import Logica.Color;

public class EstrategiaMatch4 implements Estrategias{
    
   protected int prioridad;
   public EstrategiaMatch4(int prioridad)
   {
      this.prioridad=prioridad;
   }

    public Resultado se_Cumple_la_Estrategia(LinkedList<Entidad> horizontal, LinkedList<Entidad> vertical, Entidad origen) {
        Resultado toReturn=null;
        if(horizontal.size()==4 && vertical.size()<3)
        {
           Entidad aux= horizontal.getFirst();
           Entidad gemaCreada=new GemaRayada(aux.get_TableroNotificable(),aux.get_fila(),aux.get_columna(),new Color(aux.get_color()), 5,false, aux.get_Skin());
           toReturn= new Resultado(horizontal, gemaCreada);
        }
        else if (vertical.size()==4 && horizontal.size()<3)
        {
           Entidad aux= horizontal.getFirst();
           Entidad gemaCreada=new GemaRayada(aux.get_TableroNotificable(),aux.get_fila(),aux.get_columna(),new Color(aux.get_color()), 1,false, aux.get_Skin());
           toReturn= new Resultado(horizontal, gemaCreada);
        }
     return toReturn;
    }

    public void set_prioridad(int prioridad)
    {
      this.prioridad=prioridad;
    }

    public int get_prioridad()
    {
      return prioridad;
    }
}
