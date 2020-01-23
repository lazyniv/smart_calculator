package calculator;

import calculator.Calculator;
import exceptions.CalculatorException;
import exceptions.InvalidAssignmentException;
import exceptions.InvalidIdentifierException;
import expression.Expression;
import org.junit.Assert;
import org.junit.Test;
import token.Token;

import javax.accessibility.AccessibleStateSet;

public class CalculatorTest {

    @Test
    public void evaluate_SimpleExpressionGiven_ShouldBeWorkRight() throws CalculatorException {
        String test = "1 + 2 * 3";
        Expression testExpression = new Expression(test);
        Calculator calculator = new Calculator();
        int actual = calculator.evaluate(testExpression);
        Assert.assertEquals(7, actual);
    }

    @Test
    public void evaluate_ExpressionWithParenthesesGiven_ShouldBeWorkRight() throws CalculatorException {
        String test = "8 * 3 + 12 * (4 - 2)";
        Expression testExpression = new Expression(test);
        Calculator calculator = new Calculator();
        int actual = calculator.evaluate(testExpression);
        Assert.assertEquals(48, actual);
    }

    @Test
    public void evaluate_LongExpressionGiven_ShouldBeWorkRight() throws CalculatorException {
        String test = "3 + 8 * ((4 + 3) * 2 + 1) - 6 / (2 + 1)";
        Expression testExpression = new Expression(test);
        Calculator calculator = new Calculator();
        int actual = calculator.evaluate(testExpression);
        Assert.assertEquals(121, actual);
    }

    @Test
    public void evaluate_ExpressionWithPowerGiven_ShouldBeWorkRight() throws CalculatorException {
        String test = "2*2^3";
        Expression testExpression = new Expression(test);
        Calculator calculator = new Calculator();
        int actual = calculator.evaluate(testExpression);
        Assert.assertEquals(16, actual);
    }

    @Test
    public void evaluate_ExpressionWithPlusesAndMinusesInARow_ShouldBeWorkRight() throws CalculatorException {
        String test = "1 +++ 2 * 3 -- 4";
        Expression testExpression = new Expression(test);
        Calculator calculator = new Calculator();
        int actual = calculator.evaluate(testExpression);
        Assert.assertEquals(11, actual);
    }

    @Test
    public void evaluate_ExpressionWithUnaryMinusGiven_ShouldBeWorkRight() throws CalculatorException {
        String test = "-1 + -3";
        Expression testExpression = new Expression(test);
        Calculator calculator = new Calculator();
        int actual = calculator.evaluate(testExpression);
        Assert.assertEquals(-4, actual);
    }

    @Test
    public void assign_varEqual10Given_varShouldBeEqualTo10() throws CalculatorException {
        String test = "var = 5";
        Expression assignment = new Expression(test);
        Calculator calculator = new Calculator();
        calculator.assign(assignment);
        Assert.assertEquals(5, calculator.getVariable(new Token("var")));
    }

    @Test
    public void assign_LongAssignmentGiven_ShouldBeWorkRight() throws CalculatorException {
        String test = "var = 10 - 5^2 * (4-2) - -2";
        Expression assignment = new Expression(test);
        Calculator calculator = new Calculator();
        calculator.assign(assignment);
        Assert.assertEquals(-38, calculator.getVariable(new Token("var")));
    }

    @Test
    public void assign_AssignmentWithoutSpacesAroundVariableNameGiven_ShouldBeWorkRight() throws CalculatorException {
        String test = "var=10 - 1";
        Expression assignment = new Expression(test);
        Calculator calculator = new Calculator();
        calculator.assign(assignment);
        Assert.assertEquals(9, calculator.getVariable(new Token("var")));
    }

    @Test
    public void assign_AssignmentFromAnotherVariableGiven_ShouldBeWorkRight() throws CalculatorException {
        String test = "var = a";
        Expression assignment = new Expression(test);
        Calculator calculator = new Calculator();
        calculator.assign(new Expression("a = 100"));
        calculator.assign(assignment);
        Assert.assertEquals(100, calculator.getVariable(new Token("var")));
    }

    @Test(expected = InvalidIdentifierException.class)
    public void assign_AssignmentWithInvalidIdentifierGiven_ShouldThrowsInvalidIdentifierException() throws CalculatorException {
        String test = "var100 = 100";
        Expression assignment = new Expression(test);
        Calculator calculator = new Calculator();
        calculator.assign(assignment);
    }


    @Test(expected = InvalidAssignmentException.class)
    public void assign_AssignmentWithInvalidAssignmentGiven_ShouldThrowsInvalidAssignmentException() throws CalculatorException {
        String test = "var = 100 = 100";
        Expression assignment = new Expression(test);
        Calculator calculator = new Calculator();
        calculator.assign(assignment);
    }


}
