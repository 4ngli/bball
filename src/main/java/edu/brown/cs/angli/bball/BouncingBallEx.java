package edu.brown.cs.angli.bball;

import javax.swing.JFrame;

public class BouncingBallEx extends JFrame {
  private final String VERSION = "0.0.1";


  public BouncingBallEx() {
    initWindow();
  }

  private void initWindow() {
    add(new AnimationBoard());

    setTitle("Bouncing Ball v" + VERSION);

    setLocationRelativeTo(null);
    setResizable(false);
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }
}
