package EstrategiaMatch;


import java.util.Iterator;
import java.util.LinkedList;

import Entidades.Entidad;
import Tablero.Tablero;

public class AdministradorEstrategias {
    protected LinkedList<Estrategias> listas_Estrategias;
    protected Tablero miTablero;
    
    public AdministradorEstrategias(LinkedList<Estrategias> estrategias, Tablero miTablero)
    {
       listas_Estrategias=estrategias;
       this.miTablero=miTablero;
    }

	public LinkedList<Estrategias> get_lista_de_Estrategias()
	{
		return listas_Estrategias;
	}

    public Resultado buscar_match(Entidad entidad)
    {
       LinkedList<Entidad> caramelosEnFila=buscarCombosEnFila(entidad.get_fila(), entidad.get_columna());
       LinkedList<Entidad> caramelosEnColumna=buscarCombosEnColumna(entidad.get_fila(),entidad.get_columna());
       Resultado resultado=matchea(caramelosEnFila,caramelosEnColumna, entidad);
       return resultado;
    }

    private Resultado matchea(LinkedList<Entidad> caramelosEnFila,LinkedList<Entidad> caramelosEnColumna, Entidad entidad)
    {
        Resultado resultadoparcial=null;
        Resultado resultadoparcial2=null;
       if(listas_Estrategias.getFirst().get_prioridad()==1)
       {
         resultadoparcial=listas_Estrategias.getFirst().se_Cumple_la_Estrategia(caramelosEnFila, caramelosEnColumna, entidad);
         if(resultadoparcial!=null)
            {
            Iterator<Estrategias> estrategias = listas_Estrategias.iterator();
               estrategias.next();
                   while (estrategias.hasNext() && resultadoparcial2==null) 
                       {
                           resultadoparcial2= estrategias.next().se_Cumple_la_Estrategia(caramelosEnFila, caramelosEnColumna, entidad); 
                          }
                               }
                                 if(resultadoparcial2!=null)
                                    {
                                     resultadoparcial=resultadoparcial2;
                                     }
                                       }
       else
          {
            Iterator<Estrategias> estrategias = listas_Estrategias.iterator();
            while(estrategias.hasNext() && resultadoparcial==null)
            {
                resultadoparcial=estrategias.next().se_Cumple_la_Estrategia(caramelosEnFila, caramelosEnColumna, entidad);
            }
            }
		if(resultadoparcial==null)
		{
			resultadoparcial=gemasNormales(caramelosEnFila,caramelosEnColumna);
		}
       return resultadoparcial;
    }

	private Resultado gemasNormales(LinkedList<Entidad> caramelosEnFila,LinkedList<Entidad> caramelosEnColumna)
	{
		LinkedList<Entidad> toInsert=new LinkedList<>();
		if(caramelosEnColumna.size()==3 && caramelosEnFila.size()<3)
		{
		   for(Entidad caramelo: caramelosEnColumna)
		   {
              toInsert.addLast(caramelo);
		   }
		}
		if(caramelosEnFila.size()==3 && caramelosEnColumna.size()<3)
		{
		   for(Entidad caramelo: caramelosEnFila)
		   {
              toInsert.addLast(caramelo);
		   }
		}
		return new Resultado(toInsert, null);
	}

    private LinkedList<Entidad> buscarCombosEnFila(int fila, int columna) {
		LinkedList<Entidad> combosEnFila = new LinkedList<>();

		// Obtener el tipo de gema en la posición (fila, columna)
		Entidad entidad = miTablero.get_entidad(fila, columna);
		Entidad entidadAinsertar=entidad;
		if(entidad.tieneGemaInterna())
		{
			entidad=entidad.get_gema_interna();
		}

		// Contador para el número de gemas iguales consecutivas
		int cantidad = 1;
        int columnas= miTablero.getColumna();

		// Buscar hacia la izquierda
		int colIzquierda = columna - 1;
		while (colIzquierda >= 0 && cumpleConelColor(miTablero.get_entidad(fila, colIzquierda),entidad)) {
			combosEnFila.add(miTablero.get_entidad(fila, colIzquierda)); // Agregar a la lista de combos
			cantidad++;
			colIzquierda--;
		}

		// Buscar hacia la derecha
		int colDerecha = columna + 1;
		while (colDerecha < columnas && cumpleConelColor(miTablero.get_entidad(fila, colDerecha),entidad)) {
			combosEnFila.add(miTablero.get_entidad(fila, colDerecha)); // Agregar a la lista de combos
			cantidad++;
			colDerecha++;
		}

		// Si hay al menos 3 gemas iguales consecutivas, agregar la posición actual
		if (cantidad >= 3) {
			combosEnFila.add(entidadAinsertar); // Agregar la posición actual a la lista de combos
		} else {
			combosEnFila.clear(); // No hay combos, limpiar la lista
		}

		return combosEnFila;
	}

	private LinkedList<Entidad> buscarCombosEnColumna(int fila, int columna) {
		LinkedList<Entidad> combosEnColumna = new LinkedList<>();

		// Obtener el tipo de gema en la posición (fila, columna)
		Entidad entidad = miTablero.get_entidad(fila, columna);
		Entidad entidadAinsertar=entidad;
		if(entidad.tieneGemaInterna())
		{
			entidad=entidad.get_gema_interna();
		}
		

		// Contador para el número de gemas iguales consecutivas
		int cantidad = 1;
        int filas =miTablero.getFila();

		// Buscar hacia arriba
		int filaArriba = fila - 1;
		while (filaArriba >= 0 && cumpleConelColor(miTablero.get_entidad(filaArriba, columna),entidad)) {
			combosEnColumna.add(miTablero.get_entidad(filaArriba, columna)); // Agregar a la lista de combos
			cantidad++;
			filaArriba--;
		}

		// Buscar hacia abajo
		int filaAbajo = fila + 1;
		while (filaAbajo < filas && cumpleConelColor(miTablero.get_entidad(filaAbajo, columna),entidad)) {
			combosEnColumna.add(miTablero.get_entidad(filaAbajo, columna)); // Agregar a la lista de combos
			cantidad++;
			filaAbajo++;
		}

		// Si hay al menos 3 gemas iguales consecutivas, agregar la posición actual
		if (cantidad >= 3) {
			combosEnColumna.add(entidadAinsertar); // Agregar la posición actual a la lista de combos
		} else {
			combosEnColumna.clear(); // No hay combos, limpiar la lista
		}

		return combosEnColumna;
	}

	private boolean cumpleConelColor(Entidad comparada, Entidad aComparar)
	{
        boolean toRet=false;
		if(comparada.tieneGemaInterna())
		{
           toRet= comparada.get_gema_interna().get_color()==aComparar.get_color();
		}
		else{
			toRet=comparada.get_color()==aComparar.get_color();
		}

		return toRet;
	}
}
