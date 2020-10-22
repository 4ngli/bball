package edu.brown.cs.angli.bball;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentCollisionHandler {
  private ExecutorService pool;
  private ArrayList<Ball> previousStates;
  private ArrayList<Ball> newStates;
  private ArrayList<SingleBallHandler> handlers;
  private ArrayList<Future<Ball>> futures;

  private int top;
  private int bot;
  private int left;
  private int right;

  public ConcurrentCollisionHandler(int top, int bot, int left, int right, int numBalls) {
    this.pool = Executors.newCachedThreadPool();
    this.top = top;
    this.bot = bot;
    this.left = left;
    this.right = right;
    this.handlers = new ArrayList<SingleBallHandler>();
    this.previousStates = new ArrayList<Ball>();
    this.newStates = new ArrayList<Ball>();

    for (int i = 0; i < numBalls; i++) {
      this.addHandler();
    }
  }

  public void addHandler() {
    SingleBallHandler newHandler = new SingleBallHandler(this.top, this.bot, this.left, this.right);
    this.handlers.add(newHandler);
  }

  public void handleCollision(ArrayList<Ball> balls) throws CloneNotSupportedException {
    this.futures = new ArrayList<Future<Ball>>();
    this.previousStates = balls;

    for (int i = 0; i < balls.size(); i++) {
      this.handlers.get(i).setStates(i, this.previousStates);
    }

    for (int i = 0; i < balls.size(); i++) {
      Future<Ball> future = this.pool.submit(this.handlers.get(i));
      futures.add(future);
    }

    this.newStates = new ArrayList<Ball>();

    boolean allDone = true;
    for (Future<Ball> future : futures) {
      allDone &= future.isDone();
    }

    while (!allDone) {
      allDone = true;
      for (Future<Ball> future : futures) {
        allDone &= future.isDone();
      }
    }

    for (Future<Ball> future : futures) {
      try {
        Ball ball = future.get();
        this.newStates.add(ball);
      } catch (Exception e) {
      }
    }
  }

  public ArrayList<Ball> getNewStates() {
    return this.newStates;
  }

  public void shutdownPool() {
    this.pool.shutdown();
  }
}

