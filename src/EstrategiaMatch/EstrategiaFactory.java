package EstrategiaMatch;

import java.util.HashMap;
import java.util.Map;

public class EstrategiaFactory {

    private static final Map<Character, Estrategias> estrategiaMap = new HashMap<>();

    static {
        estrategiaMap.put('4', new EstrategiaMatch4());
        estrategiaMap.put('l', new EstrategiaMatchL());
        estrategiaMap.put('s', new EstrategiaMatchS());
        estrategiaMap.put('t', new EstrategiaMatchT());
    }

    public static Estrategias createEstrategia(char type) {
        return estrategiaMap.get(type);
    }
}