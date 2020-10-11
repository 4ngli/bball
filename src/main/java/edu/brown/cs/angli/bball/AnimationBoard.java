/**
 * This is the main animation class which hosts all the animations and handles all interactions
 */

package edu.brown.cs.angli.bball;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class AnimationBoard extends JPanel implements ActionListener, MouseListener {
  private Timer timer;
  private ArrayList<Ball> balls;


  private final int B_WIDTH = 600; // board width
  private final int B_HEIGHT = 600; // board height
  private final int DELAY = 20; // timer delay
  private final int INITIAL_SPEED = 7; // initial speed of balls
  private final int RADIUS = 25; // radius of balls
  private final int NEW_BALL_X = 250; // x coordinate for added ball
  private final int NEW_BALL_Y = RADIUS; // y coordinate for added ball
  private final int MAX_NUM_BALLS = 20; // maximum number of balls
  private final int LONG_PRESS_DELAY = 2000; // long press delay

  private int longPressCounter = 0;
  private int longPressMax = LONG_PRESS_DELAY / DELAY;

  public AnimationBoard() {
    initBoard();
  }

  private void initBoard() {
    timer = new Timer(DELAY, this);

    setBackground(Color.decode("#E6F9FF"));
    setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
    setFocusable(true);
    this.setLayout(null);

    initHelp();
    initBalls();

    timer.start();

    addMouseListener(this);
  }

  // Generate the interaction helps
  private void initHelp() {
    String helpInfo = "<html>Click balls to change color <br>"
        + "Click on blank space to add ball <br>" + "Press for 3 seconds to reset" + "</html>";
    JLabel helpText = new JLabel(helpInfo);
    this.add(helpText);
    Dimension helpSize = helpText.getPreferredSize();
    helpText.setBounds(B_WIDTH - 20 - helpSize.width, 5, helpSize.width, helpSize.height);
  }

  private void initBalls() {
    this.balls = new ArrayList<Ball>();
    Ball first = new Ball(100, 100, 2 * INITIAL_SPEED, 0, RADIUS);
    Ball second = new Ball(NEW_BALL_X, NEW_BALL_Y, 0, INITIAL_SPEED, RADIUS);
    balls.add(first);
    balls.add(second);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    drawBoard(g);
    Toolkit.getDefaultToolkit().sync();
  }

  private void drawCenteredCircle(Graphics2D g, int x, int y, int r) {
    g.fillOval(x - r, y - r, 2 * r, 2 * r);
  }

  // Draw the balls
  private void drawBoard(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    for (Ball ball : balls) {
      g2d.setColor(ball.getPaletteColor());
      drawCenteredCircle(g2d, (int) ball.getX(), (int) ball.getY(), (int) ball.getRadius());
    }

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    step();

    repaint();
  }

  // At each time step, modify the velocity of balls using the collision handler
  // Then move the balls and increment long press counter
  private void step() {
    CollisionHandler.HandleCollision(balls, this.getY(), this.getY() + B_HEIGHT, this.getX(),
        this.getX() + B_WIDTH);
    for (Ball ball : balls) {
      ball.move();
    }

    longPressCounter += 1;
  }


  // Use press handler to lower latency
  @Override
  public void mouseClicked(MouseEvent e) {
    // TODO Auto-generated method stub
  }

  private void onBallClickHandler(Ball nearestBall) {
    nearestBall.changeRandomPaletteColor();
  }

  private void blankSpaceClickHandler() {
    if (balls.size() < MAX_NUM_BALLS) {
      Ball newBall = new Ball(NEW_BALL_X, NEW_BALL_Y, 0, INITIAL_SPEED, RADIUS);
      balls.add(newBall);
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseExited(MouseEvent e) {
    // TODO Auto-generated method stub

  }

  // Change color/add ball and start long press counter
  @Override
  public void mousePressed(MouseEvent e) {
    // TODO Auto-generated method stub
    longPressCounter = 0;

    double mouseX = e.getX();
    double mouseY = e.getY();
    Ball nearestBall = Ball.getNearestBall(balls, mouseX, mouseY);
    if (nearestBall.distance(mouseX, mouseY) < nearestBall.getRadius()) {
      onBallClickHandler(nearestBall);
    } else {
      blankSpaceClickHandler();
    }

    repaint();
  }

  // If has pressed for more than 3 seconds, reset the board
  @Override
  public void mouseReleased(MouseEvent e) {
    // TODO Auto-generated method stub
    if (longPressCounter >= longPressMax) {
      initBalls();
      repaint();
    }
  }
}
