package shotsd;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Deque;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

  private BufferedImage image;
  private double scaleFactor;
  private AffineTransform scaleTransform
      = AffineTransform.getScaleInstance(scaleFactor, scaleFactor);
  private Deque<PointCollection> pointCollections;

  private void fillDot(Graphics2D g, Point2D p) {
    int x = (int) (p.getX() - 10);
    int y = (int) (p.getY() - 10);
    int w = 20;
    int h = 20;
    g.setColor(Color.red);
    g.fillOval(x, y, w, h);
  }

  public ImagePanel(BufferedImage image,
      double scaleFactor,
      Deque<PointCollection> pointCollections) {
    this.image = image;
    this.pointCollections = pointCollections;
    this.scaleFactor = scaleFactor;
    this.scaleTransform = AffineTransform.getScaleInstance(scaleFactor, scaleFactor);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.transform(scaleTransform);
    g2d.drawImage(image, 0, 0, null);
    for (PointCollection pointCollection : pointCollections) {
      for (int i = 0; i < pointCollection.getPointCount(); i++) {
        fillDot(g2d, pointCollection.getPoint(i));
      }

      // crosshairs for mean point
      g2d.setColor(Color.BLUE);
      Point2D meanPoint = pointCollection.getMean();
      int mX = (int) meanPoint.getX();
      int mY = (int) meanPoint.getY();
      g2d.drawLine(mX, 0, mX, 3000);
      g2d.drawLine(0, mY, 3000, mY);

      // circles for standard deviation
      g2d.setColor(Color.ORANGE);
      int sd = (int) pointCollection.getSD();
      g2d.drawOval(mX - sd / 2, mY - sd / 2, sd, sd);
      g2d.drawOval(mX - sd, mY - sd, 2 * sd, 2 * sd);
      g2d.drawOval(mX - 3 * sd / 2, mY - 3 * sd / 2, 3 * sd, 3 * sd);
    }
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(
        (int) (image.getWidth() * scaleFactor),
        (int) (image.getHeight() * scaleFactor));
  }

  public void setScale(double scale) {
    scaleFactor = scale;
    scaleTransform = AffineTransform.getScaleInstance(scale, scale);
    revalidate();
    repaint();
  }

  @Override
  public void revalidate() {
    super.revalidate();
    System.out.println("revalidating image panel");
  }
}