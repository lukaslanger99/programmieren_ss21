package de.ostfalia.prog.s21ss.s3.model.wettgeschaeft;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import de.ostfalia.prog.s21ss.s3.base.Farbe;

public class Wettplaettchen {
	private Map<Farbe, Deque<Integer>> wettplaettchen = new HashMap<Farbe, Deque<Integer>>();

	public Wettplaettchen() {
		init();
	}

	public void init() {
		wettplaettchen.clear();
		for (Farbe farbe : Farbe.values()) {
			Deque<Integer> farbenStapel = new ArrayDeque<Integer>();
			farbenStapel.add(5);
			farbenStapel.add(3);
			farbenStapel.add(2);
			wettplaettchen.put(farbe, farbenStapel);
		}
	}

	public Integer getNext(Farbe farbe) {
		return wettplaettchen.get(farbe).pop();
	}
}
