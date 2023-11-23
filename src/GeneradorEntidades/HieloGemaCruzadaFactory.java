package GeneradorEntidades;

import Entidades.Entidad;
import Entidades.GemaCruzada;
import Entidades.Hielo;
import Logica.Color;
import Tablero.TableroJuego;

public class HieloGemaCruzadaFactory implements EntidadFactory{

    public Entidad crear(TableroJuego tablero, int i, int j, String[] partes, String skin) {
       Entidad aux=new GemaCruzada(tablero, i, j, new Color(Integer.parseInt(partes[1].trim())), true,skin);
       return new Hielo(tablero, i, j, new Color(Color.HIELO), false, aux);
    }
    
}
