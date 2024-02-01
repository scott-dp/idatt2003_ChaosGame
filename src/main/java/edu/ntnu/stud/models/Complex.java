package edu.ntnu.stud.models;

import java.util.Vector;

public class Complex extends Vector2D {
  public Complex(double realPart, double imaginaryPart) {
    super(realPart, imaginaryPart);
  }

  public Complex sqrt(){
    double realPart = Math.sqrt(0.5 * Math.sqrt(Math.pow(this.getX0(),2) + Math.pow(this.getX1(),2) + this.getX0()));
    double imaginaryPart = Math.signum(this.getX1()) * Math.sqrt(0.5 * Math.sqrt(Math.pow(this.getX0(),2) + Math.pow(this.getX1(),2) - this.getX0()));

    return new Complex(realPart, imaginaryPart);
  }

}
