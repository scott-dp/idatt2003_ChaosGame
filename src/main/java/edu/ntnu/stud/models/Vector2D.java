package edu.ntnu.stud.models;

public class Vector2D {
  private final double x0;
  private final double x1;

  public Vector2D(double x0, double x1){
    this.x0 = x0;
    this.x1 = x1;
  }

  public double getX0() {
    return x0;
  }

  public double getX1() {
    return x1;
  }

  public Vector2D add(Vector2D other){
    double newX0 = this.getX0() + other.getX0();
    double newX1 = this.getX1() + other.getX1();

    return new Vector2D(newX0, newX1);
  }

  public Vector2D subtract(Vector2D other){
    double newX0 = this.getX0() - other.getX0();
    double newX1 = this.getX1() - other.getX1();

    return new Vector2D(newX0, newX1);
  }

}
