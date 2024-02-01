package edu.ntnu.stud.models;

public class Matrix2x2 {
    private double a00, a01, a10, a11;

    public Matrix2x2(double a00, double a01, double a10, double a11) {
        this.a00 = a00;
        this.a01 = a01;
        this.a10 = a10;
        this.a11 = a11;
    }

    public Vector2D multiply(Vector2D vector) {
        return new Vector2D(a00 * v.getX0() + a01 * v.getX1(), a10 * v.getX0() + a11 * v.getX1());
    }
}
