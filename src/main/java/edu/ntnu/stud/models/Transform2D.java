package edu.ntnu.stud.models;

import edu.ntnu.stud.models.Vector2D;

/**
 * The Transform2D interface represents a 2D transformation.
 * <p>
 *   Implementing classes provide methods to transform 2D vectors.
 * </p>
 *
 * @author Scott du Plessis, Stanislovas Mockus
 */
public interface Transform2D {
  /**
   * Transforms the specified 2D vector.
   *
   * @param point The 2D vector to be transformed.
   * @return The transformed vector.
   */
  Vector2D transform(Vector2D point);

  @Override
  String toString();
}