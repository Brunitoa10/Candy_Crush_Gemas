package Paneles;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class PanelObjetivo extends JPanel implements Paneles {

    private CentralPaneles miCentral;
    private int[] objetivoColores;
    private JLabel[] objetivoProgreso;

    public PanelObjetivo(CentralPaneles mi_central) {
        miCentral = mi_central;
		objetivoColores = new int[miCentral.getCantidadDeObjetivos()];
		objetivoProgreso = new JLabel[miCentral.getCantidadDeObjetivos()];
    }

    @Override
    public void crearPanel() {
		setSize(100,100);
		setLayout(new GridBagLayout());
		setBackground(new Color(0,0,0,200));
    }

    @Override
    public void agregarAPanelPrincipal() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 0, 0);   
		miCentral.agregarConGBCs((Component)this, gbc, 0, 1, 2, 2);
    }

    public void mostrarObjetivos() {   
	    JLabel tituloObjetivo = miCentral.crearLabelConColor(" OBJETIVOS:", "Algerian", Font.PLAIN, 20, Color.WHITE, 2, 1);
	    GridBagConstraints cTitulo = new GridBagConstraints();
	    cTitulo.insets = new Insets(0, 0, 0, 0);      
	    cTitulo.gridx = 0;                               
	    cTitulo.gridy = 0;
	    add(tituloObjetivo, cTitulo);

	    int coordenada_y = 1;
	    int numeroDeObjetivo = 0;

	    for(int i = 0; i < miCentral.obtenerInfoObjetivos().length; i += 4) {
	        int color = Integer.parseInt(miCentral.obtenerInfoObjetivos()[i + 3]);
	        objetivoColores[numeroDeObjetivo] = color;

	        JLabel objetivosTexto = miCentral.crearLabelConColor(miCentral.obtenerInfoObjetivos()[i], "Algerian", Font.PLAIN, 15, Color.WHITE, 1, 1);
	        JLabel objetivosImagen = miCentral.crearImagen(miCentral.obtenerInfoObjetivos()[i + 1]);
	        JLabel objetivosNumero = miCentral.crearLabelConColor("0/" + miCentral.obtenerInfoObjetivos()[i + 2], "Arial", Font.PLAIN, 15, Color.WHITE, 2, 1);

            GridBagConstraints gbc = new GridBagConstraints();
	        miCentral.agregarConGBCs(objetivosTexto, this, gbc, 0, coordenada_y, 1, 1);
	        miCentral.agregarConGBCs(objetivosImagen, this, gbc, 1, coordenada_y, 1, 1);
	        miCentral.agregarConGBCs(objetivosNumero, this, gbc, 0, coordenada_y + 1, 2, 1);

	        objetivoProgreso[numeroDeObjetivo] = objetivosNumero;

	        coordenada_y += 2;
	        numeroDeObjetivo++;
	    }
	}
    
    public void reiniciarProgreso() {
	    for(int i = 0; i < objetivoColores.length; i++) {
	        String aux = objetivoProgreso[i].getText();
	        String[] partes = aux.split("/");
	        int gemasTotales = Integer.parseInt(partes[1]);

	        objetivoProgreso[i].setText("0/" + gemasTotales);
	    }
	}

    public void actualizarProgreso(int gemasRestantes, int tipoGema) {
		for(int i = 0; i < objetivoColores.length; i++) {
	        if(tipoGema == objetivoColores[i] && objetivoProgreso[i] != null) {
	            String aux = objetivoProgreso[i].getText();
	            String[] partes = aux.split("/");
	            Integer num = Integer.valueOf(partes[1]);
	            int gemasTotales = num.intValue();
	            int progreso = gemasTotales - gemasRestantes;

	            objetivoProgreso[i].setText(progreso + "/" + gemasTotales);
	            this.repaint();
	        }
	    }
	}
    
}
