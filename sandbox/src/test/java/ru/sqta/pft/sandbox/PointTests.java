package ru.sqta.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.sandbox.Point;

public class PointTests {
    @Test
    public void testDistance(){
        Point p1 = new Point(1.4, 5);
        Point p2 = new Point(2, -1.56);
        Assert.assertEquals(p1.distanceMethod(p2), 6.58738187749883);
    }
}
