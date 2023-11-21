

public class Estrategia_Match_4 implements Estrategia {

    public boolean cumple_con_la_Estrategia(int vertical, int horizontal) {
        return (vertical==4 || horizontal==4);
    }

    public boolean crea_Gema_Especial(int vertical, int horizontal) {
        return (cumple_con_la_Estrategia(vertical, horizontal));
    }
    
}
