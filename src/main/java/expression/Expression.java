package expression;

import exception.CalculatorException;
import token.Token;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Expression {
    private String expression;

    private static final String VARIABLE_OR_NUMBER_TEMPLATE = "\\(*[+-]?([a-zA-Z]+|\\d+)\\)*";

    private static final Pattern CALCULATION_PATTERN = Pattern.compile(
            String.format("%1$s(\\s*(\\++|\\-+|[*^/])\\s*%1$s)*", VARIABLE_OR_NUMBER_TEMPLATE)
    );

    private static final Pattern ASSIGNMENT_PATTERN = Pattern.compile("[a-zA-Z]([a-zA-Z]|\\d)*\\s*=\\s*" +
            CALCULATION_PATTERN.pattern());

    private static final Pattern DELIMITER_PATTERN = Pattern.compile("[+\\-*/^()#~]");

    private static final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";

    private static final Map<Token, Integer> OPERATOR_TO_PRIORITY = Map.of(
            Token.UNARY_MINUS, 4, Token.UNARY_PLUS, 4,
            new Token("^"), 3, new Token("*"), 2, new Token("/"), 2,
            new Token("+"), 1, new Token("-"), 1
    );

    public Expression(String expression) {
        this.expression = expression;
    }

    public Expression(Token token) {
        this.expression = token.toString();
    }

    public boolean isAssignment() {
        return ASSIGNMENT_PATTERN.matcher(expression).matches();
    }

    public boolean isCalculation() {
        return CALCULATION_PATTERN.matcher(expression).matches();
    }

    public List<Token> toPostfixTokensList() throws CalculatorException {
        if(!isCalculation()) {
            throw new CalculatorException("Invalid expression");
        }

        Deque<Token> stack = new ArrayDeque<>();
        List<Token> tokensList = toTokensList();
        List<Token> postfixTokensList = new ArrayList<>();

        for(Token token : tokensList) {
            if(token.isVariable() || token.isNumber()) {
                postfixTokensList.add(token);
            } else {
                if(token.equals(Token.LEFT_BRACKET)) {
                    stack.offerLast(token);
                }
                else if(token.equals(Token.RIGHT_BRACKET)) {
                    if(!stack.contains(Token.LEFT_BRACKET)){
                        throw new CalculatorException("Invalid expression");
                    }
                    while(!stack.peekLast().equals(Token.LEFT_BRACKET)) {
                        postfixTokensList.add(stack.pollLast());
                    }
                    stack.pollLast();
                } else {
                    while(!stack.isEmpty() && !stack.peekLast().equals(Token.LEFT_BRACKET )
                            && OPERATOR_TO_PRIORITY.get(token) <= OPERATOR_TO_PRIORITY.get(stack.peekLast())) {
                        postfixTokensList.add(stack.pollLast());
                    }
                    stack.offerLast(token);
                }
            }
        }
        while(!stack.isEmpty() && !stack.peekLast().equals(Token.LEFT_BRACKET)) {
            postfixTokensList.add(stack.pollLast());
        }
        if(!stack.isEmpty()) {
            throw new CalculatorException("Invalid expression");
        }
        return postfixTokensList;
    }

    public List<Token> split(String regex, int limit) {
        String[] tokens = expression.split(regex, limit);
        List<Token> tokensList = new ArrayList<>();
        Arrays.stream(tokens).forEach(t -> tokensList.add(new Token(t)));
        return tokensList;
    }

    public List<Token> toTokensList() {
        String preparedExpression = ExpressionUtils.eliminateMultiplePlusAndMinus(
                ExpressionUtils.setUnaryOperators(expression)
        );
        String[] tokens = preparedExpression.replaceAll("\\s+","").split(String.format(WITH_DELIMITER,DELIMITER_PATTERN));
        List<Token> tokensList = Arrays.stream(tokens)
                .map(Token::new)
                .collect(Collectors.toList());
        return tokensList;
    }

    @Override
    public String toString() {
        return expression;
    }
}
