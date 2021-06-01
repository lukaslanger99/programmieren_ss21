package de.ostfalia.prog.s21ss.s3.model;

import java.util.AbstractList;
import java.util.Collections;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.ostfalia.prog.s21ss.s3.base.Farbe;

public class Pyramide {
	private static final Pyramide OBJ = new Pyramide();
	private static Queue<Wuerfel> wuerfelStack = new ConcurrentLinkedQueue<>();

	/***
	 * Konstruktor
	 */
	private Pyramide() {
	}

	/***
	 * liefert den wuerfel
	 * 
	 * @return wuerfelstack
	 */
	public static Queue<Wuerfel> getWuerfel() {
		return wuerfelStack;
	}

	/***
	 * liefert instanz
	 * 
	 * @return obj
	 */
	public static Pyramide getInstance() {
		return OBJ;
	}

	/***
	 * pyramide befüllen
	 * 
	 * @param values
	 */
	public void initialisiere(String... values) {
		wuerfelStack.clear();
		for (String feld : values) {
			feld = feld.trim();
			String[] farbeUndFeld = feld.split(":");
			wuerfelStack.add(new Wuerfel(Farbe.valueOf(farbeUndFeld[0]), Integer.parseInt(farbeUndFeld[1])));
		}
	}

	/***
	 * pyramide mit random werten befüllem
	 */
	public void initialisiereRandom() {
		wuerfelStack.clear();
		Farbe[] farbenArray = { Farbe.BLAU, Farbe.GELB, Farbe.GRUEN, Farbe.ORANGE, Farbe.WEISS };
		shuffle(farbenArray);
		Random r = new Random();
		for (Farbe farbe : farbenArray) {
			wuerfelStack.add(new Wuerfel(farbe, r.nextInt(3) + 1));
		}
	}

	/***
	 * array shuffeln
	 * 
	 * @param array
	 */
	private void shuffle(Farbe[] array) {
		Collections.shuffle(new AbstractList<Farbe>() {
			@Override
			public Farbe get(int index) {
				return array[index];
			}

			@Override
			public int size() {
				return array.length;
			}

			@Override
			public Farbe set(int index, Farbe element) {
				Farbe result = array[index];
				array[index] = element;
				return result;
			}
		});
	}

	/***
	 * liefert wie viele wuerfel noch in der pyramide sind
	 * 
	 * @return size
	 */
	public static int anzahlWuerfel() {
		return wuerfelStack.size();
	}

	/***
	 * 1x würfeln
	 * 
	 * @return wuerfel
	 */
	public static Wuerfel wuerfeln() {
		return wuerfelStack.poll();

	}
}
