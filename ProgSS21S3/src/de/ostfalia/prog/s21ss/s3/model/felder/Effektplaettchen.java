package de.ostfalia.prog.s21ss.s3.model.felder;

import de.ostfalia.prog.s21ss.s3.base.Charakter;

public class Effektplaettchen {
	private Plaettchen typ;
	private Charakter owner;

	public Effektplaettchen(Plaettchen typ, Charakter owner) {
		this.typ = typ;
		this.owner = owner;
	}

	public Plaettchen getTyp() {
		return typ;
	}

	public Charakter getOwner() {
		return owner;
	}

	@Override
	public String toString() {
		return "[" + typ + ":" + owner + "]";
	}
}
