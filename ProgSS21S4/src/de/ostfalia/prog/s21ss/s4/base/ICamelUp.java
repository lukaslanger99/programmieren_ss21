package de.ostfalia.prog.s21ss.s4.base;

import java.util.List;

import de.ostfalia.prog.s21ss.s4.model.exceptions.UngueltigerZugException;
import de.ostfalia.prog.s21ss.s4.model.felder.Plaettchen;

public interface ICamelUp {

	/**
	 * Setzt die Kamele entsprechend der gewuerfelten Werte
	 * auf die Startposition innerhalb des Spielfeldes.
	 */
	public void startPosition();
	
	/**
	 * Setzt die Kamele auf die uebergebenen Positionen 
	 * innerhalb des Spielfeldes.
	 * z.B.: "GRUEN:1","GELB:1","ORANGE:2","BLAU:2","WEISS:3"
	 * @param felder - Startposiotion der Kamele.
	 */
	public void startPosition(String... felder);

	/**
	 * Bewegt das Kamel mit der gewuerfelten Farbe entsprechend 
	 * dem gewuerfelten Werte im Uhrzeigersinn weiter.
	 * @param charakter - Charakter des Spielers.
	 */
	public void bewegeKamel(Charakter charakter);
	
	/**
	 * Legt das Wuestenplaettchen des Charakters mit dem angegebenen
	 * Typ auf das Feld.
	 * @param charakter - Charakter der Plaettchens.
	 * @param typ - Typ des Plaettchens (OASE oder FATAMORGANA).
	 * @param feld - Feldnummer (2..16).
	 * @throws UngueltigerZugException, wenn das Legen des 
	 * Plaettchens auf das Feld verboten ist.
	 * @throws UngueltigerFeldException, wenn das Plaettchen auf
	 * ein Feld außerhalb des Spielfelds gelegt werden soll.
	 */
	public void legeWuestenplaettchen(Charakter charakter, int feld, Plaettchen typ) 
			throws UngueltigerZugException;

	/**
	 * Liefert die Farbe des fuehrenden Kamels auf dem Spielfeld.
	 * @return Farbe des fuehrenden Kamels.
	 */
	public Farbe fuehrendesKamel();
	
	/**
	 * Liefert die Farbe des zweitplatzierten Kamels auf dem Spielfeld.
	 * @return Farbe des zweitplatzierten Kamels.
	 */
	public Farbe zweitplatziertesKamel();
	
	/**
	 * Liefert die Farbe des letzten Kamels auf dem Spielfeld.
	 * @return Farbe des letzten Kamels.
	 */
	public Farbe letztesKamel();

	/**
	 * Der Charakter wettet auf den Etappensieg des Kamels mit
	 * der angegebenen Farbe.
	 * @param charakter - Charakter, der die Wette abgibt.
	 * @param farbe - Farbe des Kamels.
	 */
	public void etappenWette(Charakter charakter, Farbe farbe);
	
	/**
	 * Der Charakter wettet auf das tolle Kamel (den Gesamtsieger
	 * des Spiels) mit der angegebenen Farbe.
	 * @param charakter - Charakter, der die Wette abgibt.
	 * @param farbe - Farbe des Kamels.
	 */
	public void wetteTollesKamel(Charakter charakter, Farbe farbe);
	
	/**
	 * Der Charakter wettet auf das olle Kamel (den Gesamtletzten) 
	 * mit der angegebenen Farbe.
	 * @param charakter - Charakter, der die Wette abgibt.
	 * @param farbe - Farbe des Kamels.
	 */
	public void wetteOllesKamel(Charakter charakter, Farbe farbe);

	/**
	 * Liefert die Spielfeldnummer des Kamels mit der angegebenen
	 * Farbe. 
	 * @param farbe - Farbe des Kamels.
	 * @return Spielfeldnummer (1..16) des Kamels mit der
	 * angegebenen Farbe, oder 0, wenn sich das Kamel nicht 
	 * auf dem Spielfeld befindet.
	 */
	public int feldNummer(Farbe farbe);

	/**
	 * Liefert die Spielfeldnummer des Wuestenplaettchens mit dem 
	 * angegebenen Charakter.	
	 * @param charakter - Charakter des Wuestenplaettchens.
	 * @return Spielfeldnummer (2..16) des Wuestenplaettchens mit dem 
	 * angegebenen Charakter, oder 0, wenn sich das Plaettchen nicht 
	 * auf dem Spielfeld befindet.
	 */
	int feldNummer(Charakter charakter);

	/**
	 * Liefert die Stapelposition des Kamels mit der angegebenen
	 * Farbe. 
	 * @param farbe - Farbe des Kamels.
	 * @return Position (1..5) des Kamels mit der angegebenen 
	 * Farbe im Stapel, oder 0, wenn sich das Kamel 
	 * nicht auf dem Spielfeld befindet.
	 */
	public int stapelPosition(Farbe farbe);
	
	/**
	 * Liefert das Guthaben des angegebenen Charakters.	
	 * @param charakter - Charakter des Spielers. 
	 * @return Guthaben des Charakters in Pfund, oder 0, wenn
	 * sich der Charakter nicht am Spiel beteiligt.
	 */
	public int guthaben(Charakter charakter);
	
	/**
	 * Liefert den bzw. die Spielgewinner. Steht noch kein
	 * Gewinner fest, liefert die Methode eine leere Liste.
	 * @return Liste der Charaktere der Spielgewinner. 
	 */
	public List<Charakter> spielGewinner();

}