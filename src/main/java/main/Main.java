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
            System.out.print("\n> ");
            String line = scanner.nextLine();
            if(line.equals("/exit")) {
                System.out.println("Bye!");
                break;
            }
            if(line.equals("/help")) {
                Calculator.printHelp();
                continue;
            }
            if(line.equals("/vars")) {
                calculator.listVars();
                continue;
            }
            if(line.equals("")) {
                System.out.print("");
                continue;
            }
            if(line.startsWith("/")) {
                System.out.println("Unknown command");
                continue;
            }
            Expression expression = new Expression(line);
            try {
                if (line.contains("=")) {
                    calculator.assign(expression);
                } else {
                    System.out.println(calculator.evaluate(expression));
                }
            } catch (CalculatorException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
