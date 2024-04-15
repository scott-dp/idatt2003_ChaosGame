package edu.ntnu.stud.controllers;

import edu.ntnu.stud.models.chaosgamehandling.ChaosGame;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGameDescription;
import edu.ntnu.stud.views.ChaosGameView;

public class ChaosGameController {
  ChaosGame chaosGame;
  ChaosGameView chaosGameView;

  public ChaosGameController(ChaosGame game, ChaosGameView view) {
    this.chaosGame = game;
    this.chaosGameView = view;
  }

  public void setChaosGameDescription(ChaosGameDescription description) {
    this.chaosGame.setDescription(description);
  }

  public void runSteps(int steps) {
    this.chaosGame.runSteps(steps);
  }
}
