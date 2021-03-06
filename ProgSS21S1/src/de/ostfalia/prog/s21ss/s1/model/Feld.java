package de.ostfalia.prog.s21ss.s1.model;

import java.util.ArrayDeque;
import java.util.Deque;

import de.ostfalia.prog.s21ss.s1.base.Farbe;

public class Feld {
	Deque<Camel> camelStack = new ArrayDeque<Camel>();
	private boolean finished = false;

	public Feld() {

	}

	public void addCamel(Camel camel) {
		camelStack.push(camel);
	}

	public void popStack(Farbe farbe, int wuerfelZahl, Feld[] spielfeld) {
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
		return "[" + string + "]";
	}

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

	public Boolean checkFinish() {
		return finished;
	}
}
