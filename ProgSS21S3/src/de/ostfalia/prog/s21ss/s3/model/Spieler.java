package de.ostfalia.prog.s21ss.s3.model;

import de.ostfalia.prog.s21ss.s3.base.Charakter;

public class Spieler {
	private Charakter charakter;
	private int feldnummer = 0;
	private int guthaben = 3;

	/***
	 * Konstruktor
	 * 
	 * @param charakter
	 */
	public Spieler(Charakter charakter) {
		this.charakter = charakter;
	}

	/***
	 * toString
	 */
	@Override
	public String toString() {
		return "guthaben=" + guthaben;
	}

	/***
	 * liefert Charakter
	 * 
	 * @return charakter
	 */
	public Charakter getCharakter() {
		return charakter;
	}

	/***
	 * liefert Feldnummer
	 * 
	 * @return feldnummer
	 */
	public int getFeldnummer() {
		return feldnummer;
	}

	/***
	 * liefert guthaben
	 * 
	 * @return guthaben
	 */
	public int getGuthaben() {
		return guthaben;
	}

	/***
	 * setzt guthaben
	 * 
	 * @param guthaben
	 */
	public void setGuthaben(int guthaben) {
		this.guthaben = guthaben;
	}

	/***
	 * setzt feldnummer
	 * 
	 * @param feldnummer
	 */
	public void setFeldnummer(int feldnummer) {
		this.feldnummer = feldnummer;
	}

	/***
	 * f?gt guthaben hinzu ( auch negativ )
	 * 
	 * @param amount
	 */
	public void addGuthaben(int amount) {
		guthaben += amount;
	}
}
