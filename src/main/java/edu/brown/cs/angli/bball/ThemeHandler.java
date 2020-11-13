/**
 * This class takes care of the creation and switching of themese
 * 
 * @author angli
 */

package edu.brown.cs.angli.bball;

import java.awt.Color;
import java.util.ArrayList;

public class ThemeHandler {
  private ArrayList<Theme> myThemes;
  private int myCurrentTheme;
  private ArrayList<String> myThemeNames;

  public ThemeHandler() {
    // default theme
    Theme pastel = new Theme();

    myThemes = new ArrayList<Theme>();
    myThemeNames = new ArrayList<String>();

    myThemes.add(pastel);

    String[] cyberpunkBallColors = {"#ff124f", "#ff00a0", "#fe75fe", "#7a04eb"};
    String cyberpunkBackground = "#120458";
    String cyberpunkTextColor = "#ffffff";
    Theme cyberpunk =
        new Theme(cyberpunkBallColors, cyberpunkBackground, cyberpunkTextColor, "cyberpunk");
    myThemes.add(cyberpunk);

    String[] blossomBallColors = {"#e59b95", "#d8697b", "#f0bbb8", "#d87cbc"};
    String blossomBackground = "#90ace5";
    String blossomTextColor = "#000000";
    Theme blossom = new Theme(blossomBallColors, blossomBackground, blossomTextColor, "blossom");
    myThemes.add(blossom);


    myCurrentTheme = 0;

    for (Theme theme : myThemes) {
      myThemeNames.add(theme.getName());
    }
  }

  public Color[] getBallColors() {
    return myThemes.get(myCurrentTheme).getBallColors();
  }

  public Color getBackgroundColor() {
    return myThemes.get(myCurrentTheme).getBackgroundColor();
  }

  public Color getTextColor() {
    return myThemes.get(myCurrentTheme).getTextColor();
  }

  public ArrayList<String> getThemeNames() {
    return myThemeNames;
  }

  public void setTheme(int i) {
    if (i < myThemes.size()) {
      myCurrentTheme = i;
    }
  }

  public int getCurrentTheme() {
    return myCurrentTheme;
  }


}
