package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  Point p1 = new Point(1.0, 2.0);
  Point p2 = new Point(3.0, 4.0);

  @Test
  public void testPoint(){
    Assert.assertEquals(p1.distance(p2), Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2)));
  }

}
