/**
 * Concurrent handling of collision
 * 
 * @author angli
 */

package edu.brown.cs.angli.bball;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentCollisionHandler {
  private ExecutorService myPool;
  private ArrayList<Ball> myPreviousStates;
  private ArrayList<Ball> myNewStates;
  private ArrayList<SingleBallHandler> myHandlers;
  private ArrayList<Future<Ball>> myFutures;

  private int myTop;
  private int myBot;
  private int myLeft;
  private int myRight;

  public ConcurrentCollisionHandler(int top, int bot, int left, int right, int numBalls) {
    myPool = Executors.newCachedThreadPool();
    myTop = top;
    myBot = bot;
    myLeft = left;
    myRight = right;
    myHandlers = new ArrayList<SingleBallHandler>();
    myPreviousStates = new ArrayList<Ball>();
    myNewStates = new ArrayList<Ball>();

    for (int i = 0; i < numBalls; i++) {
      this.addHandler();
    }
  }

  public void addHandler() {
    SingleBallHandler newHandler =
        new SingleBallHandler(this.myTop, this.myBot, this.myLeft, this.myRight);
    this.myHandlers.add(newHandler);
  }

  public void handleCollision(ArrayList<Ball> balls) throws CloneNotSupportedException {
    this.myFutures = new ArrayList<Future<Ball>>();
    this.myPreviousStates = balls;

    for (int i = 0; i < balls.size(); i++) {
      this.myHandlers.get(i).setStates(i, this.myPreviousStates);
    }

    for (int i = 0; i < balls.size(); i++) {
      Future<Ball> future = this.myPool.submit(this.myHandlers.get(i));
      myFutures.add(future);
    }

    myNewStates = new ArrayList<Ball>();

    for (Future<Ball> future : myFutures) {
      try {
        Ball ball = future.get();
        this.myNewStates.add(ball);
      } catch (Exception e) {
      }
    }
  }

  public ArrayList<Ball> getNewStates() {
    return this.myNewStates;
  }

  public void shutdownPool() {
    this.myPool.shutdown();
  }
}

