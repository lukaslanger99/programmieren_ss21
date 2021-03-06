package de.ostfalia.prog.s21ss.s2.model;

import java.util.HashMap;
import java.util.Map;

import de.ostfalia.prog.s21ss.s2.base.Charakter;
import de.ostfalia.prog.s21ss.s2.base.Farbe;
import de.ostfalia.prog.s21ss.s2.base.ICamelUp;
import de.ostfalia.prog.s21ss.s2.model.exceptions.UngueltigerZugException;
import de.ostfalia.prog.s21ss.s2.model.exceptions.UngueltigesFeldException;
import de.ostfalia.prog.s21ss.s2.model.felder.Plaettchen;

public class CamelUp implements ICamelUp {
	private Map<Farbe, Camel> camelMap = new HashMap<Farbe, Camel>();
	private Boolean gameFinished = false;
	private Feld[] spielfeld;
	private Map<Charakter, Integer> chars = new HashMap<Charakter, Integer>();

	public CamelUp(Charakter[] characters) {
		initCamels();
		initSpielfeld();
		initChars();
	}

	private void initCamels() {
		for (Farbe farbe : Farbe.values()) {
			camelMap.put(farbe, new Camel(farbe));
		}
	}

	private void initSpielfeld() {
		spielfeld = new Feld[16];
		for (int i = 0; i < spielfeld.length; i++) {
			spielfeld[i] = new Feld();
		}
	}

	private void initChars() {
		for (Charakter charakter : Charakter.values()) {
			chars.put(charakter, 0);
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
		Feld currentField = spielfeld[camelMap.get(wuerfel.getColor()).getPosition() - 1];
		if (!gameFinished) {
			currentField.popStack(wuerfel.getColor(), wuerfel.getValue(),
				spielfeld);
			gameFinished = currentField.checkFinish();
		}
	}

	@Override
	public Farbe fuehrendesKamel() {
		if (gameFinished || finishedCheck()) {
			return firstPlace();
		}
		return calcFirst(Farbe.BLAU, camelMap);
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
		Farbe randomColor = null;
		for (Camel camel : camelMap.values()) {
			if (camel.getFinished()) {
				finishedCamelMap.put(camel.getColor(), camel);
				randomColor = camel.getColor();
			}
		}
		return calcFirst(randomColor, finishedCamelMap);
	}

	/***
	 * Pr?ft welches Kamel am weitesten ist wenn noch keines im Ziel ist
	 * 
	 * @param start
	 * @param map
	 * @return
	 */
	private Farbe calcFirst(Farbe start, Map<Farbe, Camel> map) {
		int highestPosition = map.get(start).getPosition();
		Farbe highestCamelColor = start;
		for (Camel camel : map.values()) {
			if (highestPosition < camel.getPosition()) {
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
		Farbe randomColor = null;
		for (Camel camel : camelMap.values()) {
			if (!camel.getFinished()) {
				notFinishedCamelMap.put(camel.getColor(), camel);
				randomColor = camel.getColor();
			}
		}
		return calcLast(randomColor, notFinishedCamelMap);
	}

	/***
	 * Pr?ft welches Kamel am weitesten hinten ist wenn noch keines im Ziel ist
	 * 
	 * @param start
	 * @param map
	 * @return
	 */
	private Farbe calcLast(Farbe start, Map<Farbe, Camel> map) {
		int lowestPosition = map.get(start).getPosition();
		Farbe lowestCamelColor = start;
		for (Camel camel : map.values()) {
			if (lowestPosition > camel.getPosition()) {
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
		return calcLast(Farbe.BLAU, camelMap);
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
		if (chars.get(charakter) > 0) {
			spielfeld[chars.get(charakter) - 1].removePlaettchen();
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
			spielfeld[feld - 1].addPlaettchen(typ);
			chars.put(charakter, feld);
		}
	}

	@Override
	public int feldNummer(Charakter charakter) {
		return chars.get(charakter);
	}

}
