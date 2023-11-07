package EstrategiaDetonaciones;

import Entidades.Entidad;
import Logica.Color;
import Tablero.Tablero;

public class EstrategiaDetonacionGemaCruz implements EstategiaDetonacion{

	@Override
	public void detonar(Entidad entidad, Tablero tablero) {
		    int topecolumna=tablero.getFila();
            int topefila=tablero.getColumna();
            int columna=entidad.get_columna();
			int fila=entidad.get_fila();

            for(int i=0;i<topefila;i++)
		    { 
			    if(i!=columna && tablero.get_entidad(fila,i).get_color()!=Color.TRANSPARENTE)
			    {
                 tablero.get_entidad(fila,i).detonar(tablero);
			    }
		    }
            for(int j=0;j<topecolumna;j++)
		    {
                if(j!=fila && tablero.get_entidad(j,columna).get_color()!=Color.TRANSPARENTE)
			    {
                 tablero.get_entidad(j,columna).detonar(tablero);
			    }
		    }
			entidad.set_color(Color.TRANSPARENTE);
			entidad.cargarImagenesRepresentativas(entidad.get_rutadeLaImagen());
			entidad.get_EntidadGrafica().notificarse_detonacion();
		
	}

}
