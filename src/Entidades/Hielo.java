package Entidades;

import EstrategiaDetonaciones.EstategiaDetonacion;
import EstrategiaDetonaciones.EstrategiaDetonacionHielo;
import GUI.EntidadGrafica;
import Logica.Color;
import Tablero.Tablero;
import Tablero.TableroNotificable;

public class Hielo extends Obstaculo {
	protected Entidad caramelo_interno;

	public Hielo(TableroNotificable tablero, int f, int c, Color color, boolean visible, Entidad ent) {
		super(tablero, f, c, "/assets/obstaculo/hielo/", color, visible);
		caramelo_interno = ent;
		cargarImagenesRepresentativas(rutadeLaImagen);
	}

	public void detonar(Tablero t) {
		EstategiaDetonacion estrategia = new EstrategiaDetonacionHielo();
		estrategia.detonar(this, t);
	}

	public void set_gema_interna(Entidad e) {
		caramelo_interno = e;
	}

	public void set_EntidadGrafica_entidadInterna(EntidadGrafica entidadGrafica) {
		caramelo_interno.set_EntidadGrafica(entidadGrafica);
	}

	public void cargarImagenesRepresentativas(String ri) {
		imagenes_representativas = new String[5];
		imagenes_representativas[0] = ri + color.get_color() + ".png";
		imagenes_representativas[1] = ri + color.get_color() + "-cursor.png";
		imagenes_representativas[2] = ri + "detonado.gif";
		imagenes_representativas[3] = ri + "enfocado-detonado.gif";
		imagenes_representativas[4] = caramelo_interno.get_imagen_representativa();
	}

	public int get_score() {
		return color.get_score() + caramelo_interno.get_score();
	}

	public boolean esAfectadaPorExplosionAdyacente() {
		return false;
	}

	public Entidad get_gema_interna() {
		return caramelo_interno;
	}

	public void intercambiar(Entidad entidad) {
		System.out.println("Estoy en hielo en el metodo intercambiar");
		entidad.intercambiar_con(this);
	}

	public void intercambiar_con(GemaNormal gemaNormal) {
		intercambiar_gema_y_hielo(gemaNormal, this);

	}

	public void intercambiar_con(GemaRayada gemaRayada) {
		intercambiar_gema_y_hielo(gemaRayada, this);
	}

	public void intercambiar_con(GemaEnvuelta gemaEnvuelta) {
		intercambiar_gema_y_hielo(gemaEnvuelta, this);
	}

	public void intercambiar_con(GemaCruzada gemaCruzada) {
		intercambiar_gema_y_hielo(gemaCruzada, this);
	}

	public void intercambiar_con(Hielo hielo) {
		intercambiar_hielo_y_hielo(this, hielo);
	}

	public boolean tieneGemaInterna() {
		return true;
	}

	public boolean puede_recibir(GemaNormal c) {
		return true;
	}

	@Override
	public boolean puede_recibir(GemaRayada p) {
		return true;
	}

	@Override
	public boolean puede_recibir(GemaEnvuelta p) {
		return true;
	}

	@Override
	public boolean puede_recibir(GemaCruzada p) {
		return true;
	}

	public boolean puede_recibir(Hielo p) {
		return true;
	}

	@Override
	public boolean es_posible_intercambiar(Entidad e) {
		return e.puede_recibir(this);
	}
}