package expression;

import exception.CalculatorException;
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
    public void isAssignment_AssignmentWhichContainEqualAtTheEndGiven_False() {
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
    public void isCalculation_ExpressionWhichContainsUnaryMinusGiven_True() {
        String test = "-a + -b";
        Expression testExpression = new Expression(test);
        Assert.assertTrue(testExpression.isCalculation());
    }

    @Test
    public void isCalculation_ExpressionWhichContainsUnaryPlusGiven_True() {
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

    @Test
    public void isCalculation_ExpressionWithOperatorAtTheEnd_False() {
        String test = "a + b +";
        Expression testExpression = new Expression(test);
        Assert.assertFalse(testExpression.isCalculation());
    }

    @Test
    public void isCalculation_ExpressionWithMissedValue_False() {
        String test = "a + + b";
        Expression testExpression = new Expression(test);
        Assert.assertFalse(testExpression.isCalculation());
    }



    @Test
    public void toTokensList_SimpleExpressionGiven_ShouldBeRightResult() {
        String test = "a + b * c";
        List<Token> expected = List.of(
                new Token("a"), new Token("+"), new Token("b"),
                new Token("*"), new Token("c")
        );
        Expression testExpression = new Expression(test);
        List<Token> actual = testExpression.toTokensList();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void toTokensList_ExpressionWithoutSpacesGiven_ShouldBeRightResult() {
        String test = "100-x^y*(a+b)";
        List<Token> expected = List.of(
                new Token("100"), new Token("-"), new Token("x"),
                new Token("^"), new Token("y"), new Token("*"),
                new Token("("), new Token("a"), new Token("+"),
                new Token("b"), new Token(")")
        );
        Expression testExpression = new Expression(test);
        List<Token> actual = testExpression.toTokensList();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void toTokensList_ExpressionWhichContainsLongVariableAndValueGiven_ShouldBeRightResult() {
        String test = "variable1 + variable2 / 1000";
        List<Token> expected = List.of(
                new Token("variable1"), new Token("+"),
                new Token("variable2"), new Token("/"),
                new Token("1000")
        );
        Expression testExpression = new Expression(test);
        List<Token> actual = testExpression.toTokensList();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void toTokensList_ExpressionWithUnaryPlusGiven_ShouldBeRightResult() {
        String test = "+a-b";
        List<Token> expected = List.of(
                new Token("#"), new Token("a"),
                new Token("-"), new Token("b")
        );
        Expression testExpression = new Expression(test);
        List<Token> actual = testExpression.toTokensList();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void ToPostfixTokensList_SimpleExpressionGiven_ShouldBeRightResult() throws CalculatorException {
        String test = "a + b * c";
        List<Token> expected = List.of(
                new Token("a"), new Token("b"), new Token("c"),
                new Token("*"), new Token("+")
        );
        Expression testExpression = new Expression(test);
        List<Token> actual = testExpression.toPostfixTokensList();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void ToPostfixTokensList_ExpressionWithPairedParenthesesGiven_ShouldBeRightResult() throws CalculatorException {
        String test = "(a + b) * c";
        List<Token> expected = List.of(
                new Token("a"), new Token("b"), new Token("+"),
                new Token("c"), new Token("*")
        );
        Expression testExpression = new Expression(test);
        List<Token> actual = testExpression.toPostfixTokensList();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void ToPostfixTokensList_ExpressionWithoutSpacesGiven_ShouldBeRightResult() throws CalculatorException {
        String test = "100-x^y*(a+b)";
        List<Token> expected = List.of(
                new Token("100"), new Token("x"), new Token("y"),
                new Token("^"), new Token("a"), new Token("b"),
                new Token("+"), new Token("*"), new Token("-")
        );
        Expression testExpression = new Expression(test);
        List<Token> actual = testExpression.toPostfixTokensList();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = CalculatorException.class)
    public void ToPostfixTokensList_ExpressionWithUnpairedRightBracketGiven_ShouldThrowsInvalidExpressionException() throws CalculatorException {
        String test = "(a + b)) * c";
        Expression testExpression = new Expression(test);
        testExpression.toPostfixTokensList();
    }

    @Test(expected = CalculatorException.class)
    public void ToPostfixTokensList_ExpressionWithLeftParenthesesGiven_ShouldThrowsInvalidExpressionException() throws CalculatorException {
        String test = "(a + b) * (c";
        Expression testExpression = new Expression(test);
        testExpression.toPostfixTokensList();
    }

    @Test
    public void ToPostfixTokensList_ExpressionWithUnaryMinusGiven_ShouldBeRightResult() throws CalculatorException {
        String test = "-a + -b";
        List<Token> expected = List.of(
                new Token("a"), new Token("~"), new Token("b"),
                new Token("~"), new Token("+")
        );
        Expression testExpression = new Expression(test);
        List<Token> actual = testExpression.toPostfixTokensList();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void toPostfixTokensList_ExpressionWithUnaryPlusGiven_ShouldBeRightResult() throws CalculatorException {
        String test = "+a-b";
        List<Token> expected = List.of(
                new Token("a"), new Token("#"),
                new Token("b"), new Token("-")
        );
        Expression testExpression = new Expression(test);
        List<Token> actual = testExpression.toPostfixTokensList();
        Assert.assertEquals(expected, actual);
    }


}
