package de.ostfalia.prog.s21ss.s3.model.wettgeschaeft;

import de.ostfalia.prog.s21ss.s3.base.Charakter;
import de.ostfalia.prog.s21ss.s3.base.Farbe;

public class Wette {
	private Charakter charakter;
	private Wettentyp typ;
	private Farbe farbe;
	private int wert;

	public Wette(Charakter charakter, Wettentyp typ, Farbe farbe) {
		this.charakter = charakter;
		this.typ = typ;
		this.farbe = farbe;
	}

	public Wette(Charakter charakter, Wettentyp typ, Farbe farbe, int wert) {
		this.charakter = charakter;
		this.typ = typ;
		this.farbe = farbe;
		this.wert = wert;
	}

	public Charakter getCharakter() {
		return charakter;
	}

	public Wettentyp getTyp() {
		return typ;
	}

	public Farbe getFarbe() {
		return farbe;
	}

	public int getWert() {
		return wert;
	}
}
