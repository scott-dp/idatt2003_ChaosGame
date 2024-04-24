package edu.ntnu.stud.models;

/**
 * The AffineTransform2D class represents an affine transformation in two-dimensional space.
 * <p>
 * This class implements the Transform2D interface.
 * </p>
 *
 * @author Scott du Plessis, Stanislovas Mockus
 * @see Transform2D
 */

public class AffineTransform2D implements Transform2D {
  private final Matrix2x2 matrix;
  private final Vector2D vector;

  /**
   * Constructs a new AffineTransform2D object with
   * the specified transformation matrix and translation vector.
   *
   * @param matrix The 2x2 transformation matrix.
   * @param vector The translation vector.
   */
  public AffineTransform2D(Matrix2x2 matrix, Vector2D vector) {
    this.matrix = matrix;
    this.vector = vector;
  }

  public Matrix2x2 getMatrix() {
    return matrix;
  }

  public Vector2D getVector() {
    return vector;
  }

  /**
   * Transforms the specified 2D vector using this affine transformation.
   *
   * @param point The 2D vector to be transformed.
   * @return The transformed vector.
   */
  @Override
  public Vector2D transform(Vector2D point) {
    Vector2D result = matrix.multiply(point);

    return result.add(vector);
  }

  /**
   * Creates a String that represents this Affine transform.
   *
   * @return the String that represents this transform
   */
  @Override
  public String toString() {
    return matrix.toString() + ", " + vector.toString();
  }
}
