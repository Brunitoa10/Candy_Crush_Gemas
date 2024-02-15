package DeteccionDeCombos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import Entidades.Entidad;
import GeneradorEntidades.EntidadCaramelo;
import GeneradorEntidades.EntidadFactory;
import GeneradorEntidades.EntidadPotenciadorHorizontal;
import GeneradorEntidades.EntidadPotenciadorVertical;
import Logica.EfectosDeTransicion;
import Tablero.Tablero;

public class ComboHandler {
    private final Tablero tablero;
    private final int HORIZONTAL = 0;
    private final int VERTICAL = 1;
    
    public ComboHandler(Tablero tablero) {
        this.tablero = tablero;
    }

    public void analizarCombos(EfectosDeTransicion efectoTransicion, Entidad entidad_origen,Entidad entidad_destino) {
        //Map<Integer, List<Entidad>> entidadesPorColor = agruparEntidadesPorColor(buscarCombos(entidad_origen.get_fila(), entidad_origen.get_columna(), entidad_destino.get_fila(), entidad_destino.get_columna()));
        LinkedList<Entidad> listaCombos = buscarCombos(entidad_origen.get_fila(), entidad_origen.get_columna());
        listaCombos.addAll(buscarCombos(entidad_destino.get_fila(), entidad_destino.get_columna()));

        Map<Integer, List<Entidad>> entidadesPorColor = agruparEntidadesPorColor(listaCombos);

        for (List<Entidad> grupoEntidades : entidadesPorColor.values()) {
            determinarTipoMatch(efectoTransicion, grupoEntidades);
        }
    }

    private Map<Integer, List<Entidad>> agruparEntidadesPorColor(LinkedList<Entidad> listaEntidadesEnCombo) {
        Map<Integer, List<Entidad>> entidadesPorColor = new HashMap<>();
        int color = 0;

        for (Entidad entidad : listaEntidadesEnCombo) {
            color = entidad.get_color();
            entidadesPorColor.computeIfAbsent(color, k -> new ArrayList<>()).add(entidad);
        }

        return entidadesPorColor;
    }

    private void determinarTipoMatch(EfectosDeTransicion efectoTransicion, List<Entidad> grupoEntidades) {
        int size = grupoEntidades.size();

        Map<Integer, Runnable> matchHandlers = new HashMap<>();
        matchHandlers.put(3, () -> manejarMatch3(efectoTransicion, grupoEntidades));
        matchHandlers.put(4, () -> manejarMatch4(efectoTransicion, grupoEntidades));
        matchHandlers.put(5, () -> manejarMatch5o6(efectoTransicion,grupoEntidades));

        if (size >= 3 && matchHandlers.containsKey(size)) {
            matchHandlers.get(size).run();
        }
    }

    private void manejarMatch3(EfectosDeTransicion efectoTransicion, List<Entidad> listaEntidadesEnCombo) {
        System.out.println("manejarMatch3");
        EntidadFactory entidadCaramelo = new EntidadCaramelo();
        for (Entidad e : listaEntidadesEnCombo) {
            efectoTransicion.agregar_entidad_a_detonar_y_reemplazar(e);
            efectoTransicion.agregar_entidad_de_reemplazo(entidadCaramelo.crearEntidad(tablero, e.get_fila(), e.get_columna(),  crearColorRandom()));
        }
    }

    private void manejarMatch4(EfectosDeTransicion efectoTransicion,List<Entidad> listaEntidadesEnCombo) {
        System.out.println("manejarMatch4");
        int direccion = evaluarDireccion(listaEntidadesEnCombo);
        EntidadFactory entidadFactory;
        boolean creacionPotenciador = false;

        for (Entidad e : listaEntidadesEnCombo) {
            if(direccion == HORIZONTAL && !creacionPotenciador){
                entidadFactory = new EntidadPotenciadorHorizontal();
                efectoTransicion.agregar_entidad_a_detonar_y_reemplazar(e);
                efectoTransicion.agregar_entidad_de_reemplazo(entidadFactory.crearEntidad(tablero, e.get_fila(), e.get_columna(), e.get_color()));
                creacionPotenciador = true;
            }else{
                if(direccion == VERTICAL && !creacionPotenciador){
                    entidadFactory = new EntidadPotenciadorVertical();
                    efectoTransicion.agregar_entidad_a_detonar_y_reemplazar(e);
                    efectoTransicion.agregar_entidad_de_reemplazo(entidadFactory.crearEntidad(tablero, e.get_fila(), e.get_columna(), e.get_color()));
                    creacionPotenciador = true;
                }else{
                    entidadFactory = new EntidadCaramelo();
                    efectoTransicion.agregar_entidad_a_detonar_y_reemplazar(e);
                    efectoTransicion.agregar_entidad_de_reemplazo(entidadFactory.crearEntidad(tablero, e.get_fila(), e.get_columna(), crearColorRandom()));
                }
            } 
        }
    }

    private void manejarMatch5o6(EfectosDeTransicion efectoTransicion,List<Entidad> listaEntidadesEnCombo) {
        // Lógica específica para Match5 o superior
        System.out.println("manejarMatch5OMas");
       
    }

    private int evaluarDireccion(List<Entidad> listaEntidadesEnCombo) {
       int direccion = VERTICAL;
       boolean evaluacion = false;
       Entidad entidadA,entidadB; 
       for(int pos = 0; pos < listaEntidadesEnCombo.size() && !evaluacion; pos++){
            entidadA = listaEntidadesEnCombo.get(pos);
            entidadB = listaEntidadesEnCombo.get(pos+1);
            if(sonEntidadesMismoColor(entidadA,entidadB)){
                if(sonEntidesHorizontales(entidadA,entidadB)){
                    direccion = HORIZONTAL;
                    evaluacion = true;
                }else{
                    if(sonEntidadesVerticales(entidadA,entidadB)){
                        direccion = VERTICAL;
                        evaluacion = true;
                    }
                }
            }
        }
       return direccion;
    }

    private boolean sonEntidadesVerticales(Entidad entidadA, Entidad entidadB) {
        return entidadA.get_columna() == entidadB.get_columna();
    }

    private boolean sonEntidesHorizontales(Entidad entidadA, Entidad entidadB) {
        return entidadA.get_fila() == entidadB.get_fila();
    }

    private boolean sonEntidadesMismoColor(Entidad entidadA, Entidad entidadB) {
        return entidadA.get_color() == entidadB.get_color();
    }

    private int crearColorRandom() {
        return new Random().nextInt(4) + 1;
    }

    private LinkedList<Entidad> buscarCombosEnFila(int fila, int columna) {
		LinkedList<Entidad> combosEnFila = new LinkedList<>();
		
		Entidad entidadFila = tablero.obtenerEntidad(fila, columna);

		// Contador para el número de gemas iguales consecutivas
		int cantidad = 1;
        
		// Buscar hacia la izquierda
		int colIzquierda = columna - 1;
		while (colIzquierda >= 0 && sonEntidadesMismoColor(tablero.obtenerEntidad(fila, colIzquierda),entidadFila)) {
            combosEnFila.add(tablero.obtenerEntidad(fila, colIzquierda)); 
			cantidad++;
			colIzquierda--;
		}
		// Buscar hacia la derecha
		int colDerecha = columna + 1;
		while (colDerecha < tablero.get_columnas() && sonEntidadesMismoColor(tablero.obtenerEntidad(fila, colDerecha),entidadFila)) {
			combosEnFila.add(tablero.obtenerEntidad(fila, colDerecha)); 
			cantidad++;
			colDerecha++;
		}
		// Si hay al menos 3 gemas iguales consecutivas, agregar la posición actual
		if (cantidad >= 3) {
			combosEnFila.add(entidadFila); 
		} else {
			combosEnFila.clear();
		}

		return combosEnFila;
	}
	

	private LinkedList<Entidad> buscarCombosEnColumna(int fila, int columna) {
		LinkedList<Entidad> combosEnColumna = new LinkedList<>();
		
		Entidad entidadColumna = tablero.obtenerEntidad(fila, columna);
		// Contador para el número de gemas iguales consecutivas
		int cantidad = 1;
		// Buscar hacia arriba
		int filaArriba = fila - 1;
		while (filaArriba >= 0 && sonEntidadesMismoColor(tablero.obtenerEntidad(filaArriba, columna),entidadColumna)) {
			combosEnColumna.add(tablero.obtenerEntidad(filaArriba, columna));
			cantidad++;
			filaArriba--;
		}
		// Buscar hacia abajo
		int filaAbajo = fila + 1;
		while (filaAbajo < tablero.get_filas() && sonEntidadesMismoColor(tablero.obtenerEntidad(filaAbajo,columna),entidadColumna)) {
			combosEnColumna.add(tablero.obtenerEntidad(filaAbajo, columna)); 
			cantidad++;
			filaAbajo++;
		}

		// Si hay al menos 3 gemas iguales consecutivas, agregar la posición actual
		if (cantidad >= 3) {
			combosEnColumna.add(entidadColumna); 
		} else {
			combosEnColumna.clear(); 
		}
		return combosEnColumna;
	}

    /*private LinkedList<Entidad> buscarCombos(int f1, int c1, int f2, int c2) {
		LinkedList<Entidad> listaCombos = new LinkedList<>();

		listaCombos.addAll(buscarCombosEnFila(f1, c1));
		listaCombos.addAll(buscarCombosEnColumna(f1, c1));
		listaCombos.addAll(buscarCombosEnFila(f2, c2));
		listaCombos.addAll(buscarCombosEnColumna(f2, c2));
       
		return listaCombos;
	}*/
    private LinkedList<Entidad> buscarCombos(int fila, int columna) {
        LinkedList<Entidad> combosEnFila = buscarCombosEnFila(fila, columna);
        LinkedList<Entidad> combosEnColumna = buscarCombosEnColumna(fila, columna);
    
        // Combinar las listas sin duplicar las entidades
        Set<Entidad> combinedCombos = new HashSet<>(combosEnFila);
        combinedCombos.addAll(combosEnColumna);
    
        return new LinkedList<>(combinedCombos);
    }
}
