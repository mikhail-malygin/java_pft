package ru.sqta.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.sandbox.Equation;

public class EquationTests {

    @Test
    public void Test0() {
        Equation equation = new Equation(1, 1, 1);
        Assert.assertEquals(equation.rootNumber(), 0);
    }

    @Test
    public void Test1() {
        Equation equation = new Equation(1, 2, 1);
        Assert.assertEquals(equation.rootNumber(), 1);
    }

    @Test
    public void Test2() {
        Equation equation = new Equation(1, 5, 6);
        Assert.assertEquals(equation.rootNumber(), 2);
    }
}
