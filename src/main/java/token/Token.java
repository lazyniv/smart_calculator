package token;

import java.util.Objects;
import java.util.regex.Pattern;

public class Token {
    private final String token;

    private static final Pattern VARIABLE_PATTERN = Pattern.compile("[a-zA-Z]+"); //FIXME variable may have a prefix with sign "-" or "+"

    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+"); //FIXME number may have a prefix with sign "-" or "+"

    public static final Token LEFT_BRACKET = new Token("(");

    public static final Token RIGHT_BRACKET = new Token(")");

    public static final Token UNARY_MINUS = new Token("~");

    public static final Token UNARY_PLUS = new Token("#");

    public Token(String token) {
        this.token = token;
    }

    public boolean isVariable() {
        return VARIABLE_PATTERN.matcher(token).matches();
    }

    public boolean isNumber() {
        return NUMBER_PATTERN.matcher(token).matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token anotherToken = (Token) o;
        return Objects.equals(token, anotherToken.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }

    @Override
    public String toString() {
        return token;
    }
}
