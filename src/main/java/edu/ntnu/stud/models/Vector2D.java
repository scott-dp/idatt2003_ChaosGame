package edu.ntnu.stud.models;

import edu.ntnu.stud.models.utils.ChaosGameUtils;

/**
 * The Vector2D class represents a two-dimensional vector.
 * <p>
 * This class provides basic operations for manipulating vectors.
 * </p>
 *
 * @author Scott du Plessis, Stanislovas Mockus
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
    this.x0 = ChaosGameUtils.roundDoubleToSetDecimals(x0, 3);
    this.x1 = ChaosGameUtils.roundDoubleToSetDecimals(x1, 3);
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

  /**
   * Creates a String that represents a Vector2D object.
   *
   * @return the String that represents the object
   */
  @Override
  public String toString() {
    return x0 + ", " + x1;
  }

  /**
   * Checks if the parameter {@code o} is equal to this instance of {@code Vector2D}.
   *
   * @param o object to check for equality to {@code this}
   * @return true if {@code this} and {@code o} are equal, false if they are not
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vector2D vector = (Vector2D) o;
    return ChaosGameUtils.areDoublesEqual(getX0(), vector.getX0())
        && ChaosGameUtils.areDoublesEqual(getX1(), vector.getX1());
  }

}
