package edu.ntnu.stud.models;

public class AffineTransform2D implements Transform2D{
  private final Matrix2x2 matrix;
  private final Vector2D vector;

  public AffineTransform2D(Matrix2x2 matrix, Vector2D vector){
    this.matrix = matrix;
    this.vector = vector;
  }

  @Override
  public Vector2D transform(Vector2D point) {
    Vector2D result = matrix.multiply(point);
    result.add(vector);

    return result;
  }
}
