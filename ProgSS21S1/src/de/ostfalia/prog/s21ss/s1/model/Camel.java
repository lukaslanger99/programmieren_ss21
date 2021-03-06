package de.ostfalia.prog.s21ss.s1.model;

import de.ostfalia.prog.s21ss.s1.base.Farbe;

public class Camel {
	private Farbe farbe;
	private int feldnummer;
	private boolean finished = false;

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

	public void setPosition(int i) {
		feldnummer = i % 17;
		if (i > 16) {
			feldnummer++;
			finished = true;
		}
	}

	public int getPosition() {
		return feldnummer;
	}

	public Farbe getColor() {
		return farbe;
	}

	public Boolean getFinished() {
		return finished;
	}
}
