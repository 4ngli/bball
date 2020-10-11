/**
 * This class aggregates the static functions that handles the collision
 * 
 */

package edu.brown.cs.angli.bball;

import java.util.List;

public class CollisionHandler {

  // Aggregates the collision with other balls as well as with walls
  public static void HandleCollision(List<Ball> balls, int top, int bot, int left, int right) {
    for (Ball ball : balls) {
      CollisionWithWall(ball, top, bot, left, right);
    }

    for (int i = 0; i < balls.size(); i++) {
      for (int j = i + 1; j < balls.size(); j++) {
        CollisionWithBall(balls.get(i), balls.get(j));
      }
    }
  }

  // Handles the collision between two balls
  private static void CollisionWithBall(Ball b1, Ball b2) {
    double d = b1.distance(b2);
    double r1 = b1.getRadius();
    double r2 = b2.getRadius();

    if (d < r1 + r2) {
      double deltaDX = (b2.getX() - b1.getX()) / d;
      double deltaDY = (b2.getY() - b1.getY()) / d;
      double b1CollisionComp = b1.getDx() * deltaDX + b1.getDy() * deltaDY;
      double b2CollisionComp = b2.getDx() * deltaDX + b2.getDy() * deltaDY;

      double b1NewDx = b1.getDx() - b1CollisionComp * deltaDX + b2CollisionComp * deltaDX;
      double b1NewDy = b1.getDy() - b1CollisionComp * deltaDY + b2CollisionComp * deltaDY;
      double b2NewDx = b2.getDx() - b2CollisionComp * deltaDX + b1CollisionComp * deltaDX;
      double b2NewDy = b2.getDy() - b2CollisionComp * deltaDY + b1CollisionComp * deltaDY;

      b1.setDx(b1NewDx);
      b1.setDy(b1NewDy);
      b2.setDx(b2NewDx);
      b2.setDy(b2NewDy);

      double d1 = (r1 + r2 - d) * r1 / (r1 + r2);
      double d2 = (r1 + r2 - d) * r2 / (r1 + r2);
      double b1NewX = b1.getX() - deltaDX * d1 / d;
      double b1NewY = b1.getY() - deltaDY * d1 / d;
      double b2NewX = b2.getX() + deltaDX * d2 / d;
      double b2NewY = b2.getY() + deltaDY * d2 / d;

      b1.setX(b1NewX);
      b1.setY(b1NewY);
      b2.setX(b2NewX);
      b2.setY(b2NewY);
    }

  }

  // Handles collision between a single ball and four walls
  private static void CollisionWithWall(Ball b, int top, int bot, int left, int right) {
    double x = b.getX();
    double y = b.getY();
    double dx = b.getDx();
    double dy = b.getDy();
    double r = b.getRadius();

    if (x - r < left & dx < 0) {
      b.setDx(-dx);
    } else if (r + x > right & dx > 0) {
      b.setDx(-dx);
    }

    if (y - r < top & dy < 0) {
      b.setDy(-dy);
    } else if (r + y > bot & dy > 0) {
      b.setDy(-dy);
    }
  }
}
