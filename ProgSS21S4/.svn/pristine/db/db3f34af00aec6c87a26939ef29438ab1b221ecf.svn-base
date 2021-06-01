package de.ostfalia.prog.s21ss.s4.model.wettgeschaeft;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import de.ostfalia.prog.s21ss.s4.model.CamelUp;

public class Wettgeschaeft {
	private List<Wette> etappenWetten = new ArrayList<Wette>();
	private Deque<Wette> olleWetten = new ArrayDeque<Wette>();
	private Deque<Wette> tolleWetten = new ArrayDeque<Wette>();
	private int[] order = { 8, 5, 3, 2, 1 };

	/***
	 * Konstruktor
	 */
	public Wettgeschaeft() {

	}

	/***
	 * printer
	 * 
	 * @return string array
	 */
	public String[] getWetten() {
		String[] wetten = new String[etappenWetten.size() + olleWetten.size() + tolleWetten.size()];
		int counter = 0;
		for (Wette wette : etappenWetten) {
			wetten[counter++] = wette.toString();
		}
		for (Wette wette : olleWetten) {
			wetten[counter++] = wette.toString();
		}
		for (Wette wette : tolleWetten) {
			wetten[counter++] = wette.toString();
		}
		return wetten;
	}

	/***
	 * Wette wird hinzugefügt zum jeweiligen Wettstapel
	 * 
	 * @param wette
	 */
	public void addWette(Wette wette) {
		if (wette.getTyp() == Wettentyp.ETAPPE) {
			etappenWetten.add(wette);
		} else if (wette.getTyp() == Wettentyp.OLL) {
			olleWetten.add(wette);
		} else if (wette.getTyp() == Wettentyp.TOLL) {
			tolleWetten.add(wette);
		}
	}

	/***
	 * Stapel für Etappenwetten abarbeiten
	 * 
	 * @param camelUp
	 */
	private void etappenWettenAbarbeiten(CamelUp camelUp) {
		for (Wette wette : etappenWetten) {
			if (wette.getFarbe() == camelUp.fuehrendesKamel()) {
				// erster -> wert der karte
				camelUp.getChars().get(wette.getCharakter()).addGuthaben(wette.getWert());
			} else if (wette.getFarbe() == camelUp.zweitplatziertesKamel()) {
				// zweiter -> 1 äp
				camelUp.getChars().get(wette.getCharakter()).addGuthaben(1);
			} else {
				// falsches kamel -> -1 äp
				camelUp.getChars().get(wette.getCharakter()).addGuthaben(-1);
			}
		}
		etappenWetten.clear();
	}

	/***
	 * Stapel für olle Wetten abarbeiten
	 * 
	 * @param camelUp
	 */
	private void olleWettenAbarbeiten(CamelUp camelUp) {
		int orderIndex = 0;
		int size = olleWetten.size();
		int amount;
		for (int i = 0; i < size; i++) {
			Wette wette = olleWetten.pop();
			if (wette.getFarbe() == camelUp.letztesKamel()) {
				amount = order[orderIndex++];
			} else {
				amount = -1;
			}
			camelUp.getChars().get(wette.getCharakter()).addGuthaben(amount);
		}
		olleWetten.clear();
	}

	/***
	 * Stapel für tolle Wetten abarbeiten
	 * 
	 * @param camelUp
	 */
	private void tolleWettenAbarbeiten(CamelUp camelUp) {
		int orderIndex = 0;
		int size = tolleWetten.size();
		int amount;
		for (int i = 0; i < size; i++) {
			Wette wette = tolleWetten.pop();
			if (wette.getFarbe() == camelUp.fuehrendesKamel()) {
				amount = order[orderIndex++];
			} else {
				amount = -1;
			}
			camelUp.getChars().get(wette.getCharakter()).addGuthaben(amount);
		}
		tolleWetten.clear();
	}

	/***
	 * Wird nach jeder Etappe aufgerufen
	 * 
	 * @param camelUp
	 */
	public void wettenAbarbeiten(CamelUp camelUp) {
		etappenWettenAbarbeiten(camelUp);
		olleWettenAbarbeiten(camelUp);
		tolleWettenAbarbeiten(camelUp);

	}
}
