package de.ostfalia.prog.s21ss.s4.model.wettgeschaeft;

import de.ostfalia.prog.s21ss.s4.base.Charakter;
import de.ostfalia.prog.s21ss.s4.base.Farbe;

public class Wette {
	@Override
	public String toString() {
		return "Wette [" + charakter + "," + typ + "," + farbe + "," + wert + "]";
	}

	private Charakter charakter;
	private Wettentyp typ;
	private Farbe farbe;
	private int wert;

	/***
	 * konstruktor
	 * 
	 * @param charakter
	 * @param typ
	 * @param farbe
	 */
	public Wette(Charakter charakter, Wettentyp typ, Farbe farbe) {
		this.charakter = charakter;
		this.typ = typ;
		this.farbe = farbe;
	}

	/***
	 * konstruktor
	 * 
	 * @param charakter
	 * @param typ
	 * @param farbe
	 * @param wert
	 */
	public Wette(Charakter charakter, Wettentyp typ, Farbe farbe, int wert) {
		this.charakter = charakter;
		this.typ = typ;
		this.farbe = farbe;
		this.wert = wert;
	}

	/***
	 * getter
	 * 
	 * @return charakter
	 */
	public Charakter getCharakter() {
		return charakter;
	}

	/***
	 * getter
	 * 
	 * @return typ
	 */
	public Wettentyp getTyp() {
		return typ;
	}

	/***
	 * getter
	 * 
	 * @return farbe
	 */
	public Farbe getFarbe() {
		return farbe;
	}

	/***
	 * getter
	 * 
	 * @return wert
	 */
	public int getWert() {
		return wert;
	}
}
