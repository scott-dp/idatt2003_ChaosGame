package edu.ntnu.stud.models.chaosgamehandling;

import edu.ntnu.stud.models.transform.AffineTransform2D;
import edu.ntnu.stud.models.mathematics.Matrix2x2;
import edu.ntnu.stud.models.mathematics.Vector2D;
import edu.ntnu.stud.models.utils.ChaosGameUtils;

import java.util.Arrays;

/**
 * Represents a canvas for chaos game visualization with methods to manipulate pixels and canvas.
 */
public class ChaosCanvas {
  private final int[][] canvas;
  private final int width;
  private final int height;
  private final Vector2D minCoords;
  private final Vector2D maxCoords;
  private final AffineTransform2D transformCoordsToIndices;

  /**
   * Constructs a ChaosCanvas with the specified width, height, and coordinate range
   * and sets the correct transformation.
   *
   * @param width      The width of the canvas.
   * @param height     The height of the canvas.
   * @param minCoords  The minimum coordinates of the fractal in the plane.
   * @param maxCoords  The maximum coordinates of the fractal in the plane.
   */
  public ChaosCanvas(int width, int height, Vector2D minCoords, Vector2D maxCoords)
      throws IllegalArgumentException {
    this.width = ChaosGameUtils.validatePositiveInteger(width);
    this.height = ChaosGameUtils.validatePositiveInteger(height);
    ChaosGameUtils.validateMinAndMaxCoords(minCoords, maxCoords);
    this.minCoords = minCoords;
    this.maxCoords = maxCoords;
    this.canvas = new int[height][width];
    transformCoordsToIndices = setTransformCoordsMatrix();
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int[][] getCanvas() {
    return canvas;
  }

  /**
   * Retrieves the canvas (2D table) value at a specified point on the plane.
   *
   * @param point The coordinates of the point in the plane.
   * @return The canvas value at the specified point.
   */
  public int getPixel(Vector2D point) {
    ChaosGameUtils.verifyPointBetweenMinAndMax(point, minCoords, maxCoords);
    Vector2D transformedPoint = transformCoordsToIndices.transform(point);
    int x = (int) transformedPoint.getX0();
    int y = (int) transformedPoint.getX1();
    return canvas[y][x];
  }

  /**
   * Sets the canvas value at the specified point in the plane.
   *
   * @param point The coordinates of the point in the plane.
   */
  public void putPixel(Vector2D point) throws IllegalArgumentException{
    ChaosGameUtils.verifyPointBetweenMinAndMax(point, minCoords, maxCoords);
    Vector2D transformedPoint = transformCoordsToIndices.transform(point);
    int x = (int) transformedPoint.getX0();
    int y = (int) transformedPoint.getX1();
    canvas[x][y] += 1;
  }

  /**
   * Clears the entire canvas by resetting all values to 0.
   */
  public void clear() {
    Arrays.stream(canvas).forEach(row -> Arrays.fill(row, 0));
  }

  /**
   * Creates and returns an AffineTransform2D object representing the transformation
   * that transforms coordinates from the plane to the int[][] grid.
   *
   * @return AffineTransform2D object representing the 2D transformation
   */
  public AffineTransform2D setTransformCoordsMatrix() {
    double a01 = (height - 1) / (minCoords.getX1() - maxCoords.getX1());
    double a10 = (width - 1) / (maxCoords.getX0() - minCoords.getX0());

    Matrix2x2 transformMatrix = new Matrix2x2(0, a01, a10, 0);

    double x0 = ((height - 1) * maxCoords.getX1()) / (maxCoords.getX1() - minCoords.getX1());
    double x1 = ((width - 1) * minCoords.getX0()) / (minCoords.getX0() - maxCoords.getX0());

    Vector2D transformVector = new Vector2D(x0, x1);

    return new AffineTransform2D(transformMatrix, transformVector);
  }

  /**
   * Goes through every element in the canvas and prints it to the terminal to show
   * a fractal.
   */
  public void showCanvas() {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (canvas[i][j] == 1) {
          System.out.print("X");
        } else {
          System.out.print(" ");
        }
      }
      System.out.println();
    }
  }
}
