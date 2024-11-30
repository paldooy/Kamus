package org.example.kamus.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import org.example.kamus.model.Tree;
import org.example.kamus.model.Kalkulator;
import org.example.kamus.model.Gimmick;

import java.io.IOException;

public class FirstPage  {

    @FXML
    private ComboBox<String> pilihan;

    @FXML
    private TextArea inputText;

    @FXML
    private TextArea outputText;

    @FXML
    private ScrollPane contentBox;

    private Gimmick[] gimmicks = new Gimmick[1];

    private Tree indoToEng = new Tree();
    private Tree engToindo = new Tree();

    @FXML
    public void initialize() {

        pilihan.getItems().addAll("ID - ENG", "ENG - ID");
        pilihan.setValue("ID - ENG");

        gimmicks[0] = new Kalkulator();
        //indo - eng
        indoToEng.add("selamat", "congratulations");
        indoToEng.add("kalkulator", "calculator");
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
        String translation = indoToEng.search(textToTranslate.toLowerCase());

        Gimmick selectedGimmick = null;

        switch (textToTranslate.toLowerCase()) {
            case "kalkulator":
                selectedGimmick = gimmicks[0];
                outputText.setText(translation); // Tampilkan terjemahan
                break;
        }

        if (selectedGimmick != null) {
            selectedGimmick.display((VBox) contentBox.getContent());
            return;
        }

        if (pilihan.getValue().equals("ID - ENG")) {
            translation = indoToEng.search(textToTranslate.toLowerCase());
        } else if (pilihan.getValue().equals("ENG - ID")) {
            translation = engToindo.search(textToTranslate.toLowerCase());
        }        if (textToTranslate.equalsIgnoreCase("kalkulator")) {
            displayCalculator();
            return;
        }

        if (translation == null) {
            outputText.setText("Kata tidak ditemukan");
        } else {
            outputText.setText(translation);
        }
    }

    private void displayCalculator() {
        // Clear previous content in scroll pane
        contentBox.setContent(new VBox()); // Clear previous content

        // Load the calculator UI from FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/kamus/calculator.fxml")); // Update path accordingly
        try {
            VBox calculatorUI = loader.load(); // Load the FXML file
            contentBox.setContent(calculatorUI); // Set new content to scroll pane
        } catch (IOException e) {
            e.printStackTrace(); // Handle loading error
            outputText.setText("Error loading calculator.");
        }
    }
}
