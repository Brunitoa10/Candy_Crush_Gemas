package Entidades;

import Logica.Color;
import Tablero.Tablero;

public class GemaEnvuelta extends Gema{

	public GemaEnvuelta(int f, int c, int col) {
		super(f, c, col, "/assets/gemas/gema_envuelta/");
	}

	
	@Override
	public void detonar(Tablero tablero) {
		int f=fila;
		int c=columna;
		int topeFila=tablero.getFila();
		int topeColumna=tablero.getColumna();

		if(f!=0)
		{
			f=fila-1;
		}

		if(c!=0)
		{
			c=columna-1;
		}

        if(topeFila-1!=f)
		{
          topeFila=fila+1;		
		}

		if(topeColumna-1!=c)
		{
		   topeColumna=columna+1;
		}

		for(int i= f; i<topeFila; i++){
		  	for(int j = c; j<topeFila; j++){
				if(i==fila && j==columna)
				{
						System.out.println("destruido gema envuelta "+ this.color + " en: "+fila+","+columna );
						color = Color.TRANSPARENTE;
						cargarImagenesRepresentativas(ruta);
						entidadG.notificarse_explosion();
						//tablero.caida(this);
				}
				else
				{
					if(tablero.get_entidad(i, columna).get_color()!=0)
					{
						tablero.get_entidad(i, j).detonar(tablero);
					}
				}
			}
		}

	}

	public void explosionAdyacente()
	{}

	public boolean es_posible_intercambiar(Entidad e) {
		return e.puede_recibir(this);
	}

	public boolean machea(Entidad e) 
	{
		return e.match_con(this);
	}

	public int get_score()
	{
		return 50;
	}
}
