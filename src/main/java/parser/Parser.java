package parser;

import calculator.Calculator;
import exception.CalculatorException;
import expression.Expression;

public class Parser {
    private static Calculator calculator = Calculator.getInstance();

    public static void parse(String line) throws CalculatorException {
        if(line.equals("")) {
            System.out.print("");
        }
        else if(line.startsWith("/")) {
            parseCommand(line);
        } else {
            parseExpression(line);
        }
    }

    private static void parseExpression(String expression) throws CalculatorException {
        if(expression.contains("=")) {
            calculator.assign(new Expression(expression));
        } else {
            System.out.println(calculator.evaluate(new Expression(expression)));
        }
    }

    private static void parseCommand(String command) {
        switch (command) {
            case "/help":
                Calculator.printHelp();
                break;
            case "/vars":
                calculator.listVars();
                break;
            case "/drop":
                calculator.dropVars();
                break;
            default:
                System.err.println("Unknown command");
        }
    }
}
