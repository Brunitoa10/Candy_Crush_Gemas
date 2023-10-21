package Entidades;


public class GemaEnvuelta extends Gema{

	public GemaEnvuelta(int f, int c, int col) {
		super(f, c, col, "/assets/gemas/gema_envuelta/");
	}

	
	/*public void romper() {
		int f=fila;
		int c=columna;
		int topeFila=miTablero.getFila();
		int topeColumna=miTablero.getColumna();

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
				}
				else
				{
					if(miTablero.getEntidad(i, columna).obtenerColor()!=0)
					{
						miTablero.getEntidad(i, j).romper(miTablero);
					}
				}
			}
		}

	}*/


	@Override
	public boolean es_posible_intercambiar(Entidad e) {
		// TODO Auto-generated method stub
		return e.puede_recibir(this);
	}

	@Override
	public boolean puede_recibir(GemaNormal c) {
		// TODO Auto-generated method stub
		return true; 
	}

	@Override
	public boolean puede_recibir(Hielo g) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean puede_recibir(GemaRayada p) {
		// TODO Auto-generated method stub
		return true; 
	}

	@Override
	public boolean puede_recibir(GemaEnvuelta p) {
		// TODO Auto-generated method stub
		return true; 
	}

	@Override
	public boolean machea(Entidad e) {
		// TODO Auto-generated method stub
		return e.match_con(this);
	}

	@Override
	public boolean match_con(GemaNormal c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean match_con(GemaRayada p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean match_con(GemaEnvuelta p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean match_con(Hielo g) {
		// TODO Auto-generated method stub
		return false;
	}
}
