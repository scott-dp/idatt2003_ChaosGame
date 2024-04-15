package edu.ntnu.stud;

import edu.ntnu.stud.models.ChaosGameDescriptionFactory;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGame;
import edu.ntnu.stud.views.AppView;
import edu.ntnu.stud.views.ChaosGameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main{
  static AppView appView;
  public static void main(String[] args) {
    appView = new AppView();
    appView.launchApp(args);
  }

}
