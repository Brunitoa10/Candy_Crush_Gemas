package Entidades;

public class GemaEnvuelta extends Gema{

	public GemaEnvuelta(int f, int c, int col) {
		super(f, c, col, "/assets/gemas/gema_envuelta/");
	}

	@Override
	public void romper(Entidad e) {
		int fila=e.getFila();
		int columna=e.getColumna();
		int topeFila=miTablero.getFila();
		int topeColumna=miTablero.getColumna();
		int i=0;
		int j=0;

		if(fila!=0)
		{
			i=fila-1;
		}

		if(columna!=0)
		{
			j=columna-1;
		}

        if(topeFila-1!=fila)
		{
          topeFila=fila+1;		
		}

		if(topeColumna-1!=columna)
		{
			topeColumna=columna+1;
		}

		while(i!=topeFila)
		{
          int c=j;
          while(c!=topeColumna)
		  {
			if(i==fila && c==columna)
			{
				miTablero.getEntidad(i,c).setImagenesRep("0");
			}
			else
			{
                miTablero.getEntidad(i, c).destruir();
			}
			c=c+1;
		  }
		  i=i++;
		}
	}

}
