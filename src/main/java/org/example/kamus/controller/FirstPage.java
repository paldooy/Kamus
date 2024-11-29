package org.example.kamus.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

public class FirstPage  {

    @FXML
    private ComboBox<String> pilihan;

    @FXML
    private TextArea inputText;

    @FXML
    private TextArea outputText;

    private Tree indoToEng = new Tree();
    private Tree engToindo = new Tree();

    @FXML
    public void initialize() {

        pilihan.getItems().add("ID - ENG");
        pilihan.getItems().add("ENG - ID");

        pilihan.setValue("ID - ENG");
        
        //indo - eng
        indoToEng.add("selamat", "congratulations");
        indoToEng.add("terima kasih", "thank you");
        indoToEng.add("ya", "yes");
        indoToEng.add("tidak", "no");
        indoToEng.add("tolong", "please");
        indoToEng.add("apa", "what");
        indoToEng.add("di mana", "where");
        indoToEng.add("mengapa", "why");
        indoToEng.add("siapa", "who");
        indoToEng.add("bagus", "good");
        //eng - indo
        engToindo.add("congratulations", "selamat");
        engToindo.add("thank you", "terima kasih");
        engToindo.add("yes", "ya");
        engToindo.add("no", "tidak");
        engToindo.add("please", "tolong");
        engToindo.add("what", "apa");
        engToindo.add("where", "di mana");
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
        String translation = null;
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
