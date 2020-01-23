package calculator;

import exceptions.*;
import expression.Expression;
import token.Token;

import java.util.*;

public class Calculator {
    private Map<Token, Integer> variableToValue = new HashMap<>();

    public int evaluate(Expression expression) throws CalculatorException {
        if(!expression.isCalculation()) {
            throw new InvalidExpressionException("Invalid expression");
        }
        List<Token> postfixTokensList = expression.toPostfixTokensList();
        Deque<Token> stack = new ArrayDeque<>();

        for(Token token : postfixTokensList) {
            if(token.isNumber() || token.isVariable()) {
                stack.offerLast(token);
            }
            else if(token.equals(Token.UNARY_MINUS)) {
                int o = parseToken(stack.pollLast());
                stack.offerLast(new Token(-o));
            }
            else if(token.equals(Token.UNARY_PLUS)) { //FIXME
            }
            else {
                int o2 = parseToken(stack.pollLast());
                int o1 = parseToken(stack.pollLast());
                switch (token.toString()) {
                    case "^":
                        stack.offerLast(new Token((int) Math.pow(o1,o2)));
                        break;
                    case "+":
                        stack.offerLast(new Token(o1 + o2));
                        break;
                    case "-":
                        stack.offerLast(new Token(o1 - o2));
                        break;
                    case "*":
                        stack.offerLast(new Token(o1 * o2));
                        break;
                    case "/":
                        if(o2 == 0) {
                            throw new ArithmeticException("Division by zero!");
                        }
                        stack.offerLast(new Token(o1 / o2));
                }
            }
        }
        return parseToken(stack.pollLast());
    }

    public void assign(Expression assignment) throws CalculatorException {
        List<Token> tokensList = assignment.split("\\s*=\\s*", 2); //FIXME change name
        Token variable = tokensList.get(0);

        if(!variable.isVariable()) {
            throw new InvalidIdentifierException("Invalid identifier");
        }

        Expression calculationExpression = new Expression(tokensList.get(1));

        if(!calculationExpression.isCalculation()) {
            throw new InvalidAssignmentException("Invalid assignment");
        }

        int assignedValue = evaluate(calculationExpression);
        variableToValue.put(variable, assignedValue);
    }

    public int getVariable(Token variable) {
        return variableToValue.get(variable);
    }

    private int parseToken(Token token) throws CalculatorException { //May occurs left/right bracket
        if (token.isVariable()) {
            if (!variableToValue.containsKey(token)) {
                throw new UnknownVariableException("Unknown variable");
            }
            return variableToValue.get(token);
        }
        return Integer.parseInt(token.toString());
    }
}