package de.ostfalia.prog.s21ss.s3.model;

import java.util.Scanner;

import de.ostfalia.prog.s21ss.s3.base.Charakter;
import de.ostfalia.prog.s21ss.s3.base.Farbe;
import de.ostfalia.prog.s21ss.s3.model.exceptions.UngueltigerZugException;
import de.ostfalia.prog.s21ss.s3.model.felder.Plaettchen;

public class Main {

	public static void main(String[] args) throws NumberFormatException, UngueltigerZugException {
		Pyramide.getInstance().initialisiereRandom();
		Charakter[] chars = { Charakter.ABENTEURER, Charakter.DAME, Charakter.GENTLEMAN, Charakter.HAENDLER,
				Charakter.LADY };
		CamelUp camelUp = new CamelUp(chars);
		camelUp.startPosition();
		Scanner scanner = new Scanner(System.in);

		System.out.println("Spielstellung: " + camelUp);
		System.out.println("Guthaben: " + camelUp.getChars());

		while (!camelUp.getGameFinished()) {
			System.out
					.println(
							"Wetten abschließen & Plaettchen legen-> ABENTEURER:ETAPPE:BLAU,DAME:OLL:GRUEN,GENTLEMEN:TOLL:GELB,GENTLEMEN:OASE:12");
			String input = scanner.nextLine();

			// wetten und plaettchen setzen
			if (input.length() > 0) {
				String[] wetten = input.split(",");
				for (String wette : wetten) {
					String[] wetteSplit = wette.split(":");
					if (wetteSplit[1].equals("ETAPPE")) {
						camelUp.etappenWette(Charakter.valueOf(wetteSplit[0]), Farbe.valueOf(wetteSplit[2]));
						System.out.println(wetteSplit[0] + " Etappenwette auf " + wetteSplit[2]);
					} else if (wetteSplit[1].equals("OLL")) {
						camelUp.wetteOllesKamel(Charakter.valueOf(wetteSplit[0]), Farbe.valueOf(wetteSplit[2]));
					} else if (wetteSplit[1].equals("TOLL")) {
						camelUp.wetteTollesKamel(Charakter.valueOf(wetteSplit[0]), Farbe.valueOf(wetteSplit[2]));
					} else if (wetteSplit[1].equals("OASE") || wetteSplit[1].equals("FATAMORGANA")) {
						camelUp.legeWuestenplaettchen(Charakter.valueOf(wetteSplit[0]), Integer.parseInt(wetteSplit[2]),
								Plaettchen.valueOf(wetteSplit[1]));
					} else {
						System.out.println("invalidInput: " + wette);
					}
				}
			}

			Pyramide.getInstance().initialisiereRandom();
			int wuerfelLeftAnzahl = Pyramide.anzahlWuerfel();
			for (int i = 0; i < wuerfelLeftAnzahl; i++) {
				camelUp.bewegeKamel(chars[i]);
			}

			System.out.println("Spielstellung: " + camelUp);
			System.out.println("Guthaben: " + camelUp.getChars());
		}
		scanner.close();
		System.out.println("Kamelsieger: " + camelUp.fuehrendesKamel());
		System.out.println("Charaktere Sieger: " + camelUp.spielGewinner());
	}

}
