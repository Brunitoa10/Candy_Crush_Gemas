
package EstrategiaMatch;

import java.util.LinkedList;

import Entidades.Entidad;
import Entidades.GemaEnvuelta;
import Logica.Color;

public class EstrategiaMatchS implements Estrategias{

   protected int prioridad;
   public EstrategiaMatchS()
   {
      this.prioridad=2;
   }

    public Resultado se_Cumple_la_Estrategia(LinkedList<Entidad> horizontal, LinkedList<Entidad> vertical, Entidad origen) {
      Resultado toReturn=null;
      int tamañoHorizontal=horizontal.size();
      int tamañoVertical=vertical.size();
      if(cumpleLasDimensiones(tamañoHorizontal,tamañoVertical) && cumpleLascondiciones(horizontal.getFirst(), horizontal.getLast(), vertical.getFirst(), vertical.getLast()))
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

    private boolean cumpleLasDimensiones(int tamañoHorizontal, int tamañoVertical)
    {
       return (tamañoHorizontal>=3 && tamañoVertical>=3);
    }

    private boolean cumpleLascondiciones(Entidad primeraEntidadHorizontal, Entidad ultimaEntidadHorizontal, Entidad primeraEntidadVertical, Entidad ultimaEntidadVertical)
    {
      return !(primeraEntidadHorizontal.equals(ultimaEntidadVertical)) && !(primeraEntidadHorizontal.equals(primeraEntidadVertical)) && !(primeraEntidadVertical.equals(ultimaEntidadHorizontal)) && !(primeraEntidadVertical.equals(primeraEntidadHorizontal));
    }

    public void set_prioridad(int prioridad)
    {
      this.prioridad=prioridad;
    }

    public int get_prioridad()
    {
      return prioridad;
    }

    public String get_NombreRegla()
    {
      return ("S");
    }
}
