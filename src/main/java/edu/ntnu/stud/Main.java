package edu.ntnu.stud;

import edu.ntnu.stud.models.*;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGameDescription;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGame;
import edu.ntnu.stud.ui.UserInterface;

import java.util.ArrayList;
import java.util.List;

public class Main {
  static UserInterface ui = new UserInterface();
  public static void main(String[] args) {
    ChaosGame game = new ChaosGame(ChaosGameDescriptionFactory.createBarnsleyDescription(), 200, 100);
    game.runSteps(1000000);
    game.getCanvas().showCanvas();
    ui.init();
    ui.start();
  }
}
