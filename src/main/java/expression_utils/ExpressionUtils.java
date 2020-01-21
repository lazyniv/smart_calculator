package expression_utils;

public class ExpressionUtils {

    public static String eliminateMultiplePlusAndMinus(String expression) {
        return expression.replaceAll("-(--)+", "-")
                .replaceAll("(--)+", "+")
                .replaceAll("\\++", "+");
    }

    public static String setUnaryOperators(String expression) { //FIXME костыль
        return expression.replaceAll("(?<=(\\s|[*/^]|\\^))-(?=\\w)", "~")
                .replaceAll("(?<=(\\s|[*/^]|\\^))\\+(?=\\w)", "#");
    }

}
