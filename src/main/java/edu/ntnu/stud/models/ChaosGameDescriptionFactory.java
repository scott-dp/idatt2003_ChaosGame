package edu.ntnu.stud.models;

import edu.ntnu.stud.models.chaosgamehandling.ChaosGameDescription;
import java.util.ArrayList;

/**
 * Factory class for creating different chaos game descriptions.
 * <p>
 *   This class provides static methods for creating
 *   different chaos game descriptions.
 *   The descriptions are created with the necessary transforms
 *   and min and max points.
 *   {@link ChaosGameDescription} instances are returned.
 * </p>
 *
 * @author Scott du Plessis, Stanislovas Mockus
 * @version 1.0
 */
public class ChaosGameDescriptionFactory {
  static ArrayList<Transform2D> transforms;

  /**
   * A static method that creates a Sierpinski chaos game description.
   * with predefined transforms and min and max points.
   *
   * @return a {@link ChaosGameDescription} instance
   */
  public static ChaosGameDescription createSierpinskiDescription() {
    transforms = new ArrayList<>();
    Transform2D sierpinski1 =
          new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0, 0));
    Transform2D sierpinski2 =
          new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0.5, 0));
    Transform2D sierpinski3 =
          new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0.25, 0.5));
    transforms.add(sierpinski1);
    transforms.add(sierpinski2);
    transforms.add(sierpinski3);
    Vector2D min = new Vector2D(0, 0);
    Vector2D max = new Vector2D(1, 1);
    return new ChaosGameDescription(min, max, transforms);
  }

  /**
   * A static method that creates a Barnsley chaos game description.
   * with predefined transforms and min and max points.
   *
   * @return a {@link ChaosGameDescription} instance
   */
  public static ChaosGameDescription createBarnsleyDescription() {
    transforms = new ArrayList<>();
    Transform2D barnsley1 =
          new AffineTransform2D(new Matrix2x2(0, 0, 0, 0.16), new Vector2D(0, 0));
    Transform2D barnsley2 =
          new AffineTransform2D(new Matrix2x2(0.85, 0.04, -0.04, 0.85), new Vector2D(0, 1.6));
    Transform2D barnsley3 =
          new AffineTransform2D(new Matrix2x2(0.2, -0.26, 0.23, 0.22), new Vector2D(0, 1.6));
    Transform2D barnsley4 =
          new AffineTransform2D(new Matrix2x2(-0.15, 0.28, 0.26, 0.24), new Vector2D(0, 0.44));
    transforms.add(barnsley1);
    transforms.add(barnsley2);
    transforms.add(barnsley3);
    transforms.add(barnsley4);
    Vector2D min = new Vector2D(-2.1820, 0);
    Vector2D max = new Vector2D(2.6558, 9.9983);
    return new ChaosGameDescription(min, max, transforms);
  }

  /**
   * A static method that creates a Julia set chaos game description.
   * with predefined transforms and min and max points.
   * The Julia set is created with a positive sign.
   * The complex number used in the Julia set formula is passed as a parameter.
   *
   * @param c the complex number to be used in the Julia set formula
   * @return a {@link ChaosGameDescription} instance
   */
  public static ChaosGameDescription createPositiveJuliaSet(Complex c) {
    transforms = new ArrayList<>();
    Vector2D min = new Vector2D(-1.5, -1.5);
    Vector2D max = new Vector2D(1.5, 1.5);
    Transform2D julia = new JuliaTransform(c, 1);
    transforms.add(julia);
    return new ChaosGameDescription(min, max, transforms);
  }

  /**
   * A static method that creates a Julia set chaos game description.
   * with predefined transforms and min and max points.
   * The Julia set is created with a negative sign.
   * The complex number used in the Julia set formula is passed as a parameter.
   *
   * @param c the complex number to be used in the Julia set formula
   * @return a {@link ChaosGameDescription} instance
   */
  public static ChaosGameDescription createNegativeJuliaSet(Complex c) {
    transforms = new ArrayList<>();
    Vector2D min = new Vector2D(-1.5, -1.5);
    Vector2D max = new Vector2D(1.5, 1.5);
    Transform2D julia = new JuliaTransform(c, -1);
    transforms.add(julia);
    return new ChaosGameDescription(min, max, transforms);
  }
}