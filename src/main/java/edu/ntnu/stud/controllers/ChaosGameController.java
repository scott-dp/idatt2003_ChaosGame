package edu.ntnu.stud.controllers;

import edu.ntnu.stud.models.chaosgamehandling.ChaosGame;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGameDescription;
import edu.ntnu.stud.views.ChaosGameView;

/**
 * Represents the controller that is used to decouple {@link ChaosGame} and {@link ChaosGameView}
 */
public class ChaosGameController {
  ChaosGame chaosGame;
  ChaosGameView chaosGameView;

  /**
   * Constructs an instance of ChaosGameController with a set ChaosGame
   * and a set ChaosGameView.
   *
   * @param game the ChaosGame object being shown
   * @param view the ChaosGameView that shows a ChaosGame fractal in the GUI
   */
  public ChaosGameController(ChaosGame game, ChaosGameView view) {
    this.chaosGame = game;
    this.chaosGameView = view;
  }

  /**
   * Used to set a new ChaosGame, which basically is another/distinct fractal.
   *
   * @param description the description being set
   */
  public void setChaosGame(ChaosGameDescription description) {
    this.chaosGame.setNewChaosGame(description);
  }

  /**
   * Calls the runSteps() methods of the ChaosGame instance saved in this object.
   * Used to make the fractal visible.
   *
   * @param steps the amount of steps being run to create the fractal
   */
  public void runSteps(int steps) {
    this.chaosGame.runSteps(steps);
  }


  public ChaosGame getChaosGame() {
    return chaosGame;
  }

  public ChaosGameView getChaosGameView() {
    return chaosGameView;
  }
}
