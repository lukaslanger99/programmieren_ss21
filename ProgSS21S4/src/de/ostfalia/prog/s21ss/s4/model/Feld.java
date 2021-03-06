package de.ostfalia.prog.s21ss.s4.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import de.ostfalia.prog.s21ss.s4.base.Charakter;
import de.ostfalia.prog.s21ss.s4.base.Farbe;
import de.ostfalia.prog.s21ss.s4.model.felder.Effektplaettchen;
import de.ostfalia.prog.s21ss.s4.model.felder.Plaettchen;

public class Feld {
	private Deque<Camel> camelStack = new ArrayDeque<Camel>();
	private boolean finished = false;
	private de.ostfalia.prog.s21ss.s4.model.felder.Effektplaettchen plaettchen;

	/***
	 * Konstruktor
	 */
	public Feld() {

	}

	/***
	 * Camel zum stack hinzuf?gen
	 * 
	 * @param camel
	 */
	public void addCamel(Camel camel) {
		camelStack.push(camel);
	}

	/***
	 * Stack popen bis einschlei?lich gew?rfelter Farbe, im Falle eines Plaettchens
	 * wird der Effekt anschlie?end noch angewandt
	 * 
	 * @param farbe
	 * @param wuerfelZahl
	 * @param spielfeld
	 * @param chars
	 */
	public void popStack(Farbe farbe, int wuerfelZahl, Feld[] spielfeld, Map<Charakter, Spieler> chars) {
		Camel camel;
		Deque<Camel> movingStack = new ArrayDeque<Camel>();
		do {
			camel = camelStack.pop();
			movingStack.add(camel);
		} while (camel.getColor() != farbe);
		int movingStackSize = movingStack.size();
		finished = false;
		for (int i = 0; i < movingStackSize; i++) {
			camel = movingStack.pollLast();
			int newPosition = camel.getPosition() + wuerfelZahl;
			camel.setPosition(newPosition);
			spielfeld[camel.getPosition() - 1].addCamel(camel);
			if (newPosition > 16) {
				finished = true;
			}
		}
		if (spielfeld[camel.getPosition() - 1].isOase()) {
			chars.get(spielfeld[camel.getPosition() - 1].plaettchen.getOwner()).addGuthaben(1);
			spielfeld[camel.getPosition() - 1].oaseEffekt(spielfeld);
		} else if (spielfeld[camel.getPosition() - 1].isFatamorgana()) {
			chars.get(spielfeld[camel.getPosition() - 1].plaettchen.getOwner()).addGuthaben(1);
			spielfeld[camel.getPosition() - 1].fatamorganaEffekt(spielfeld);
		}
	}

	@Override
	public String toString() {
		String string = "";
		Camel[] camelArray = new Camel[camelStack.size()];
		int i = 0;
		for (Camel camel : camelStack) {
			camelArray[i++] = camel;
		}
		for (int j = camelArray.length - 1; j >= 0; j--) {
			if (j > 0) {
				string += camelArray[j] + ", ";
			} else {
				string += camelArray[j];
			}
		}
		if (plaettchenCheck()) {
			string += plaettchen;
		}
		return "[" + string + "] \n";
	}

	/***
	 * Liefert position wo sich das camel im Stack befindet
	 * 
	 * @param camel
	 * @return stackposition
	 */
	public int getStackPosition(Camel camel) {
		Deque<Camel> camelStackCopy = new ArrayDeque<Camel>();
		camelStackCopy.addAll(camelStack);
		Camel[] camels = new Camel[camelStackCopy.size()];
		for (int i = camels.length - 1; i >= 0; i--) {
			camels[i] = camelStackCopy.pop();
		}
		for (int i = 0; i < camels.length; i++) {
			if (camels[i].getColor().equals(camel.getColor())) {
				return (i + 1);
			}
		}
		return 0;
	}

	/***
	 * Pr?fen ob das Spiel abgeschlossen ist
	 * 
	 * @return finsihed
	 */
	public Boolean checkFinish() {
		return finished;
	}

	/***
	 * Plaettchen hinzuf?gen
	 * 
	 * @param effektplaettchen
	 */
	public void addPlaettchen(Effektplaettchen effektplaettchen) {
		plaettchen = effektplaettchen;
	}

	/***
	 * Gibt zur?ck ob auf dem Feld ein Plaettchen ist
	 * 
	 * @return boolean
	 */
	public boolean plaettchenCheck() {
		return plaettchen != null;
	}

	/***
	 * Pr?ft ob auf dem Feld Kamele sind
	 * 
	 * @return boolean
	 */
	public boolean camelCheck() {
		return camelStack.size() > 0;
	}

	/***
	 * Plaettchen entfernen
	 */
	public void removePlaettchen() {
		plaettchen = null;
	}

	/***
	 * Oasen effekt ausl?sen, Stack wird um 1 nach vorne geschoben und oben drauf
	 * gepackt
	 * 
	 * @param spielfeld
	 */
	private void oaseEffekt(Feld[] spielfeld) {
		Camel camel = null;
		int stackSize = camelStack.size();
		Deque<Camel> movingStack = new ArrayDeque<Camel>();
		for (int i = 0; i < stackSize; i++) {
			camel = camelStack.pop();
			movingStack.add(camel);
			int newPosition = camel.getPosition() + 1;
			camel.setPosition(newPosition);
			if (camel.getFinished()) {
				finished = true;
			}
		}
		spielfeld[camel.getPosition() - 1].addStackOase(movingStack);
	}

	/***
	 * Fatamorgana Effekt, Stack wird um 1 nach hinten gesetzt und nach unten
	 * 
	 * @param spielfeld
	 */
	private void fatamorganaEffekt(Feld[] spielfeld) {
		Camel camel = null;
		int stackSize = camelStack.size();
		Deque<Camel> movingStack = new ArrayDeque<Camel>();
		for (int i = 0; i < stackSize; i++) {
			camel = camelStack.pop();
			movingStack.add(camel);
			int newPosition = camel.getPosition() - 1;
			camel.setPosition(newPosition);
		}
		spielfeld[camel.getPosition() - 1].addStackFatamorgana(movingStack);
	}

	/***
	 * Pr?fen ob Feld Oase ist
	 * 
	 * @return boolean
	 */
	private boolean isOase() {
		return plaettchen != null && plaettchen.getTyp().equals(Plaettchen.OASE);
	}

	/***
	 * Pr?fen ob Feld Fatamorgana ist
	 * 
	 * @return boolean
	 */
	private boolean isFatamorgana() {
		return plaettchen != null && plaettchen.getTyp().equals(Plaettchen.FATAMORGANA);
	}

	/***
	 * Stack wird dem aktuellen Stack hinzugef?gt ( oben )
	 * 
	 * @param movingStack
	 */
	private void addStackOase(Deque<Camel> movingStack) {
		int stackSize = camelStack.size();
		Deque<Camel> tmpCamelStack = new ArrayDeque<Camel>();
		for (int i = 0; i < stackSize; i++) {
			movingStack.add(camelStack.pop());
		}
		camelStack.addAll(tmpCamelStack);
		camelStack.addAll(movingStack);
	}

	/***
	 * Stack wird dem aktuellen Stack hinzugef?gt ( unten )
	 * 
	 * @param movingStack
	 */
	private void addStackFatamorgana(Deque<Camel> movingStack) {
		int size = movingStack.size();
		for (int i = 0; i < size; i++) {
			camelStack.addLast(movingStack.pop());
		}
	}
}
