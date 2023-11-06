package Logica;

public class Color {
	//en caso de cambiar los valores, avisenle a Nacho para cambiar los nombres de los assets :)
	public static final int TRANSPARENTE = 0;
	public static final int ROJO = 1;
	public static final int NARANJA = 2;
	public static final int AMARILLO = 3;
	public static final int VERDE = 4;
	public static final int AZUL = 5;
	public static final int PURPURA = 6;
	public static final int ROCA = 7;
	public static final int HIELO = 8;
	public static final int BOMBA=9;
    protected int score;
	protected int color;

	public Color(Integer color)
	{
	  this.color=color;

	}

	public int get_color()
	{
		return color;
	}

	public int get_score()
	{
			int score=0;
			if(color==TRANSPARENTE)
			{
				score=0;
			}
			if (color==ROJO)
			{
     	        score=5;
			}
			if(color==NARANJA)
			{
				score=15;
			}
			if(color==AMARILLO || color==AZUL)
			{
				score=20;
			}
			if(color==VERDE)
			{
				score=10;
			}
			if(color==PURPURA)
			{
				score=25;
			}
			if(color==ROCA)
			{
				score=25;
			}
			if(color==BOMBA)
			{
				score=150;
			}
			return score;
	}

	public void set_score(int score)
	{
		this.score=score;
	}

	public void set_color(int color)
	{
		this.color=color;
	}
}
