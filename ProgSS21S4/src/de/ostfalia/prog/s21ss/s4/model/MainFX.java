package de.ostfalia.prog.s21ss.s4.model;

import de.ostfalia.prog.s21ss.s4.base.Charakter;
import de.ostfalia.prog.s21ss.s4.base.Farbe;
import de.ostfalia.prog.s21ss.s4.model.exceptions.UngueltigerZugException;
import de.ostfalia.prog.s21ss.s4.model.felder.Plaettchen;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainFX extends Application {
	Charakter[] chars = { Charakter.ABENTEURER, Charakter.DAME, Charakter.GENTLEMAN, Charakter.HAENDLER,
			Charakter.LADY };
	private CamelUp camelUp;

	private FlowPane root;

	private Label spielfeld;
	private Label plazierteWetten;
	private Label guthaben;

	private ListView<String> charList;
	private ListView<String> farbenList;

	private TextField value;
	private Button legeOase;
	private Button legeFatamorgana;
	private Button etappenWette;
	private Button olleWette;
	private Button tolleWette;
	private Button etappeSpielenButton;
	private VBox mainArea;
	private HBox spielfeldUndListen;
	private HBox wetten;
	private HBox plaettchen;

	public static void main(String[] args) {
		launch(args);
	}

	public MainFX() {
		Pyramide.getInstance().initialisiereRandom();
		camelUp = new CamelUp(chars);
		camelUp.startPosition();
		System.out.println("created");
	}

	@Override
	public void start(Stage stage) {
		initLayout();
		initListViews();
		initButtons();

		spielfeldUndListen.getChildren().addAll(spielfeld, charList, farbenList, guthaben, plazierteWetten);
		plaettchen.getChildren().addAll(value, legeOase, legeFatamorgana);
		wetten.getChildren().addAll(etappenWette, olleWette, tolleWette);
		mainArea.getChildren().addAll(spielfeldUndListen, plaettchen, wetten, etappeSpielenButton);
		root.getChildren().addAll(mainArea);

		stage.setTitle("CamelUp");
		stage.setScene(new Scene(root, 1000, 500));
		stage.show();
	}

	/***
	 * layout
	 */
	private void initLayout() {
		root = new FlowPane();
		mainArea = new VBox();
		mainArea.setSpacing(5);
		spielfeldUndListen = new HBox();
		plaettchen = new HBox();
		wetten = new HBox();
		plaettchen.setSpacing(5);
		wetten.setSpacing(5);
	}

	/***
	 * listviews
	 */
	private void initListViews() {
		ObservableList<String> names = FXCollections.observableArrayList(
		          "ABENTEURER", "DAME", "GENTLEMAN", "HAENDLER", "LADY");
		charList = new ListView<String>();
		charList.setItems(names);

		names = FXCollections.observableArrayList("BLAU", "GELB", "GRUEN", "WEISS", "ORANGE");
		farbenList = new ListView<String>();
		farbenList.setItems(names);

		spielfeld = new Label(camelUp.toString());
		plazierteWetten = new Label(camelUp.printAktiveWetten());
		guthaben = new Label(camelUp.printGuthaben());
		
		value = new TextField();
	}

	/***
	 * buttons
	 */
	private void initButtons() {
		legeOase = new Button("Lege Oase");
		legeOase.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				int fieldNumber = Integer.parseInt(value.getText());
				Charakter charakter = Charakter.valueOf(charList.getSelectionModel().getSelectedItem());

				if (charakter != null && fieldNumber > 0) {
					try {
						camelUp.legeWuestenplaettchen(charakter, fieldNumber, Plaettchen.OASE);
						spielfeld.setText(camelUp.toString());
						value.setText("");
					} catch (UngueltigerZugException e) {
						e.printStackTrace();
					}
				}
			}

		});

		legeFatamorgana = new Button("Lege Fatamorgana");
		legeFatamorgana.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				int fieldNumber = Integer.parseInt(value.getText());
				Charakter charakter = Charakter.valueOf(charList.getSelectionModel().getSelectedItem());

				if (charakter != null && fieldNumber > 0) {
					try {
						camelUp.legeWuestenplaettchen(charakter, fieldNumber, Plaettchen.FATAMORGANA);
						spielfeld.setText(camelUp.toString());
						value.setText("");
					} catch (UngueltigerZugException e) {
						e.printStackTrace();
					}
				}
			}

		});

		etappenWette = new Button("Etappenwette");
		etappenWette.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Charakter charakter = Charakter.valueOf(charList.getSelectionModel().getSelectedItem());
				Farbe farbe = Farbe.valueOf(farbenList.getSelectionModel().getSelectedItem());

				if (charakter != null && farbe != null) {
					camelUp.etappenWette(charakter, farbe);
					plazierteWetten.setText(camelUp.printAktiveWetten());
					System.out.println("Etappenwette: " + charakter + "-" + farbe);
				}
			}

		});

		olleWette = new Button("Olle-wette");
		olleWette.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Charakter charakter = Charakter.valueOf(charList.getSelectionModel().getSelectedItem());
				Farbe farbe = Farbe.valueOf(farbenList.getSelectionModel().getSelectedItem());

				if (charakter != null && farbe != null) {
					camelUp.etappenWette(charakter, farbe);
					plazierteWetten.setText(camelUp.printAktiveWetten());
					System.out.println("Etappenwette: " + charakter + "-" + farbe);
				}
			}

		});

		tolleWette = new Button("Tolle-wette");
		tolleWette.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Charakter charakter = Charakter.valueOf(charList.getSelectionModel().getSelectedItem());
				Farbe farbe = Farbe.valueOf(farbenList.getSelectionModel().getSelectedItem());

				if (charakter != null && farbe != null) {
					camelUp.etappenWette(charakter, farbe);
					plazierteWetten.setText(camelUp.printAktiveWetten());
					System.out.println("Etappenwette: " + charakter + "-" + farbe);
				}
			}

		});

		etappeSpielenButton = new Button("Etappe spielen");
		etappeSpielenButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Pyramide.getInstance().initialisiereRandom();
				int wuerfelLeftAnzahl = Pyramide.anzahlWuerfel();
				for (int i = 0; i < wuerfelLeftAnzahl; i++) {
					camelUp.bewegeKamel(chars[i]);
				}
				spielfeld.setText(camelUp.toString());
				plazierteWetten.setText(camelUp.printAktiveWetten());
				guthaben.setText(camelUp.printGuthaben());

				if (camelUp.getGameFinished()) {
					charList.setVisible(false);
					farbenList.setVisible(false);
					plaettchen.setVisible(false);
					wetten.setVisible(false);
					etappeSpielenButton.setVisible(false);
				}
			}

		});
	}
}