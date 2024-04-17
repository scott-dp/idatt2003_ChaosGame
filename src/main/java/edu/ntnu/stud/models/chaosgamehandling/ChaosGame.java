package edu.ntnu.stud.models.chaosgamehandling;

import edu.ntnu.stud.models.Vector2D;
import edu.ntnu.stud.models.observer.ChaosGameObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a chaos game with methods to run the game and visualize the result.
 *
 *<p>This class utilizes a ChaosCanvas object as its drawing surface, where the
 * fractal pattern is rendered. The size of the canvas and the coordinate range
 * it covers are configurable.
 *
 * @author Scott du Plessis, Stanislovas Mockus
 * @version 1.0
 * @see ChaosCanvas
 */
public class ChaosGame {
  private ChaosCanvas canvas;
  private ChaosGameDescription description;
  private Vector2D currentPoint;
  private final Random random;
  private final List<ChaosGameObserver> observerList = new ArrayList<>();

  /**
   * Constructs a ChaosGame instance with the specified game description and canvas dimensions.
   *
   * @param description The description of the chaos game, including the set of transformations.
   * @param width       The width of the canvas.
   * @param height      The height of the canvas.
   */
  public ChaosGame(ChaosGameDescription description, int width, int height) {
    this.description = description;
    this.currentPoint = new Vector2D(0, 0);
    this.canvas =
        new ChaosCanvas(width, height, description.getMinCoords(), description.getMaxCoords());
    this.random = new Random();
  }

  /**
   * Updates all observers of this ChaosGame that there has been a change in the ChaosGame.
   */
  public void updateObservers() {
    observerList.forEach(ChaosGameObserver::update);
  }

  /**
   * Method to add an observer to the observerList that will be notified
   * when there is a change in this ChaosGame.
   *
   * @param observer the observer being added to observerList
   */
  public void addObserver(ChaosGameObserver observer) {
    if (!observerList.contains(observer)) {
      observerList.add(observer);
    }
  }

  /**
   * Method to remove an observer from the observerList.
   *
   * @param observer the observer to be removed.
   */
  public void removeObserver(ChaosGameObserver observer) {
    observerList.remove(observer);
  }

  /**
   * Modifier method to set a new ChaosGameDescription, which means that a new ChaosCanvas
   * also needs to be set.
   *
   * @param description the new description being represented
   */
  public void setNewChaosGame(ChaosGameDescription description) {
    this.description = description;
    this.canvas = new ChaosCanvas(canvas.getWidth(), canvas.getHeight(),
        description.getMinCoords(), description.getMaxCoords());
    updateObservers();
  }

  /**
   * Returns the canvas used by this chaos game.
   *
   * @return The ChaosCanvas where the fractal pattern is rendered.
   */
  public ChaosCanvas getChaosCanvas() {
    return canvas;
  }

  /**
   * Runs the chaos game for a specified number of steps. At each step, a random
   * transformation is selected from the set provided by the ChaosGameDescription,
   * applied to the current point, and the result is plotted on the canvas.
   *
   * @param steps The number of steps to run the game for.
   */
  public void runSteps(int steps) {
    canvas.clear();
    for (int i = 0; i < steps; i++) {
      int randomIndex = random.nextInt(description.getTransforms().size());
      currentPoint = description.getTransforms().get(randomIndex).transform(currentPoint);

      canvas.putPixel(currentPoint);
    }
    updateObservers();
  }
}
