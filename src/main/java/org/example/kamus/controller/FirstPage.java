package org.example.kamus.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import org.example.kamus.model.Jam;
import org.example.kamus.model.Tree;
import org.example.kamus.model.Kalkulator;
import org.example.kamus.model.Gimmick;


public class FirstPage  {

    @FXML
    private ComboBox<String> pilihan;

    @FXML
    private TextArea inputText;

    @FXML
    private TextArea outputText;

    @FXML
    private ScrollPane contentBox;

    private Gimmick[] gimmicks = new Gimmick[2];

    private Tree indoToEng = new Tree();
    private Tree engToindo = new Tree();



    @FXML
    public void initialize() {

        pilihan.getItems().addAll("ID - ENG", "ENG - ID");
        pilihan.setValue("ID - ENG");

        gimmicks[0] = new Kalkulator();
        gimmicks[1] = new Jam();
        //indo - eng
        indoToEng.add("tutup", "close");
        indoToEng.add("kalkulator", "calculator");
        indoToEng.add("jam", "clock");
        indoToEng.add("apa aja deh", "");
        indoToEng.add("komputer", "computer");
        indoToEng.add("tolong", "please");
        indoToEng.add("apa", "what");
        indoToEng.add("mengapa", "why");
        indoToEng.add("siapa", "who");
        indoToEng.add("bagus", "good");
        //eng - indo
        engToindo.add("close", "tutup");
        engToindo.add("calculator", "kalkulator");
        engToindo.add("clock", "jam");
        engToindo.add("anything", "");
        engToindo.add("computer", "komputer");
        engToindo.add("please", "tolong");
        engToindo.add("what", "apa");
        engToindo.add("why", "mengapa");
        engToindo.add("who", "siapa");
        engToindo.add("good", "bagus");

        inputText.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleTranslate();
                inputText.setText(inputText.getText().trim());
                event.consume();
            }
        });


    }

    @FXML
    public void handleTranslate() {
        String textToTranslate = inputText.getText().trim();
        String translation = indoToEng.search(textToTranslate.toLowerCase());

        Gimmick selectedGimmick = null;
        VBox container = (VBox) contentBox.getContent();

        if (pilihan.getValue().equals("ID - ENG")) {
            if (!indoToEng.contains(textToTranslate)) {
                outputText.setText("Kata tidak ditemukan");
                return;
            }
            translation = indoToEng.search(textToTranslate);
        } else if (pilihan.getValue().equals("ENG - ID")) {
            if (!engToindo.contains(textToTranslate)) {
                outputText.setText("Words not found");
                return;
            }
            translation = engToindo.search(textToTranslate);
        }

        switch (textToTranslate.toLowerCase()) {
            case "kalkulator": case "calculator":
                selectedGimmick = gimmicks[0];
                outputText.setText(translation);
                break;
            case "jam": case "clock":
                selectedGimmick = gimmicks[1];
                selectedGimmick.display((VBox) contentBox.getContent());
                return;
            case "close": case "tutup":
                System.exit(0);
                return;
            default:
                container.getChildren().clear();
        }

        if (selectedGimmick != null) {
            selectedGimmick.display((VBox) contentBox.getContent());
            return;
        }

        if (pilihan.getValue().equals("ID - ENG")) {
            translation = indoToEng.search(textToTranslate.toLowerCase());
        } else if (pilihan.getValue().equals("ENG - ID")) {
            translation = engToindo.search(textToTranslate.toLowerCase());
        }

        if (translation == null) {
            outputText.setText("Kata tidak ditemukan");
        } else {
            outputText.setText(translation);
        }
    }

}
