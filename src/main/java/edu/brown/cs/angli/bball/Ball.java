/**
 * This class models a ball with center, radius, velocity
 * 
 * @author angli
 */

package edu.brown.cs.angli.bball;

import java.util.ArrayList;

public class Ball implements Cloneable {

  private double myX = 0;
  private double myY = 0;
  private double myDx = 0;
  private double myDy = 0;
  private double myRadius;


  private int myPaletteIndex = 0;
  private final int myNumColors = 4;

  public Object clone() throws CloneNotSupportedException {
    Ball newBall = (Ball) super.clone();
    return newBall;
  }

  public Ball(double x, double y, double dx, double dy, double r) {
    this.myX = x;
    this.myY = y;
    this.myDx = dx;
    this.myDy = dy;
    this.myRadius = r;
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
    return Math.sqrt((myX - b2.getX()) * (myX - b2.getX()) + (myY - b2.getY()) * (myY - b2.getY()));
  }

  public double distance(double x1, double y1) {
    return euclideanDistance(x1, y1, myX, myY);
  }

  // Getters
  public double getX() {
    return myX;
  }

  public double getY() {
    return myY;
  }

  public double getDx() {
    return myDx;
  }

  public double getDy() {
    return myDy;
  }

  public void setDy(double dy) {
    myDy = dy;
  }

  public double getRadius() {
    return myRadius;
  }


  // Setters
  public void setRadius(double radius) {
    myRadius = radius;
  }

  public void setX(double x) {
    myX = x;
  }

  public void setY(double y) {
    myY = y;
  }

  public void setDx(double dx) {
    myDx = dx;
  }


  public void changePaletteColor(int newColor) {
    if (newColor < myNumColors) {
      myPaletteIndex = newColor;
    }
  }

  public int getPaletteIndex() {
    return myPaletteIndex;
  }


  // Modify the location of the ball according to velocity
  public void move() {
    myX += myDx;
    myY += myDy;
  }
}
