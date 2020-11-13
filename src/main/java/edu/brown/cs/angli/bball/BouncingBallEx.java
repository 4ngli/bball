/**
 * This class generates the window and hosts the animation board
 * 
 * @author angli
 */

package edu.brown.cs.angli.bball;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;

public class BouncingBallEx extends JFrame implements ActionListener {
  private final String VERSION = "0.0.3";

  static private JMenuBar myMb;
  static private JMenu myThemeMenu;
  static private ArrayList<JRadioButtonMenuItem> myThemes;
  static private ArrayList<String> myThemeNames;
  static private AnimationBoard myBoard;
  static private int myActivatedTheme = 0;

  public BouncingBallEx() {
    initWindow();
  }

  private void initWindow() {
    myBoard = new AnimationBoard();
    add(myBoard);

    setTitle("Bouncing Ball v" + VERSION);

    setLocationRelativeTo(null);
    setResizable(false);
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    // create the menu
    myMb = new JMenuBar();
    myMb.setPreferredSize(new Dimension(600, 20));
    myThemeMenu = new JMenu("Themes");
    myMb.add(myThemeMenu);

    myThemeNames = myBoard.getThemeNames();

    myThemes = new ArrayList<JRadioButtonMenuItem>();
    for (String name : myThemeNames) {
      JRadioButtonMenuItem newItem = new JRadioButtonMenuItem(name);
      newItem.setActionCommand(name);
      newItem.addActionListener(this);
      myThemes.add(newItem);
    }

    for (JRadioButtonMenuItem theme : myThemes) {
      myThemeMenu.add(theme);
    }

    myActivatedTheme = myBoard.getCurrentTheme();
    myThemes.get(myActivatedTheme).setSelected(true);

    setJMenuBar(myMb);
  }

  private int getThemeIndex(String name) {
    for (int i = 0; i < myThemeNames.size(); i++) {
      if (myThemeNames.get(i) == name) {
        return i;
      }
    }

    return -1;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String name = e.getActionCommand();
    int themeIndex = getThemeIndex(name);
    myThemes.get(myActivatedTheme).setSelected(false);
    myActivatedTheme = themeIndex;
    myThemes.get(myActivatedTheme).setSelected(true);
    myBoard.changeTheme(myActivatedTheme);
  }
}
