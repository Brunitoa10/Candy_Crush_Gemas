package DeteccionDeCombos;

import java.util.List;

import Entidades.Entidad;

public class VerificarFormaT implements VerificadorFormas{

    @Override
    public boolean verificarForma(List<Entidad> listaEntidades) {
        // Verificar si hay exactamente 5 caramelos en la lista
        if (listaEntidades.size() == 5) {
            // Obtener las coordenadas de los caramelos en la lista
            int[] coordenadasX = new int[5];
            int[] coordenadasY = new int[5];

            for (int i = 0; i < 5; i++) {
                coordenadasX[i] = listaEntidades.get(i).get_fila();
                coordenadasY[i] = listaEntidades.get(i).get_columna();
            }

            // Verificar si las coordenadas forman una T
            if (verificarCoordenadasT(coordenadasX, coordenadasY)) {
                return true;
            }
        }
        return false;
    }

    private boolean verificarCoordenadasT(int[] coordenadasX, int[] coordenadasY) {
        // Lógica específica para verificar si las coordenadas forman una T
        // Por ejemplo, podrías verificar si hay un caramelo en el centro y otros
        // en la parte superior, inferior, izquierda y derecha del centro.
        
        // Aquí tienes un ejemplo simple:
        int centroX = coordenadasX[0];
        int centroY = coordenadasY[0];

        // Verificar si hay caramelos en posiciones específicas para formar una T
        boolean parteSuperior = contieneCoordenada(coordenadasX, coordenadasY, centroX, centroY - 1);
        boolean parteInferior = contieneCoordenada(coordenadasX, coordenadasY, centroX, centroY + 1);
        boolean parteIzquierda = contieneCoordenada(coordenadasX, coordenadasY, centroX - 1, centroY);
        boolean parteDerecha = contieneCoordenada(coordenadasX, coordenadasY, centroX + 1, centroY);

        return parteSuperior && parteInferior && parteIzquierda && parteDerecha;
    }

    private boolean contieneCoordenada(int[] coordenadasX, int[] coordenadasY, int x, int y) {
        // Verificar si las coordenadas X e Y contienen la posición (x, y)
        for (int i = 0; i < coordenadasX.length; i++) {
            if (coordenadasX[i] == x && coordenadasY[i] == y) {
                return true;
            }
        }
        return false;
    }
    
}
