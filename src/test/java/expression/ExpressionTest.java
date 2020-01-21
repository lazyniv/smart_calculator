package expression;

import org.junit.Assert;
import org.junit.Test;
import token.Token;

import java.util.List;

public class ExpressionTest {

    @Test
    public void isAssignment_SimpleAssignmentGiven_True() {
        String test = "var = a + b + 10";
        Expression testExpression = new Expression(test);
        Assert.assertTrue(testExpression.isAssignment());
    }

    @Test
    public void isAssignment_AssignmentWithoutSpacesGiven_True() {
        String test = "var=a*b";
        Expression testExpression = new Expression(test);
        Assert.assertTrue(testExpression.isAssignment());
    }

    @Test
    public void isAssignment_AssignmentWithMoreThenOneSignEqualGiven_False() {
        String test = "var=a=b";
        Expression testExpression = new Expression(test);
        Assert.assertFalse(testExpression.isAssignment());
    }

    @Test
    public void isAssignment_AssignmentWithVariableNameThatContainsDigitGiven_True() {
        String test = "var10 = a + b";
        Expression testExpression = new Expression(test);
        Assert.assertTrue(testExpression.isAssignment());
    }

    @Test
    public void isAssignment_AssignmentWithVariableNameAsNumberGiven_False() {
        String test = "100 = a + b";
        Expression testExpression = new Expression(test);
        Assert.assertFalse(testExpression.isAssignment());
    }

    @Test
    public void isAssignment_AssignmentWhichContainEqualAtTheEndGiven_False() { //FIXME change name
        String test = "foo * bar = 1";
        Expression testExpression = new Expression(test);
        Assert.assertFalse(testExpression.isAssignment());
    }

    @Test
    public void isCalculation_SimpleExpressionGiven_True() {
        String test = "a + 1";
        Expression testExpression = new Expression(test);
        Assert.assertTrue(testExpression.isCalculation());
    }

    @Test
    public void isCalculation_ExpressionThatContainsAllOperatorsGiven_True() {
        String test = "a * b / c ^ k - 100 + 200";
        Expression testExpression = new Expression(test);
        Assert.assertTrue(testExpression.isCalculation());
    }

    @Test
    public void isCalculation_ExpressionThatContainsPairedParenthesesGiven_True() {
        String test = "(foo + bar)*var";
        Expression testExpression = new Expression(test);
        Assert.assertTrue(testExpression.isCalculation());
    }

    @Test
    public void isCalculation_ExpressionWithoutSpacesGiven_True() {
        String test = "100/5*(1+4)";
        Expression testExpression = new Expression(test);
        Assert.assertTrue(testExpression.isCalculation());
    }

    @Test
    public void isCalculation_ExpressionWhichContainsSeveralPlusesInARowGiven_True() {
        String test = "foo ++++ bar";
        Expression testExpression = new Expression(test);
        Assert.assertTrue(testExpression.isCalculation());
    }

    @Test
    public void isCalculation_ExpressionWhichContainsSeveralMinusesInARowGiven_True() {
        String test = "foo ---- bar";
        Expression testExpression = new Expression(test);
        Assert.assertTrue(testExpression.isCalculation());
    }

    @Test
    public void isCalculation_ExpressionWhichContainsSeveralMulsOrDivsOrPowsInARowGiven_False() {
        String test = "foo ** bar // foo ^^ bar";
        Expression testExpression = new Expression(test);
        Assert.assertFalse(testExpression.isCalculation());
    }

    @Test
    public void isCalculation_ExpressionWhichContainsUnaryMinus_True() {
        String test = "-a + -b";
        Expression testExpression = new Expression(test);
        Assert.assertTrue(testExpression.isCalculation());
    }

    @Test
    public void isCalculation_ExpressionWhichContainsUnaryPlus_True() {
        String test = "+a + +b";
        Expression testExpression = new Expression(test);
        Assert.assertTrue(testExpression.isCalculation());
    }

    @Test
    public void isCalculation_ExpressionWhichContainsAlternationPlusAndMinusGiven_False() {
        String test = "a +- b";
        Expression testExpression = new Expression(test);
        Assert.assertFalse(testExpression.isCalculation());
    }

    @Test
    public void isCalculation_ExpressionWithMissedOperatorGiven_False() {
        String test = "bar foo";
        Expression testExpression = new Expression(test);
        Assert.assertFalse(testExpression.isCalculation());
    }

   // @Test
    /*public void toTokensList_SimpleExpressionGiven_ShouldBeRightResult() {
        String test = "a + b * c";
        List<Token> expected = List.of(
                new Token("a"), new Token("+"), new Token("b"),
                new Token("*"), new Token("c")
        );
        Expression testExpression = new Expression(test);
        List<Token> result = testExpression.toTokensList();
        Assert.assertEquals(result, expected);
    }

        String withoutSpacesTest = "100-x^y*(a+b)";
        List<Token> expectedWithoutSpacesTestTokensList = List.of(
                new Token("100"), new Token("-"), new Token("x"),
                new Token("^"), new Token("y"), new Token("*"),
                new Token("("), new Token("a"), new Token("+"),
                new Token(")")
        );
        Assert.assertEquals(new Expression(simpleTest).toTokensList(), expectedWithoutSpacesTestTokensList);

        String unaryMinusTest = "-a";
        List<Token> expectedUnaryMinusTestTokensList = List.of(
                new Token("-"), new Token("a")
        );
        Assert.assertEquals(new Expression(unaryMinusTest).toTokensList(), expectedUnaryMinusTestTokensList);

        String unaryPlusTest = "+a";
        List<Token> expectedUnaryPlusTestTokensList = List.of(
                new Token("+"), new Token("a")
        );
        Assert.assertEquals(new Expression(unaryPlusTest).toTokensList(), expectedUnaryPlusTestTokensList);

        String longVariableAndValueTest = "variable1 + variable2 / 1000";
        List<Token> expectedLongVariableAndValueTestTokensList = List.of(
                new Token("variable1"), new Token("+"),
                new Token("variable2"), new Token("/"),
                new Token("1000")
        );
    }

    @Test
    public void testToPostfixTokensList() {
        String simpleTest = "a + b * c";
        List<Token> expectedSimpleTestPostfixTokensList = List.of(
                new Token("a"), new Token("b"), new Token("c"),
                new Token("*"), new Token("+")
        );
        Assert.assertEquals(new Expression(simpleTest).toTokensList(), expectedSimpleTestPostfixTokensList);

        String withoutSpacesTest = "100-x^y*(a+b)";
        List<Token> expectedWithoutSpacesTestPostfixTokensList = List.of(
                new Token("100"), new Token("x"), new Token("y"),
                new Token("^"), new Token("a"), new Token("b"),
                new Token("+"), new Token("*"), new Token("-")
        );
        Assert.assertEquals(new Expression(simpleTest).toTokensList(), expectedWithoutSpacesTestPostfixTokensList);

        String unaryMinusTest = "-a";
        List<Token> expectedUnaryMinusTestPostfixTokensList = List.of(
                new Token("a"), new Token("~") // "~" represents a unary minus
        );
        Assert.assertEquals(new Expression(unaryMinusTest).toTokensList(), expectedUnaryMinusTestPostfixTokensList);

        String unaryPlusTest = "+a";
        List<Token> expectedUnaryPlusTestPostfixTokensList = List.of(
                new Token("a"), new Token("#") // "#" represents a unary plus
        );
        Assert.assertEquals(new Expression(unaryPlusTest).toTokensList(), expectedUnaryPlusTestPostfixTokensList);

        String longVariableAndValueTest = "variable1 + variable2 / 1000";
        List<Token> expectedLongVariableAndValueTestPostfixTokensList = List.of(
                new Token("variable1"), new Token("variable2"),
                new Token("1000"), new Token("/"),
                new Token("+")
        );
        Assert.assertEquals(new Expression(longVariableAndValueTest).toTokensList(),
                expectedLongVariableAndValueTestPostfixTokensList
        );


    }*/

}
