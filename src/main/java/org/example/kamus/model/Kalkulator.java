package org.example.kamus.model;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.net.URL;

public class Kalkulator extends Application {

    @FXML
    private TextField display;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load FXML file from the correct package path
        URL fxmlUrl = getClass().getResource("/org/example/kamus/view/calculator.fxml");
        if (fxmlUrl == null) {
            throw new IllegalArgumentException("FXML file not found!");
        }

        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        VBox root = loader.load();

        // No need to manually get display here since it's injected by FXML
        // display = (TextField) loader.getNamespace().get("display");

        primaryStage.setTitle("Kalkulator Sederhana");
        primaryStage.setScene(new Scene(root, 300, 400));
        primaryStage.show();
    }

    public void handleButtonClick(javafx.event.ActionEvent event) {
        String value = ((Button) event.getSource()).getText();

        if (value.equals("C")) {
            display.clear();
        } else if (value.equals("=")) {
            try {
                String result = evaluateExpression(display.getText());
                display.setText(result);
            } catch (Exception e) {
                display.setText("Error");
            }
        } else {
            display.appendText(value);
        }
    }

    private String evaluateExpression(String expression) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        try {
            expression = expression.trim(); // Menghapus spasi di awal dan akhir
            Object result = engine.eval(expression); // Mengevaluasi ekspresi
            return String.valueOf(result); // Mengembalikan hasil sebagai string
        } catch (ScriptException e) {
            return "Error"; // Menangani kesalahan evaluasi
        } catch (Exception e) {
            return "Error"; // Menangani kesalahan lain yang mungkin terjadi
        }
    }
}