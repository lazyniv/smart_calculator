package main;

import calculator.Calculator;
import exceptions.CalculatorException;
import expression.Expression;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();
        while(true) {
            String line = scanner.nextLine();
            if(line.equals("/exit")) {
                break;
            }
            if(line.equals("/help")) {
                Calculator.printHelp();
            }
            if(line.equals("")) {
                System.out.print("");
            }
            if(line.contains("/")) {
                System.out.println("Unknown command");
            }
            Expression expression = new Expression(line);
            try {
                if (line.contains("=")) {
                    calculator.assign(expression);
                } else {
                    System.out.println(calculator.evaluate(expression));
                }
            } catch (CalculatorException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
