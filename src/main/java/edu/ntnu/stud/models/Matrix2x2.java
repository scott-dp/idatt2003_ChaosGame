package edu.ntnu.stud.models;


import edu.ntnu.stud.models.utils.ChaosGameUtils;

/**
 * Represents a 2x2 matrix and provides a method to multiply it by a 2D vector.
 * This class is immutable, meaning the matrix cannot be changed after it is created.
 *
 *
 * @author Stanislovas Mockus, Scott du Plessis
 * @version 1.0
 */
public class Matrix2x2 {
  private final double a00;
  private final double a01;
  private final double a10;
  private final double a11;

  /**
   * Constructs a new 2x2 matrix with the specified elements.
   *
   * @param a00 the element at (0, 0)
   * @param a01 the element at (0, 1)
   * @param a10 the element at (1, 0)
   * @param a11 the element at (1, 1)
   */
  public Matrix2x2(double a00, double a01, double a10, double a11) {
    this.a00 = ChaosGameUtils.roundDoubleToSetDecimals(a00, 3);
    this.a01 = ChaosGameUtils.roundDoubleToSetDecimals(a01, 3);
    this.a10 = ChaosGameUtils.roundDoubleToSetDecimals(a10, 3);
    this.a11 = ChaosGameUtils.roundDoubleToSetDecimals(a11, 3);
  }

  /**
   * Multiplies this matrix by a given {@code Vector2D},
   * and returns the result as a new {@code Vector2D}.
   *
   * @param vector the {@code Vector2D} to be multiplied by this matrix
   * @return a new {@code Vector2D} representing the result of the multiplication
   */
  public Vector2D multiply(Vector2D vector) {
    return new Vector2D(a00 * vector.getX0() + a01 * vector.getX1(), a10
        * vector.getX0() + a11 * vector.getX1());
  }

  //TODO add javadoc here
  @Override
  public String toString() {
    return a00 + ", " + a01 + ", " + a10 + ", " + a11;
  }
}
