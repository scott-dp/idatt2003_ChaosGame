package edu.ntnu.stud.config;

import edu.ntnu.stud.models.Transform2D;
import edu.ntnu.stud.models.Vector2D;
import java.util.List;

/**
 * Represents a specific chaos game by defining the different transforms and min and max points.
 *
 * @author Scott du Plessis, Stanislovas Mockus
 */
public class ChaosGameDescription {
  private final Vector2D minCoords;
  private final Vector2D maxCoords;
  private final List<Transform2D> transforms;

  /**
   * Constructs a {@link ChaosGameDescription} instance.
   *
   * @param minCoords the minimum coordinates of the fractal (Chaos game?)
   * @param maxCoords the maximum coordinates of the fractal (Chaos game?)
   * @param transforms the list of transform that are used in the chaos game
   */
  public ChaosGameDescription(Vector2D minCoords,
                              Vector2D maxCoords, List<Transform2D> transforms) {
    this.minCoords = minCoords;
    this.maxCoords = maxCoords;
    this.transforms = transforms;
  }

  public List<Transform2D> getTransforms() {
    return transforms;
  }

  public Vector2D getMaxCoords() {
    return maxCoords;
  }

  public Vector2D getMinCoords() {
    return minCoords;
  }
}
