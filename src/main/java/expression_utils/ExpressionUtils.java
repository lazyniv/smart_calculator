package expression_utils;

public class ExpressionUtils {

    private static final String UNARY_MINUS_REGEX = "(?<=(\\s|[*/^]|\\A))-(?=\\w)";

    private static final String UNARY_PLUS_REGEX = "(?<=(\\s|[*/^]|\\A))\\+(?=\\w)";



    public static String eliminateMultiplePlusAndMinus(String expression) {
        return expression.replaceAll("-(--)+", "-")
                .replaceAll("(--)+", "+")
                .replaceAll("\\++", "+");
    }

    public static String setUnaryOperators(String expression) { //FIXME костыль
        return expression.replaceAll(UNARY_MINUS_REGEX, "~")
                .replaceAll(UNARY_PLUS_REGEX, "#");
    }

}
