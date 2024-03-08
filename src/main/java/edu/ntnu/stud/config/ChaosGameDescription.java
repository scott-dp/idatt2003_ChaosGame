package edu.ntnu.stud.config;

import edu.ntnu.stud.models.AffineTransform2D;
import edu.ntnu.stud.models.JuliaTransform;
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

  public String toString() {
    StringBuilder info = new StringBuilder();

    if (transforms.get(0) instanceof AffineTransform2D) {
      info.append("Affine2D\n");
    } else if (transforms.get(0) instanceof JuliaTransform) {
      info.append("Julia\n");
    } else {
      throw new IllegalArgumentException("Unknown transform type");
    }
    info.append(minCoords.toString()).append("\n");
    info.append(maxCoords.toString()).append("\n");
    for (Transform2D transform : transforms) {
      info.append(transform.toString()).append("\n");
    }
    return info.toString();
  }
}
