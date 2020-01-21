package expression_utils;

import org.junit.Assert;
import org.junit.Test;

public class ExpressionUtilsTest {

    @Test
    public void eliminateMultiplePlusAndMinus_StringWhichContainsSeveralPlusesInARowGiven_ShouldBeRight() {
        String test = "a +++ b";
        String expected = "a + b";
        Assert.assertEquals(expected, ExpressionUtils.eliminateMultiplePlusAndMinus(test));
    }

    @Test
    public void eliminateMultiplePlusAndMinus_StringWhichContainsSeveralPlusesInARowWithoutSpacesGiven_ShouldBeRight() {
        String test = "a+++b";
        String expected = "a+b";
        Assert.assertEquals(expected, ExpressionUtils.eliminateMultiplePlusAndMinus(test));
    }

    @Test
    public void eliminateMultiplePlusAndMinus_StringWhichContainsEvenNumberMinusesInARowGiven_ShouldBeRight() {
        String test = "a ---- b";
        String expected = "a + b";
        Assert.assertEquals(expected, ExpressionUtils.eliminateMultiplePlusAndMinus(test));
    }

    @Test
    public void eliminateMultiplePlusAndMinus_StringWhichContainsOddNumberMinusesInARowGiven_ShouldBeRight() {
        String test = "a --- b";
        String expected = "a - b";
        Assert.assertEquals(expected, ExpressionUtils.eliminateMultiplePlusAndMinus(test));
    }

    @Test
    public void eliminateMultiplePlusAndMinus_StringWhichContainsEvenNumberMinusesInARowWithoutSpacesGiven_ShouldBeRight() {
        String test = "a----b";
        String expected = "a+b";
        Assert.assertEquals(expected, ExpressionUtils.eliminateMultiplePlusAndMinus(test));
    }

    @Test
    public void eliminateMultiplePlusAndMinus_StringWhichContainsOddNumberMinusesInARowWithoutSpacesGiven_ShouldBeRight() {
        String test = "a---b";
        String expected = "a-b";
        Assert.assertEquals(expected, ExpressionUtils.eliminateMultiplePlusAndMinus(test));
    }

    @Test
    public void  setUnaryOperators_StringWhereFirstSignIsMinusGiven_ShouldBeRight() {
        String test = "-a * +b";
        String expected = "~a * #b";
        Assert.assertEquals(expected, ExpressionUtils.setUnaryOperators(test));
    }

    @Test
    public void  setUnaryOperators_StringWhereFirstSignIsPlus_ShouldBeRight() {
        String test = "+a * -b";
        String expected = "#a * ~b";
        Assert.assertEquals(expected, ExpressionUtils.setUnaryOperators(test));
    }

    @Test
    public void  setUnaryOperators_StringWhichContainsSeveralMinusesInARowWithoutSpacesGiven_ShouldBeRight() {
        String test = "a---b";
        String expected = "a---b";
        Assert.assertEquals(expected, ExpressionUtils.setUnaryOperators(test));
    }

    @Test
    public void  setUnaryOperators_StringWhichContainsSeveralPlusesInARowWithoutSpacesGiven_ShouldBeRight() {
        String test = "a+++b";
        String expected = "a+++b";
        Assert.assertEquals(expected, ExpressionUtils.setUnaryOperators(test));
    }

}
