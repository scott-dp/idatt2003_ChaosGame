package edu.ntnu.stud.controllers;

import edu.ntnu.stud.models.chaosgamehandling.ChaosGame;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGameDescription;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGameDescriptionFactory;
import edu.ntnu.stud.models.observer.ChaosGameObserver;
import edu.ntnu.stud.views.ChaosGameView;

/**
 * A singleton class that represents the controller that is used to decouple
 * {@link ChaosGame} and {@link ChaosGameView}.
 */
public class ChaosGameController {
  private static ChaosGameController instance;
  private final ChaosGame chaosGame;
  private final ChaosGameView chaosGameView;

  /**
   * Constructs an instance of ChaosGameController with a set ChaosGame
   * and a set ChaosGameView.
   */
  private ChaosGameController() {
    chaosGame = new ChaosGame(ChaosGameDescriptionFactory.createSierpinskiDescription(),
        450, 450);
    chaosGameView = new ChaosGameView(chaosGame);
  }

  public static ChaosGameController getInstance() {
    if (instance == null) {
      instance = new ChaosGameController();
    }
    return instance;
  }

  /**
   * Used to set a new ChaosGame, which basically is another/distinct fractal.
   *
   * @param description the description being set
   */
  public void setChaosGameDescription(ChaosGameDescription description) {
    chaosGame.setNewChaosGame(description);
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

  public void addObserver(ChaosGameObserver observer) {
    chaosGame.addObserver(observer);
  }


  public ChaosGame getChaosGame() {
    return chaosGame;
  }

  public ChaosGameView getChaosGameView() {
    return chaosGameView;
  }
}
