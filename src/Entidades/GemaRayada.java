package Entidades;
public class GemaRayada extends Gema{
	
	//direccion en la que la gema explotara
	//en caso de cambiar los valores de horizontal y vertical, adaptar el nombre de los assets en /assets/gema_rayada/
	private int d;
	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;

	public GemaRayada(int f, int c, int col, int direccion) {
		super(f, c,col, "/assets/gemas/gema_rayada/" + direccion);
	}
	
	public int getDireccion(){
		return d;
	}

	@Override
	public void romper(Entidad e) {
		int fila=e.getFila();
		int columna=e.getColumna();
        int tope=0;
		GemaRayada aux=(GemaRayada) e;
       if(aux.getDireccion()==0) //es Horizontal
	   {
          tope=miTablero.getColumna();
		  for(int i=0;i<tope;i++)
		  {
            if(i==columna)
			{
              miTablero.getEntidad(fila,i).setImagenesRep("0");
			}
			else
			{
			miTablero.getEntidad(fila, i).destruir();	
			}
		  }
	   }
	   else //es Vertical
	   {
          tope=miTablero.getFila();
		  for(int i=0;i<tope;i++)
		  {
            if(i==fila)
			{
              miTablero.getEntidad(i,columna).setImagenesRep("0");
			}
			else
			{
			miTablero.getEntidad(fila, i).destruir();	
			}
		  }
	   }
	}

}
