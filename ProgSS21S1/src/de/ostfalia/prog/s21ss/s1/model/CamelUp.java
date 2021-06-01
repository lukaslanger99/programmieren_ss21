package de.ostfalia.prog.s21ss.s1.model;

import java.util.HashMap;
import java.util.Map;

import de.ostfalia.prog.s21ss.s1.base.Charakter;
import de.ostfalia.prog.s21ss.s1.base.Farbe;
import de.ostfalia.prog.s21ss.s1.base.ICamelUp;

public class CamelUp implements ICamelUp {
	private Map<Farbe, Camel> camelMap = new HashMap<Farbe, Camel>();
	private Boolean gameFinished = false;
	private Feld[] spielfeld;

	public CamelUp(Charakter[] characters) {
		initCamels();
		initSpielfeld();
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
		if (gameFinished) {
			return firstPlace();
		}
		return calcFirst(Farbe.BLAU, camelMap);
	}

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

	public void updateField(Camel camel, int position) {
		spielfeld[position - 1].addCamel(camel);
	}

}
