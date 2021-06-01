package de.ostfalia.prog.s21ss.s1.model;

import de.ostfalia.prog.s21ss.s1.base.Farbe;

public class Wuerfel {
	Farbe color;
	int value;

	public Wuerfel(Farbe color, int value) {
		this.color = color;
		this.value = value;
	}

	public Farbe getColor() {
		return color;
	}

	public int getValue() {
		return value;
	}
}
