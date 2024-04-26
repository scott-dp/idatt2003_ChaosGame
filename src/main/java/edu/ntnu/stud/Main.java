package edu.ntnu.stud;

import edu.ntnu.stud.views.AppView;

public class Main {
  static AppView appView;

  public static void main(String[] args) {
    appView = new AppView();
    appView.launchApp(args);
  }

}
