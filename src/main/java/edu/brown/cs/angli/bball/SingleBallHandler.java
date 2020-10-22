package edu.brown.cs.angli.bball;

import java.util.ArrayList;
import java.util.concurrent.Callable;

class SingleBallHandler implements Callable<Ball> {
  private Ball ball;
  private int index;
  private int top;
  private int bot;
  private int left;
  private int right;
  private ArrayList<Ball> states;

  public SingleBallHandler(int top, int bot, int left, int right) {
    this.top = top;
    this.bot = bot;
    this.left = left;
    this.right = right;
  }

  public void setStates(int index, ArrayList<Ball> balls) throws CloneNotSupportedException {
    Ball originalBall = balls.get(index);
    try {
      this.ball = (Ball) originalBall.clone();
    } catch (CloneNotSupportedException e) {
      throw e;
    }

    this.index = index;
    this.states = balls;
  }

  public Ball getBall() {
    return this.ball;
  }

  public Ball call() {

    try {
      CollisionHandler.CollisionWithWall(this.ball, this.top, this.bot, this.left, this.right);
      for (int i = 0; i < this.states.size(); i++) {
        if (i != index) {
          CollisionHandler.CollisionWithBall(this.ball, states.get(i), false);
        }
      }
    } catch (Exception e) {
      throw e;
    }

    return this.ball;
  }
}
