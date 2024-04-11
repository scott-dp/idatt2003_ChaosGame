package edu.ntnu.stud.models;

import edu.ntnu.stud.models.utils.ChaosGameUtils;

/**
 * Implements a transformation based on Julia sets for 2D points.
 * This class provides a method to transform a {@link Vector2D} point using a Julia set formula.
 *
 * @author Scott du Plessis, Stanislovas Mockus
 * @version 1.0
 * @see Transform2D
 */

public class JuliaTransform implements Transform2D {
  private final Complex constantPoint;
  private final int sign;

  /**
   * Constructs a {@code JuliaTransform} with a specified complex point and sign.
   * Throws an {@link IllegalArgumentException} if the sign value is invalid.
   *
   * @param point the complex point to subtract from input points during the transformation
   * @param sign  the sign to apply to the transformed point, typically 1 or -1
   */
  public JuliaTransform(Complex point, int sign)
      throws IllegalArgumentException {
    this.constantPoint = point;
    this.sign = ChaosGameUtils.validateSignField(sign);
  }

  public Complex getConstantPoint() {
    return constantPoint;
  }

  public int getSign() {
    return sign;
  }

  /**
   * Transforms a given {@link Vector2D} point according to the Julia set transformation formula.
   * <p>
   * The method performs the transformation by first converting the input {@code Vector2D}
   * point into a complex number, subtracting the constant complex point {@code constantPoint},
   * taking the square root of the result, and then
   * applying the {@code sign} multiplier to the real and imaginary parts of the result.
   * </p>
   *
   * @param point the {@link Vector2D} point to be transformed
   * @return a new {@link Complex} object representing the transformed point
   */
  @Override
  public Vector2D transform(Vector2D point) {
    Vector2D vectorSubtracted = point.subtract(constantPoint);
    Complex newComplexPoint = new Complex(vectorSubtracted.getX0(), vectorSubtracted.getX1());

    newComplexPoint = newComplexPoint.sqrt();

    return new Complex(sign * newComplexPoint.getX0(), sign * newComplexPoint.getX1());
  }

  /**
   * Constructs a String that describes this Julia transform.
   *
   * @return the String that represents this object
   */
  @Override
  public String toString() {
    return constantPoint.toString();
  }

  /**
   * Method to check if an object o is equal to this JuliaTransform object. Checks if the
   * objects are of the same class and then checks if their fields are equal.
   *
   * @param o the other object being compared to this
   * @return true if they are equal, false if they are not
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JuliaTransform juliaTransform = (JuliaTransform) o;

    return getConstantPoint().equals(juliaTransform.getConstantPoint())
        && getSign() == juliaTransform.getSign();
  }
}
