package edu.ntnu.stud.fractalgeneration;

import edu.ntnu.stud.config.ChaosGameDescription;
import edu.ntnu.stud.graphics.ChaosCanvas;
import edu.ntnu.stud.models.Vector2D;
import edu.ntnu.stud.utils.ChaosGameUtils;
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
  private final ChaosCanvas canvas;
  private final ChaosGameDescription description;
  private Vector2D currentPoint;
  private final Random random;

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
   * Returns the canvas used by this chaos game.
   *
   * @return The ChaosCanvas where the fractal pattern is rendered.
   */
  public ChaosCanvas getCanvas() {
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
    for (int i = 0; i < steps; i++) {
      int randomIndex = random.nextInt(description.getTransforms().size());
      currentPoint = description.getTransforms().get(randomIndex).transform(currentPoint);

      canvas.putPixel(currentPoint);
    }
  }
}
