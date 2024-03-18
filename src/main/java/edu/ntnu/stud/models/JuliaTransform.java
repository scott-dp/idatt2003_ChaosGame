package edu.ntnu.stud.models;

/**
 * Implements a transformation based on Julia sets for 2D points.
 * This class provides a method to transform a {@link Vector2D} point using a Julia set formula.
 *
 * @author Scott du Plessis, Stanislovas Mockus
 * @version 1.0
 * @see Transform2D
 */

public class JuliaTransform implements Transform2D {
  //TODO add javadocs to overridden methods
  private final Complex constantPoint;
  private final int sign;

  /**
   * Constructs a {@code JuliaTransform} with a specified complex point and sign.
   *
   * @param point the complex point to subtract from input points during the transformation
   * @param sign  the sign to apply to the transformed point, typically 1 or -1
   */
  public JuliaTransform(Complex point, int sign) {
    //TODO validate sign field
    this.constantPoint = point;
    this.sign = sign;
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

  @Override
  public String toString() {
    return constantPoint.toString();
  }

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
