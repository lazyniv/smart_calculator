package expression;

import token.Token;

import java.util.*;
import java.util.regex.Pattern;

public class Expression {
    private String expression;

    private static final Pattern CALCULATION_PATTERN = Pattern.compile("(\\+*|-*|\\(*)([a-zA-Z]+|\\d+)(\\s*(\\++|-+|/+)\\s*(\\+*|-*)\\(*([a-zA-Z]+|\\d+)\\)*)*"); //FIXME decompose it

    private static final Pattern ASSIGNMENT_PATTERN = Pattern.compile("([a-zA-aZ]|\\d)+\\s*=.+");//FIXME would enough "="

    private static final Pattern DELIMITER_PATTERN = Pattern.compile("[+\\-*/^()]"); //FIXME change name

    private static final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";

    private static final Map<Token, Integer> OPERATOR_TO_PRIORITY = Map.of(
            new Token("^"), 3, new Token("*"), 2, new Token("/"), 2,
            new Token("+"), 1, new Token("-"), 1
    );
    public Expression(String expression) {
        this.expression = expression;
    }

    public boolean isAssignment() {
        return ASSIGNMENT_PATTERN.matcher(expression).matches();
    }

    public boolean isCalculation() {
        return CALCULATION_PATTERN.matcher(expression).matches();
    }

    public List<Token> toPostfixTokensList() {
        return this.toTokensList();
    }

    public List<Token> toTokensList() {
        String[] tokens = expression.split(String.format(WITH_DELIMITER,DELIMITER_PATTERN));
        List<Token> tokensList = new ArrayList<>();
        Arrays.stream(tokens).forEach(t -> tokensList.add(new Token(t)));
        return tokensList;
    }
}
