package edu.ntnu.stud.models;

import java.util.Vector;

/**
 * The Complex class represents complex numbers, which consist of a real part and an imaginary part.
 * <p>
 * This class extends the Vector2D class to inherit
 * functionality related to two-dimensional vectors.
 * </p>
 *
 * @author Scott du Plessis
 */
public class Complex extends Vector2D {
  /**
   *Constructs a new Complex number with the specified real and imaginary parts.
   *
   *@param realPart The real part of the complex number.
   *@param imaginaryPart The imaginary part of the complex number.
   */
  public Complex(double realPart, double imaginaryPart) {
    super(realPart, imaginaryPart);
  }

  /**
   *Calculates the complex square root of a complex number.
   *
   *@return The complex square root of the complex number.
   */
  public Complex sqrt() {
    double realPart = Math.sqrt(0.5 * Math.sqrt(Math.pow(this.getX0(), 2)
        + Math.pow(this.getX1(), 2) + this.getX0()));
    double imaginaryPart = Math.signum(this.getX1()) * Math.sqrt(0.5
        * Math.sqrt(Math.pow(this.getX0(), 2) + Math.pow(this.getX1(), 2) - this.getX0()));

    return new Complex(realPart, imaginaryPart);
  }

}
