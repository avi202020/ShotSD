package geometry;

import java.awt.geom.Point2D;
import org.junit.Assert;
import org.junit.Test;

public class TestCircle {
  @Test
  public void unitCircleFromDiameter() {
    Circle c = Circle.ofDiameterPoints(new Point2D.Double(-1, 0), new Point2D.Double(1, 0));
    Assert.assertEquals("Center should be zero, zero", 0, c.getCenter().getX(), 0.000001);
    Assert.assertEquals("Center should be zero, zero", 0, c.getCenter().getY(), 0.000001);
    Assert.assertEquals("Radius should be one", 1, c.getRadius(), 0.000001);
  }
  
  @Test
  public void circleFromDiagonal() {
    Circle c = Circle.ofDiameterPoints(new Point2D.Double(-1, -1), new Point2D.Double(1, 1));
    Assert.assertEquals("Center should be zero, zero", 0, c.getCenter().getX(), 0.000001);
    Assert.assertEquals("Center should be zero, zero", 0, c.getCenter().getY(), 0.000001);
    Assert.assertEquals("Radius should be 1.4", Math.sqrt(2), c.getRadius(), 0.000001);
  }

  @Test
  public void circleFromOffcenterDiagonal() {
    Circle c = Circle.ofDiameterPoints(new Point2D.Double(1, 1), new Point2D.Double(3, 2));
    Assert.assertEquals("Center should be 2, 1.5", 2, c.getCenter().getX(), 0.000001);
    Assert.assertEquals("Center should be 2, 1.5", 1.5, c.getCenter().getY(), 0.000001);
    Assert.assertEquals("Radius should be 1.18", Math.sqrt(1+0.5*0.5), c.getRadius(), 0.000001);
  }
  
  @Test
  public void circleFromTriple() {
    Point2D.Double a = new Point2D.Double(6, 4+Math.sqrt(7));
    Point2D.Double b = new Point2D.Double(5, 4+Math.sqrt(12));
    Point2D.Double c = new Point2D.Double(4, 4-Math.sqrt(15));
    Circle circle = Circle.of(a, b, c);
    Assert.assertEquals("Center should be 3,4 ", 3, circle.getCenter().getX(), 0.00001);
    Assert.assertEquals("Center should be 3,4 ", 4, circle.getCenter().getY(), 0.00001);
    Assert.assertEquals("Radius should be 4 ", 4, circle.getRadius(), 0.00001);
  }
  
  @Test
  public void containsCenter() {
    Circle c = Circle.ofDiameterPoints(new Point2D.Double(-1, -1), new Point2D.Double(1, 1));
    Assert.assertTrue("Should contain center", c.contains(new Point2D.Double(0, 0)));
  }

  @Test
  public void containsBoundary() {
    Circle c = Circle.ofDiameterPoints(new Point2D.Double(-1, -1), new Point2D.Double(1, 1));
    Assert.assertTrue("Should contain point just inside circumference", 
        c.contains(new Point2D.Double(0.7, 0.7)));
  }

  @Test
  public void doesNotContainsBeyondBoundary() {
    Circle c = Circle.ofDiameterPoints(new Point2D.Double(-1, -1), new Point2D.Double(1, 1));
    Assert.assertFalse("Should not contain point just outside circumference", 
        c.contains(new Point2D.Double(0.8, 0.8)));
  }
}