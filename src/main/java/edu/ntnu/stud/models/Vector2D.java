package edu.ntnu.stud.models;

/**
 * The Vector2D class represents a two-dimensional vector.
 * <p>
 * This class provides basic operations for manipulating vectors.
 * </p>
 *
 * @author scott du plessis
 */
public class Vector2D {
  private final double x0;
  private final double x1;

  /**
   * Constructs a new Vector2D object with the specified coordinates.
   *
   * @param x0 The x-coordinate of the vector.
   * @param x1 The y-coordinate of the vector.
   */
  public Vector2D(double x0, double x1) {
    this.x0 = x0;
    this.x1 = x1;
  }

  /**
   * Gets the x-coordinate of the vector.
   *
   * @return the x-coordinate of the vector.
   */
  public double getX0() {
    return x0;
  }

  /**
   * Gets the y-coordinate of the vector.
   *
   * @return The y-coordinate of the vector.
   */
  public double getX1() {
    return x1;
  }

  /**
   * Adds another vector to this vector and returns the result as a new vector.
   *
   * @param other The vector to be added.
   * @return The result of adding the other vector to this vector.
   */
  public Vector2D add(Vector2D other) {
    double newX0 = this.getX0() + other.getX0();
    double newX1 = this.getX1() + other.getX1();

    return new Vector2D(newX0, newX1);
  }

  /**
   * Subtracts another vector from this vector and returns the result as a new vector.
   *
   * @param other The vector to be subtracted.
   * @return The result of subtracting the other vector from this vector.
   */
  public Vector2D subtract(Vector2D other) {
    double newX0 = this.getX0() - other.getX0();
    double newX1 = this.getX1() - other.getX1();

    return new Vector2D(newX0, newX1);
  }

  @Override
  public boolean equals(Object o){
    if (this == o){
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vector2D vector = (Vector2D) o;

    return
  }

}
