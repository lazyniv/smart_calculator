package expression;

import org.junit.Assert;
import org.junit.Test;
import token.Token;

import java.util.List;

class ExpressionTest {
    @Test
    void testIsAssignment() {
        String test1 = "var = a + b + 10";
        Expression expressionTest1 = new Expression(test1);
        Assert.assertTrue(expressionTest1.isAssignment());

        String test2 = "var1=a*b";
        Expression expressionTest2 = new Expression(test2);
        Assert.assertTrue(expressionTest1.isAssignment());

        String test3 = "var=a=b";
        Expression expressionTest3 = new Expression(test3);
        Assert.assertTrue(expressionTest2.isAssignment());

        String test4 = "100=a+b";
        Expression expressionTest4 = new Expression(test4);
        Assert.assertTrue(expressionTest4.isAssignment());

        String test5 = "foo * bar / a = 1";
        Expression expressionTest5 = new Expression(test5);
        Assert.assertFalse(expressionTest5.isAssignment());
    }

    @Test
    void testIsCalculation() {
        String simpleTest = "1 + var";
        Assert.assertTrue(new Expression(simpleTest).isCalculation());

        String allOperatorsTest = "a * b / c ^ k - 100 + 200";
        Assert.assertTrue(new Expression(allOperatorsTest).isCalculation());

        String parenthesesTest  = "(foo + bar)*var";
        Assert.assertTrue(new Expression(parenthesesTest).isCalculation());

        String withoutSpacesTest = "100/5*(1+4)";
        Assert.assertTrue(new Expression(withoutSpacesTest).isCalculation());

        String unpairedParenthesesWrongTest = "(10 - 5)^a)";
        Assert.assertFalse(new Expression(unpairedParenthesesWrongTest).isCalculation());
        
        String multiplePlusTest = "foo ++++ bar";
        Assert.assertTrue(new Expression(multiplePlusTest).isCalculation());

        String multipleMinusTest = "foo ---- bar";
        Assert.assertTrue(new Expression(multipleMinusTest).isCalculation());

        String multipleMulDivPowWrongTest = "foo ** bar // foo ^^ bar";
        Assert.assertFalse(new Expression(multipleMulDivPowWrongTest).isCalculation());

        String unaryMinusTest = "-a + -b";
        Assert.assertTrue(new Expression(unaryMinusTest).isCalculation());

        String unaryPlusTest = "+a + +b";
        Assert.assertTrue(new Expression(unaryPlusTest).isCalculation());

        String alternationPlusMinusWrongTest = "a +- b";
        Assert.assertFalse(new Expression(alternationPlusMinusWrongTest).isAssignment());
    }

    @Test
    void testToTokensList() {
        String simpleTest = "a + b * c";
        List<Token> expectedSimpleTestTokensList = List.of(
                new Token("a"), new Token("+"), new Token("b"),
                new Token("*"), new Token("c")
        );
        Assert.assertEquals(new Expression(simpleTest).toTokensList(), expectedSimpleTestTokensList);

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
    void testToPostfixTokensList() {
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



    }

}
