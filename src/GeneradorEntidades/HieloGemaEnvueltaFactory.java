package GeneradorEntidades;

import Entidades.*;
import Logica.Color;
import Tablero.TableroJuego;

public class HieloGemaEnvueltaFactory implements EntidadFactory{

    public Entidad crear(TableroJuego tablero, int i, int j, String[] partes, String skin) {
       Entidad aux=new GemaEnvuelta(tablero, i, j, new Color(Integer.parseInt(partes[1].trim()) / 10), true, skin);
       return new Hielo(tablero, i, j, new Color(Color.HIELO), false, aux);
    }
    
}
