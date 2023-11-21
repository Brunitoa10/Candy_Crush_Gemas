

public class Estrategia_Match_3 implements  Estrategia {

    public boolean cumple_con_la_Estrategia(int vertical, int horizontal) {
        return (vertical==3 || horizontal==3);
    }

    @Override
    public boolean crea_Gema_Especial(int Vertical, int Hor) {
        return false;
    }
    
}
