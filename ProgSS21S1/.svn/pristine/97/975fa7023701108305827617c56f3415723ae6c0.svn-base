package de.ostfalia.prog.s21ss.s1.base;


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
	 * Liefert die Farbe des fuehrenden Kamels auf dem Spielfeld.
	 * @return Farbe des fuehrenden Kamels.
	 */
	public Farbe fuehrendesKamel();

	/**
	 * Liefert die Farbe des letzten Kamels auf dem Spielfeld.
	 * @return Farbe des letzten Kamels.
	 */
	public Farbe letztesKamel();

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
	 * Liefert die Stapelposition des Kamels mit der angegebenen
	 * Farbe. 
	 * @param farbe - Farbe des Kamels.
	 * @return Position (1..5) des Kamels mit der angegebenen 
	 * Farbe im Stapel, oder 0, wenn sich das Kamel 
	 * nicht auf dem Spielfeld befindet.
	 */
	public int stapelPosition(Farbe farbe);

}