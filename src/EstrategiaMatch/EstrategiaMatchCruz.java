package EstrategiaMatch;

import java.util.LinkedList;

import Entidades.Entidad;
import Entidades.GemaEnvuelta;
import Logica.Color;

public class EstrategiaMatchCruz implements Estrategias{

   protected int prioridad;
   public EstrategiaMatchCruz(int prioridad)
   {
      this.prioridad=prioridad;
   }

    public Resultado se_Cumple_la_Estrategia(LinkedList<Entidad> horizontal, LinkedList<Entidad> vertical, Entidad origen) {
      Resultado toReturn=null;
      if((horizontal.size()>=4 && vertical.size()>=3) && !(horizontal.getFirst().equals(vertical.getLast())) && !(horizontal.getFirst().equals(vertical.getFirst())) && !(vertical.getFirst().equals(horizontal.getLast())) && !(vertical.getFirst().equals(horizontal.getFirst())))
      {
       Entidad gemaCreada= new GemaEnvuelta(origen.get_TableroNotificable(),origen.get_fila() ,origen.get_columna(),new Color(origen.get_color()), false, origen.get_Skin());
       LinkedList<Entidad> toInsert= enlazarListas(horizontal, vertical);
       toReturn= new Resultado(toInsert,gemaCreada);
      }
      return toReturn;
    }

    private LinkedList<Entidad> enlazarListas(LinkedList<Entidad> lista1, LinkedList<Entidad> lista2)
    {
         LinkedList<Entidad> toReturn= new LinkedList<>();
         for(Entidad ent: lista1)
         {
            toReturn.addLast(ent);
         }
         for(Entidad ent: lista2)
         {
            toReturn.addLast(ent);
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
