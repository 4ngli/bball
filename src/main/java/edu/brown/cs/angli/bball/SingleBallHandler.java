/**
 * The callable class for a single ball to be used for concurrent collision handling
 * 
 * @author angli
 */

package edu.brown.cs.angli.bball;

import java.util.ArrayList;
import java.util.concurrent.Callable;

class SingleBallHandler implements Callable<Ball> {
  private Ball myBall;
  private int myIndex;
  private int myTop;
  private int myBot;
  private int myLeft;
  private int myRight;
  private ArrayList<Ball> myStates;

  public SingleBallHandler(int top, int bot, int left, int right) {
    myTop = top;
    myBot = bot;
    myLeft = left;
    myRight = right;
  }

  public void setStates(int index, ArrayList<Ball> balls) throws CloneNotSupportedException {
    Ball originalBall = balls.get(index);
    try {
      myBall = (Ball) originalBall.clone();
    } catch (CloneNotSupportedException e) {
      throw e;
    }

    myIndex = index;
    myStates = balls;
  }

  public Ball getBall() {
    return myBall;
  }

  public Ball call() {

    try {
      CollisionHandler.CollisionWithWall(myBall, myTop, myBot, myLeft, myRight);
      for (int i = 0; i < myStates.size(); i++) {
        if (i != myIndex) {
          CollisionHandler.CollisionWithBall(myBall, myStates.get(i), false);
        }
      }
    } catch (Exception e) {
      throw e;
    }

    return myBall;
  }
}
