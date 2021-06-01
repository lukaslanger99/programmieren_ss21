package de.ostfalia.prog.s21ss.s1.model;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.ostfalia.prog.s21ss.s1.base.Farbe;

public class Pyramide {
	private static final Pyramide OBJ = new Pyramide();
	private static Queue<Wuerfel> wuerfelStack = new ConcurrentLinkedQueue<>();

	private Pyramide() {
	}

	public static Queue<Wuerfel> getWuerfel() {
		return wuerfelStack;
	}

	public static Pyramide getInstance() {
		return OBJ;
	}

	public void initialisiere(String... values) {
		wuerfelStack.clear();
		for (String feld : values) {
			feld = feld.trim();
			String[] farbeUndFeld = feld.split(":");
			wuerfelStack.add(new Wuerfel(Farbe.valueOf(farbeUndFeld[0]), Integer.parseInt(farbeUndFeld[1])));
		}
	}

	public int anzahlWuerfel() {
		return wuerfelStack.size();
	}

	public static Wuerfel wuerfeln() {
		return wuerfelStack.poll();

	}
}
