package de.ostfalia.prog.s21ss.s3.model;

import de.ostfalia.prog.s21ss.s3.base.Farbe;

public class Camel {
	private Farbe farbe;
	private int feldnummer;
	private boolean finished = false;

	/***
	 * Konstruktor
	 * 
	 * @param farbe
	 */
	public Camel(Farbe farbe) {
		this.farbe = farbe;
		feldnummer = 0;
	}

	@Override
	public String toString() {
		if (finished) {
			return farbe.toString() + ":f";
		}
		return farbe.toString();
	}

	/***
	 * setzt die Position
	 * 
	 * @param i
	 */
	public void setPosition(int i) {
		feldnummer = i % 17;
		if (i > 16) {
			feldnummer++;
			finished = true;
		}
	}

	/***
	 * liefert Position
	 * 
	 * @return feldnummer
	 */
	public int getPosition() {
		return feldnummer;
	}

	/***
	 * Liefert Farbe
	 * 
	 * @return farbe
	 */
	public Farbe getColor() {
		return farbe;
	}

	/***
	 * liefert ob Kamel im Ziel ist
	 * 
	 * @return finished
	 */
	public Boolean getFinished() {
		return finished;
	}
}
