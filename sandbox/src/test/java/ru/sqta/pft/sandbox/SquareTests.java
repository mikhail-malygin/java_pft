package ru.sqta.pft.sandbox;

import org.testng.Assert;

import org.testng.annotations.Test;
import ru.stqa.pft.sandbox.Square;

public class SquareTests {

    @Test
    public void testArea(){
        Square square = new Square(5);
        square.area();
        Assert.assertEquals(square.area(), 25);
    }
}
