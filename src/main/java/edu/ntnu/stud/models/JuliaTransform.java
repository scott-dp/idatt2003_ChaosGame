package edu.ntnu.stud.models;

public class JuliaTransform implements Transform2D{
  private final Complex constantPoint;
  private final int sign;

  public JuliaTransform(Complex point, int sign) {
    this.constantPoint = point;
    this.sign = sign;
  }

  @Override
  public Vector2D transform(Vector2D point) {
    Complex newComplexPoint = (Complex) point.subtract(constantPoint);

    newComplexPoint = newComplexPoint.sqrt();

    return new Complex(sign * newComplexPoint.getX0(), sign * newComplexPoint.getX1());
  }
}
