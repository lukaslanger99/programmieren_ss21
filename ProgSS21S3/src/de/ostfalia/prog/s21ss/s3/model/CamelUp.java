package de.ostfalia.prog.s21ss.s3.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.ostfalia.prog.s21ss.s3.base.Charakter;
import de.ostfalia.prog.s21ss.s3.base.Farbe;
import de.ostfalia.prog.s21ss.s3.base.ICamelUp;
import de.ostfalia.prog.s21ss.s3.model.exceptions.UngueltigerZugException;
import de.ostfalia.prog.s21ss.s3.model.exceptions.UngueltigesFeldException;
import de.ostfalia.prog.s21ss.s3.model.felder.Effektplaettchen;
import de.ostfalia.prog.s21ss.s3.model.felder.Plaettchen;
import de.ostfalia.prog.s21ss.s3.model.wettgeschaeft.Wette;
import de.ostfalia.prog.s21ss.s3.model.wettgeschaeft.Wettentyp;
import de.ostfalia.prog.s21ss.s3.model.wettgeschaeft.Wettgeschaeft;
import de.ostfalia.prog.s21ss.s3.model.wettgeschaeft.Wettplaettchen;

public class CamelUp implements ICamelUp {
	private Map<Farbe, Camel> camelMap = new HashMap<Farbe, Camel>();
	private Boolean gameFinished = false;
	private Feld[] spielfeld;
	private Map<Charakter, Spieler> chars = new HashMap<Charakter, Spieler>();
	private Wettgeschaeft wettgeschaeft = new Wettgeschaeft();
	private Wettplaettchen wettplaettchen = new Wettplaettchen();
	private List<Charakter> pyramidenPlaettchen = new ArrayList<>();

	/***
	 * Konstruktor
	 * 
	 * @param characters
	 */
	public CamelUp(Charakter[] characters) {
		initCamels();
		initSpielfeld();
		initChars(characters);
	}

	/***
	 * liefert die Spieler
	 * 
	 * @return
	 */
	public Map<Charakter, Spieler> getChars() {
		return chars;
	}

	/***
	 * liefert anzahl an Kamelen die im Ziel sind
	 * 
	 * @return
	 */
	private int getAnzahlFinished() {
		int counter = 0;
		for (Camel camel : camelMap.values()) {
			if (camel.getFinished()) {
				counter++;
			}
		}
		return counter;
	}

	/***
	 * liefert ob game finished ist
	 * 
	 * @return gamefinished
	 */
	public Boolean getGameFinished() {
		return gameFinished;
	}

	/***
	 * Kamele initialisieren
	 */
	private void initCamels() {
		for (Farbe farbe : Farbe.values()) {
			camelMap.put(farbe, new Camel(farbe));
		}
	}

	/***
	 * Spielfeld initialisieren
	 */
	private void initSpielfeld() {
		spielfeld = new Feld[16];
		for (int i = 0; i < spielfeld.length; i++) {
			spielfeld[i] = new Feld();
		}
	}

	/***
	 * Spieler initialisieren
	 * 
	 * @param characters
	 */
	private void initChars(Charakter[] characters) {
		for (Charakter charakter : characters) {
			chars.put(charakter, new Spieler(charakter));
		}
	}

	@Override
	public String toString() {
		String string = "";
		for (int i = 0; i < spielfeld.length; i++) {
			string += (i + 1) + ":" + spielfeld[i] + " ";
		}
		return string;
	}

	@Override
	public void startPosition() {
		for (int i = 0; i < camelMap.size(); i++) {
			Wuerfel wuerfel = Pyramide.wuerfeln();
			Camel camel = camelMap.get(wuerfel.getColor());
			int wuerfelValue = wuerfel.getValue();

			camel.setPosition(wuerfelValue);
			spielfeld[wuerfelValue - 1].addCamel(camel);
		}
	}

	@Override
	public void startPosition(String... felder) {
		for (String feld : felder) {
			String[] farbeUndFeld = feld.split(":");
			camelMap.get(Farbe.valueOf(farbeUndFeld[0])).setPosition(Integer.parseInt(farbeUndFeld[1]));
			spielfeld[Integer.parseInt(farbeUndFeld[1]) - 1].addCamel(camelMap.get(Farbe.valueOf(farbeUndFeld[0])));
		}
	}

	@Override
	public void bewegeKamel(Charakter charakter) {
		Wuerfel wuerfel = Pyramide.wuerfeln();
		pyramidenPlaettchen.add(charakter);
		Feld currentField = spielfeld[camelMap.get(wuerfel.getColor()).getPosition() - 1];
		if (!gameFinished) {
			currentField.popStack(wuerfel.getColor(), wuerfel.getValue(),
					spielfeld, chars);
			gameFinished = currentField.checkFinish();
			System.out.println("Gew?rfelt: " + wuerfel.getColor() + "," + wuerfel.getValue());
		}

		// wenn etappe vorbei wettplaettchen wieder zur?ck auf die stapel und wetten
		// abarbeiten
		if (Pyramide.anzahlWuerfel() == 0) {
			wettplaettchen.init();
			wettgeschaeft.wettenAbarbeiten(this);

			for (Charakter pyrChar : pyramidenPlaettchen) {
				chars.get(pyrChar).addGuthaben(1);
			}
			pyramidenPlaettchen.clear();
		}
	}

	@Override
	public Farbe fuehrendesKamel() {
		if (finishedCheck()) {
			return firstPlace();
		}
		return calcFirst(camelMap);
	}

	/***
	 * Pr?ft ob mindestens ein Kamel im Ziel ist
	 * 
	 * @return
	 */
	private boolean finishedCheck() {
		for (Camel camel : camelMap.values()) {
			if (camel.getFinished()) {
				return true;
			}
		}
		return false;
	}

	/***
	 * Pr?ft Sieger wenn bereits Kamele im Ziel sind
	 * 
	 * @return
	 */
	private Farbe firstPlace() {
		Map<Farbe, Camel> finishedCamelMap = new HashMap<Farbe, Camel>();
		for (Camel camel : camelMap.values()) {
			if (camel.getFinished()) {
				finishedCamelMap.put(camel.getColor(), camel);
			}
		}
		return calcFirst(finishedCamelMap);
	}

	/***
	 * Pr?ft welches Kamel am weitesten ist wenn noch keines im Ziel ist
	 * 
	 * @param start
	 * @param map
	 * @return
	 */
	private Farbe calcFirst(Map<Farbe, Camel> map) {
		int highestPosition = 0;
		Farbe highestCamelColor = null;
		for (Camel camel : map.values()) {
			if (highestCamelColor == null) {
				highestPosition = camel.getPosition();
				highestCamelColor = camel.getColor();
			} else if (highestPosition < camel.getPosition()) {
				highestPosition = camel.getPosition();
				highestCamelColor = camel.getColor();
			} else if (highestPosition == camel.getPosition()) {
				int highestPositionStackPosition = spielfeld[highestPosition - 1]
						.getStackPosition(map.get(highestCamelColor));
				int currentCamelStackPosition = spielfeld[highestPosition - 1].getStackPosition(camel);
				if (highestPositionStackPosition < currentCamelStackPosition) {
					highestPosition = camel.getPosition();
					highestCamelColor = camel.getColor();
				}
			}
		}
		return highestCamelColor;
	}

	/***
	 * Pr?ft welches Kamel, dass nicht im Ziel ist ganz hinten ist
	 * 
	 * @return
	 */
	private Farbe lastPlace() {
		Map<Farbe, Camel> notFinishedCamelMap = new HashMap<Farbe, Camel>();
		for (Camel camel : camelMap.values()) {
			if (!camel.getFinished()) {
				notFinishedCamelMap.put(camel.getColor(), camel);
			}
		}
		return calcLast(notFinishedCamelMap);
	}

	/***
	 * Pr?ft welches Kamel am weitesten hinten ist wenn noch keines im Ziel ist
	 * 
	 * @param start
	 * @param map
	 * @return
	 */
	private Farbe calcLast(Map<Farbe, Camel> map) {
		int lowestPosition = 0;
		Farbe lowestCamelColor = null;
		for (Camel camel : map.values()) {
			if (lowestPosition == 0) {
				lowestPosition = camel.getPosition();
				lowestCamelColor = camel.getColor();
			} else if (lowestPosition > camel.getPosition()) {
				lowestPosition = camel.getPosition();
				lowestCamelColor = camel.getColor();
			} else if (lowestPosition == camel.getPosition()) {
				int lowestPositionStackPosition = spielfeld[lowestPosition - 1]
						.getStackPosition(map.get(lowestCamelColor));
				int currentCamelStackPosition = spielfeld[lowestPosition - 1].getStackPosition(camel);
				if (lowestPositionStackPosition > currentCamelStackPosition) {
					lowestPosition = camel.getPosition();
					lowestCamelColor = camel.getColor();
				}
			}
		}
		return lowestCamelColor;
	}

	@Override
	public Farbe letztesKamel() {
		if (gameFinished && !allCamelsFinished()) {
			return lastPlace();
		}
		return calcLast(camelMap);
	}

	/***
	 * Sonderfall wenn alle Kamele im Ziel sind
	 * 
	 * @return
	 */
	private Boolean allCamelsFinished() {
		int i = 0;
		for (Camel camel : camelMap.values()) {
			if (camel.getFinished()) {
				i++;
			}
		}
		return i == camelMap.size();
	}

	@Override
	public int feldNummer(Farbe farbe) {
		return camelMap.get(farbe).getPosition();
	}

	@Override
	public int stapelPosition(Farbe farbe) {
		return spielfeld[camelMap.get(farbe).getPosition() - 1].getStackPosition(camelMap.get(farbe));
	}

	@Override
	public void legeWuestenplaettchen(Charakter charakter, int feld, Plaettchen typ) throws UngueltigerZugException {
		if (chars.get(charakter).getFeldnummer() > 0) {
			spielfeld[chars.get(charakter).getFeldnummer() - 1].removePlaettchen();
		}

		if (feld == 0 || feld == 17) {
			// feld 0 und 17
			throw new UngueltigesFeldException();
		} else if (feld == 1) {
			// feld 1
			throw new UngueltigerZugException();
		} else if (spielfeld[feld - 2].plaettchenCheck() || spielfeld[feld - 1].plaettchenCheck()) {
			// plaettchencheck davor und selbes feld
			throw new UngueltigerZugException();
		} else if (feld != 16 && spielfeld[feld].plaettchenCheck()) {
			// plaettchencheck feld dahinter (nur wenn nicht feld 16)
			throw new UngueltigerZugException();
		} else if (spielfeld[feld - 1].camelCheck()) {
			// camelcheck
			throw new UngueltigerZugException();
		}
		else {
			spielfeld[feld - 1].addPlaettchen(new Effektplaettchen(typ, charakter));
			chars.get(charakter).setFeldnummer(feld);
		}
	}

	@Override
	public int feldNummer(Charakter charakter) {
		return chars.get(charakter).getFeldnummer();
	}

	@Override
	public Farbe zweitplatziertesKamel() {
		if (getAnzahlFinished() > 1) {
			Map<Farbe, Camel> finishedCamelMap = new HashMap<Farbe, Camel>();
			for (Camel camel : camelMap.values()) {
				if (camel.getFinished()) {
					finishedCamelMap.put(camel.getColor(), camel);
				}
			}
			Farbe firstFarbe = calcFirst(finishedCamelMap);
			Map<Farbe, Camel> camelMapWithoutFirst = finishedCamelMap;
			camelMapWithoutFirst.remove(firstFarbe);
			return calcFirst(camelMapWithoutFirst);
		} else if (getAnzahlFinished() == 1) {
			Farbe firstFarbe = null;
			Map<Farbe, Camel> camelMapWithoutFirst = new HashMap<Farbe, Camel>();
			for (Camel camel : camelMap.values()) {
				if (camel.getFinished()) {
					firstFarbe = camel.getColor();
				}
				camelMapWithoutFirst.put(camel.getColor(), camel);
			}
			camelMapWithoutFirst.remove(firstFarbe);
			return calcFirst(camelMapWithoutFirst);
		} else {
			Farbe firstFarbe = calcFirst(camelMap);
			Map<Farbe, Camel> camelMapWithoutFirst = new HashMap<Farbe, Camel>();
			for (Camel camel : camelMap.values()) {
				camelMapWithoutFirst.put(camel.getColor(), camel);
			}
			camelMapWithoutFirst.remove(firstFarbe);
			return calcFirst(camelMapWithoutFirst);
		}
	}

	@Override
	public void etappenWette(Charakter charakter, Farbe farbe) {
		wettgeschaeft.addWette(new Wette(charakter, Wettentyp.ETAPPE, farbe, wettplaettchen.getNext(farbe)));
	}

	@Override
	public void wetteTollesKamel(Charakter charakter, Farbe farbe) {
		wettgeschaeft.addWette(new Wette(charakter, Wettentyp.TOLL, farbe));
	}

	@Override
	public void wetteOllesKamel(Charakter charakter, Farbe farbe) {
		wettgeschaeft.addWette(new Wette(charakter, Wettentyp.OLL, farbe));
	}

	@Override
	public int guthaben(Charakter charakter) {
		if (chars.get(charakter) != null) {
			return chars.get(charakter).getGuthaben();
		}
		return 0;
	}

	/***
	 * Liefert Spielgewinner
	 * 
	 * @return gewinner
	 */
	public List<Charakter> spielGewinner() {
		List<Charakter> gewinnerListe = new ArrayList<>();
		int highest = 0;
		if (gameFinished) {
			for (Spieler spieler : chars.values()) {
				if (spieler.getGuthaben() > highest) {
					highest = spieler.getGuthaben();
				}
			}
			for (Spieler spieler : chars.values()) {
				if (spieler.getGuthaben() == highest) {
					gewinnerListe.add(spieler.getCharakter());
				}
			}
		}
		return gewinnerListe;
	}
}
