package pl.lborowy.rockpaperscissors;

import org.junit.Assert;
import org.junit.Test;


/**
 * Created by RENT on 2017-08-29.
 */
public class CalculatorTest {
    @Test
    public void returnTwo() throws Exception {
        Calculator calculator = new Calculator();
        Assert.assertEquals(2, calculator.returnTwo());
    }

}