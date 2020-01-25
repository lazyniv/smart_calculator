package main;

import exceptions.CalculatorException;
import parser.Parser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        printGreeting();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print("\n> ");
            String line = scanner.nextLine();
            if(line.equals("/exit")) {
                System.out.println("Bye!");
                break;
            } else {
                try {
                    Parser.parse(line);
                } catch (CalculatorException | ArithmeticException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    public static void printGreeting() {
        System.out.println("Smart Calculator 1.0\n" +
                "type  \"/help\" for more information");
    }
}