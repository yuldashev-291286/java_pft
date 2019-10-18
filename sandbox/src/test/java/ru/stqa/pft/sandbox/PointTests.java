package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  Point p1 = new Point(1.0, 2.0);
  Point p2 = new Point(3.0, 4.0);

  @Test
  public void testPoint(){
    Assert.assertEquals(Point.distance(p1, p2), Math.sqrt(Math.pow((p2.x - p1.x), 2) + Math.pow((p2.y - p1.y), 2)));
  }

}
