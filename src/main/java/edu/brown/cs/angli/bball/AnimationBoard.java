/**
 * This is the main animation class which hosts all the animations and handles all interactions
 * 
 * @author angli
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
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;



public class AnimationBoard extends JPanel implements ActionListener, MouseListener {

  private final int MENU_HEIGHT = 20;
  private final int B_WIDTH = 600; // board width
  private final int B_HEIGHT = 600 + MENU_HEIGHT; // board height
  private final int DELAY = 19; // timer delay
  private final int INITIAL_SPEED = 6; // initial speed of balls
  private final int RADIUS = 25; // radius of balls
  private final int NEW_BALL_X = 250; // x coordinate for added ball
  private final int NEW_BALL_Y = RADIUS; // y coordinate for added ball
  private final int ALTERNATIVE_BALL_X = 100; // a different x coordinate for the second ball
  private final int ALTERNATIVE_BALL_Y = 100; // a different x coordinate for the second ball
  private final int MAX_NUM_BALLS = 20; // maximum number of balls
  private final int LONG_PRESS_DELAY = 2000; // long press delay
  private final int LONG_PRESS_MAX = LONG_PRESS_DELAY / DELAY;
  private final int NUM_COLORS = 4;

  private Timer myTimer;
  private ArrayList<Ball> myBalls;
  private ConcurrentCollisionHandler myHandler;
  private ThemeHandler myThemes;
  private JLabel myHelpText;
  private int myLongPressCounter = 0;
  private static Color[] myBallColors;
  private static Color myBackgroundColor;
  private static Color myTextColor;
  private Random myRand;

  public AnimationBoard() {
    initBoard();
  }

  private void initBoard() {
    myTimer = new Timer(DELAY, this);
    myThemes = new ThemeHandler();
    myBallColors = myThemes.getBallColors();
    myBackgroundColor = myThemes.getBackgroundColor();
    myTextColor = myThemes.getTextColor();
    myRand = new Random();

    setBackground(myBackgroundColor);
    setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
    setFocusable(true);
    this.setLayout(null);

    myHelpText = getHelpText();
    this.add(myHelpText);

    initBalls();

    myTimer.start();

    addMouseListener(this);
  }

  // Generate the interaction helps
  private JLabel getHelpText() {
    String helpInfo = "<html>Click balls to change color <br>"
        + "Click on blank space to add ball <br>" + "Press for 3 seconds to reset" + "</html>";
    JLabel helpText = new JLabel(helpInfo);
    helpText.setForeground(myTextColor);
    Dimension helpSize = helpText.getPreferredSize();
    helpText.setBounds(B_WIDTH - 20 - helpSize.width, 5, helpSize.width, helpSize.height);

    return helpText;
  }

  private void initBalls() {
    this.myBalls = new ArrayList<Ball>();
    Ball first = new Ball(ALTERNATIVE_BALL_X, ALTERNATIVE_BALL_Y, 2 * INITIAL_SPEED, 0, RADIUS);
    Ball second = new Ball(NEW_BALL_X, NEW_BALL_Y, 0, INITIAL_SPEED, RADIUS);
    first.changePaletteColor(getRandomColor(-1));
    second.changePaletteColor(getRandomColor(-1));
    myBalls.add(first);
    myBalls.add(second);

    this.myHandler =
        new ConcurrentCollisionHandler(this.getY(), this.getY() + B_HEIGHT - MENU_HEIGHT,
            this.getX(), this.getX() + B_WIDTH, this.myBalls.size());
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
    for (Ball ball : myBalls) {
      int currentPaletteIndex = ball.getPaletteIndex();
      g2d.setColor(myBallColors[currentPaletteIndex]);
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
    try {
      this.myHandler.handleCollision(this.myBalls);
    } catch (Exception e) {

    }
    this.add(myHelpText);

    this.myBalls = this.myHandler.getNewStates();
    for (Ball ball : myBalls) {
      ball.move();
    }

    myLongPressCounter += 1;
  }


  // Use press handler to lower latency
  @Override
  public void mouseClicked(MouseEvent e) {
    // TODO Auto-generated method stub
  }

  private void onBallClickHandler(Ball nearestBall) {
    int newColor = this.getRandomColor(nearestBall.getPaletteIndex());
    nearestBall.changePaletteColor(newColor);
  }

  private void blankSpaceClickHandler() {
    if (myBalls.size() < MAX_NUM_BALLS) {
      Ball newBall = new Ball(NEW_BALL_X, NEW_BALL_Y, 0, INITIAL_SPEED, RADIUS);
      newBall.changePaletteColor(getRandomColor(-1));
      myBalls.add(newBall);
      this.myHandler.addHandler();
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  // Change color/add ball and start long press counter
  @Override
  public void mousePressed(MouseEvent e) {
    myLongPressCounter = 0;

    double mouseX = e.getX();
    double mouseY = e.getY();
    Ball nearestBall = Ball.getNearestBall(myBalls, mouseX, mouseY);
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
    if (myLongPressCounter >= LONG_PRESS_MAX) {
      initBalls();
      repaint();
      setBackground(myBackgroundColor);
    }
  }

  public void shutdownHandler() {
    this.myHandler.shutdownPool();
  }

  public ArrayList<String> getThemeNames() {
    return myThemes.getThemeNames();
  }

  public int getCurrentTheme() {
    return myThemes.getCurrentTheme();
  }

  public void changeTheme(int i) {
    myThemes.setTheme(i);
    myBallColors = myThemes.getBallColors();
    myBackgroundColor = myThemes.getBackgroundColor();
    myTextColor = myThemes.getTextColor();

    setBackground(myBackgroundColor);

    this.remove(myHelpText);
    myHelpText = getHelpText();
    this.add(myHelpText);
    repaint();
  }

  private int getRandomColor(int oldColor) {
    int newColor = myRand.nextInt(NUM_COLORS);
    while (newColor == oldColor) {
      newColor = myRand.nextInt(NUM_COLORS);
    }
    return newColor;
  }
}
