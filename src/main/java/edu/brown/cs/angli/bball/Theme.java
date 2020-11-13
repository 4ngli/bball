/**
 * This class describes a single theme, consisting of ball colors and a background color
 * 
 * @author angli
 */

package edu.brown.cs.angli.bball;

import java.awt.Color;
import java.util.Arrays;

public class Theme {
  private Color[] myBallColors = {Color.decode("#FF9AA2"), Color.decode("#FFB7B2"),
      Color.decode("#FFDAC1"), Color.decode("#E2F0CB")};
  private Color myBackgroundColor = Color.decode("#E6F9FF");
  private Color myTextColor = Color.decode("#000000");
  private String myName = "pastel";

  public Theme() {

  }

  public Theme(String[] ballColors, String backgroundColor, String textColor, String name) {
    myBallColors = Arrays.stream(ballColors).map(x -> Color.decode(x)).toArray(Color[]::new);
    myBackgroundColor = Color.decode(backgroundColor);
    myTextColor = Color.decode(textColor);
    myName = name;
  }

  public Color[] getBallColors() {
    return myBallColors;
  }


  public Color getTextColor() {
    return myTextColor;
  }

  public String getName() {
    return myName;
  }

  public Color getBackgroundColor() {
    return myBackgroundColor;
  }
}
