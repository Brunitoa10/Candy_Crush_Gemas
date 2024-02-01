package Entidades;

public class Color {
    public static final int AZUL = 1;
	public static final int VERDE = 2;
	public static final int NARANJA = 3;
	public static final int VIOLETA = 4;
	public static final int ROJO = 5;
	public static final int NEGRO = 6;
	public static final int TRANSPARENTE = 7;

   
    public static int obtenerColor(int valorEntidad) {
        switch (valorEntidad) {
            case 1:
                return AZUL;
            case 2:
                return VERDE;
            case 3:
                return NARANJA;
            case 4:
                return VIOLETA;
            case 5:
                return ROJO;
            case 6:
                return NEGRO;
            case 7:
                return TRANSPARENTE;
            default:
                // En caso de un valor no reconocido, puedes devolver un valor por defecto
                return VERDE;
        }
    }


}
