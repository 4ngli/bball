/**
 * This class models a ball with center, radius, velocity
 */

package edu.brown.cs.angli.bball;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Ball implements Cloneable {

  // possible colors of the ball
  private static final Color[] colors =
      {Color.decode("#FF9AA2"), Color.decode("#FFB7B2"), Color.decode("#FFDAC1"),
          Color.decode("#E2F0CB"), Color.decode("#B5EAD7"), Color.decode("#C7CEEA")};

  private double x = 0;
  private double y = 0;
  private double dx = 0;
  private double dy = 0;
  private double radius;

  private float r = 0;
  private float g = 0;
  private float b = 0;
  private int paletteIndex = 0;

  Random rand;

  public Object clone() throws CloneNotSupportedException {
    Ball newBall = (Ball) super.clone();
    newBall.rand = new Random();

    return newBall;
  }

  public Ball(double x, double y, double dx, double dy, double r) {
    this.x = x;
    this.y = y;
    this.dx = dx;
    this.dy = dy;
    this.radius = r;
    rand = new Random();
    this.changeRandomPaletteColor();
  }

  // Compute the Euclidean distance between two points
  public static double euclideanDistance(double x1, double y1, double x2, double y2) {
    return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
  }

  // Find the nearest ball given a coordinate
  // This method is to used to determine whether the click is on a ball or on blank space
  public static Ball getNearestBall(ArrayList<Ball> balls, double x, double y) {
    if (balls.size() == 0) {
      return null;
    }

    Ball nearest = balls.get(0);
    double nearestDist = euclideanDistance(x, y, nearest.getX(), nearest.getY());
    for (Ball ball : balls) {
      double currentDist = euclideanDistance(x, y, ball.getX(), ball.getY());
      if (currentDist < nearestDist) {
        nearest = ball;
        nearestDist = currentDist;
      }
    }

    return nearest;
  }

  // A few helper functions to compute the distance
  public double distance(Ball b2) {
    return Math.sqrt(
        (this.x - b2.getX()) * (this.x - b2.getX()) + (this.y - b2.getY()) * (this.y - b2.getY()));
  }

  public double distance(double x1, double y1) {
    return euclideanDistance(x1, y1, this.x, this.y);
  }

  // Getters
  public double getX() {
    return this.x;
  }

  public double getY() {
    return this.y;
  }

  public double getDx() {
    return this.dx;
  }

  public double getDy() {
    return dy;
  }

  public void setDy(double dy) {
    this.dy = dy;
  }

  public double getRadius() {
    return radius;
  }


  // Setters
  public void setRadius(double radius) {
    this.radius = radius;
  }

  public void setX(double x) {
    this.x = x;
  }

  public void setY(double y) {
    this.y = y;
  }

  public void setDx(double dx) {
    this.dx = dx;
  }


  // Change the color of the ball randomly
  public void changeRandomColor() {
    this.r = rand.nextFloat();
    this.g = rand.nextFloat();
    this.b = rand.nextFloat();
  }

  public void changeRandomPaletteColor() {
    int newColor = rand.nextInt(colors.length);
    while (newColor == this.paletteIndex) {
      newColor = rand.nextInt(colors.length);
    }
    this.paletteIndex = newColor;

  }

  // Color getters
  public Color getColor() {
    return new Color(this.r, this.g, this.b);
  }

  public Color getPaletteColor() {
    return colors[this.paletteIndex];
  }


  // Modify the location of the ball according to velocity
  public void move() {
    this.x += this.dx;
    this.y += this.dy;
  }
}
