package org.example.kamus.model;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.Stack;

public class Kalkulator {

    @FXML
    private TextField display;

    private StringBuilder input = new StringBuilder();
    private Stack<Integer> numbers = new Stack<>();
    private Stack<Character> operators = new Stack<>();

    @FXML
    private void handleButtonClick(javafx.event.ActionEvent event) {
        Button button = (Button) event.getSource();
        String buttonText = button.getText();

        switch (buttonText) {
            case "C":
                clear();
                break;
            case "=":
                calculate();
                break;
            default:
                appendToInput(buttonText);
                break;
        }
    }

    private void appendToInput(String text) {
        input.append(text);
        display.setText(input.toString());
    }

    private void clear() {
        input.setLength(0);
        display.clear();
        numbers.clear();
        operators.clear();
    }

    private void calculate() {
        // Parse input and evaluate the expression
        String expression = input.toString();
        if (expression.isEmpty()) return;

        try {
            int result = evaluateExpression(expression);
            display.setText(String.valueOf(result));
            input.setLength(0); // Clear input after calculation
        } catch (Exception e) {
            display.setText("Error");
        }
    }


    private int evaluateExpression(String expression) {
        String[] tokens = expression.split("(?<=[-+x/])|(?=[-+x/])");
        for (String token : tokens) {
            if (token.isEmpty()) continue;
            if (Character.isDigit(token.charAt(0))) {
                numbers.push(Integer.parseInt(token));
            } else {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token.charAt(0))) {
                    int b = numbers.pop();
                    int a = numbers.pop();
                    char op = operators.pop();
                    numbers.push(applyOperator(a, b, op));
                }
                operators.push(token.charAt(0));
            }
        }

        while (!operators.isEmpty()) {
            int b = numbers.pop();
            int a = numbers.pop();
            char op = operators.pop();
            numbers.push(applyOperator(a, b, op));
        }

        return numbers.pop();
    }

    private int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case 'x':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    private int applyOperator(int a, int b, char operator) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case 'x':
                return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a / b;
            default:
                throw new UnsupportedOperationException("Operator not supported: " + operator);
        }
    }
}