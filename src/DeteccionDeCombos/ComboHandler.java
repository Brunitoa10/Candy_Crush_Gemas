package DeteccionDeCombos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    public void analizarCombos(EfectosDeTransicion efectoTransicion, LinkedList<Entidad> listaEntidadesEnCombo) {
        Map<Integer, List<Entidad>> entidadesPorColor = agruparEntidadesPorColor(listaEntidadesEnCombo);

        for (List<Entidad> grupoEntidades : entidadesPorColor.values()) {
            determinarTipoMatch(efectoTransicion, grupoEntidades);
        }
    }

    private Map<Integer, List<Entidad>> agruparEntidadesPorColor(LinkedList<Entidad> listaEntidadesEnCombo) {
        Map<Integer, List<Entidad>> entidadesPorColor = new HashMap<>();

        for (Entidad entidad : listaEntidadesEnCombo) {
            int color = entidad.get_color();
            entidadesPorColor.computeIfAbsent(color, k -> new ArrayList<>()).add(entidad);
        }

        return entidadesPorColor;
    }

    private void determinarTipoMatch(EfectosDeTransicion efectoTransicion, List<Entidad> grupoEntidades) {
        int size = grupoEntidades.size();

        if (size >= 3) {
            switch (size) {
                case 3:
                    manejarMatch3(efectoTransicion, grupoEntidades);
                    break;
                case 4:
                    manejarMatch4(efectoTransicion,grupoEntidades);
                    break;
                default:
                    manejarMatch5OMas(grupoEntidades);
                    break;
            }
        } else {
            System.out.println("No se produjo un match.");
        }
    }

    private void manejarMatch3(EfectosDeTransicion efectoTransicion, List<Entidad> listaEntidadesEnCombo) {
        System.out.println("manejarMatch3");
        EntidadFactory entidadCaramelo = new EntidadCaramelo();
        for (Entidad e : listaEntidadesEnCombo) {
            efectoTransicion.agregar_entidad_a_detonar_y_reemplazar(e);
            efectoTransicion.agregar_entidad_de_reemplazo(entidadCaramelo.crearEntidad(tablero, e.get_fila(), e.get_columna(), new Random().nextInt(4) + 1));
        }
    }

    private void manejarMatch4(EfectosDeTransicion efectoTransicion,List<Entidad> listaEntidadesEnCombo) {
        // Lógica específica para Match4
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
                    efectoTransicion.agregar_entidad_de_reemplazo(entidadFactory.crearEntidad(tablero, e.get_fila(), e.get_columna(), new Random().nextInt(4) + 1));
                }
            }   
        }
    }

    private void manejarMatch5OMas(List<Entidad> listaEntidadesEnCombo) {
        // Lógica específica para Match5 o superior
        System.out.println("manejarMatch5OMas");
    }

    private void manejarMatch(EfectosDeTransicion efectoTransicion, Entidad entidad) {
        EntidadFactory entidadCaramelo = new EntidadCaramelo();
        efectoTransicion.agregar_entidad_a_detonar_y_reemplazar(entidad);
        efectoTransicion.agregar_entidad_de_reemplazo(entidadCaramelo.crearEntidad(tablero, entidad.get_fila(), entidad.get_columna(), new Random().nextInt(4) + 1));
    }

    private int evaluarDireccion(List<Entidad> listaEntidadesEnCombo) {
       int direccion = VERTICAL;
       boolean evaluacion = false;
       Entidad entidadA,entidadB; 
       for(int pos = 0; pos < listaEntidadesEnCombo.size() && !evaluacion; pos++){
            entidadA = listaEntidadesEnCombo.get(pos);
            entidadB = listaEntidadesEnCombo.get(pos+1);
            if(entidadA.get_color() == entidadB.get_color()){
                if(entidadA.get_fila() == entidadB.get_fila()){
                    direccion = HORIZONTAL;
                    evaluacion = true;
                }else{
                    if(entidadA.get_columna() == entidadB.get_columna()){
                        direccion = VERTICAL;
                        evaluacion = true;
                    }
                }
            }
        }
       return direccion;
    }
}
