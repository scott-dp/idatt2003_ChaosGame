package edu.ntnu.stud.models.chaosgamehandling;

import edu.ntnu.stud.models.mathematics.Complex;
import edu.ntnu.stud.models.mathematics.Matrix2x2;
import edu.ntnu.stud.models.mathematics.Vector2D;
import edu.ntnu.stud.models.transform.AffineTransform2D;
import edu.ntnu.stud.models.transform.JuliaTransform;
import edu.ntnu.stud.models.transform.Transform2D;
import java.util.ArrayList;
import java.util.List;

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
  /**
   * A static method that creates a Sierpinski chaos game description.
   * with predefined transforms and min and max points.
   *
   * @return a {@link ChaosGameDescription} instance
   */
  public static ChaosGameDescription createSierpinskiDescription() {
    List<Transform2D> transforms = new ArrayList<>();
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
   * Creates a {@link ChaosGameDescription} that makes a Levy Curve fractal when run.
   *
   * @return a {@link ChaosGameDescription} that represents the Levy Curve
   */
  public static ChaosGameDescription createLevyCurveDescription() {
    List<Transform2D> transforms = new ArrayList<>();

    Transform2D levy1 = new AffineTransform2D(new Matrix2x2(0.5, 0.5, -0.5, 0.5),
        new Vector2D(0, 0));
    Transform2D levy2 = new AffineTransform2D(new Matrix2x2(0.5, -0.5, 0.5, 0.5),
        new Vector2D(-0.5, -0.5));
    transforms.add(levy1);
    transforms.add(levy2);
    Vector2D min = new Vector2D(-1.53, -1.53);
    Vector2D max = new Vector2D(0.53, 0.53);
    return new ChaosGameDescription(min, max, transforms);

  }

  /**
   * A static method that creates a Barnsley chaos game description.
   * with predefined transforms and min and max points.
   *
   * @return a {@link ChaosGameDescription} instance
   */
  public static ChaosGameDescription createBarnsleyDescription() {
    List<Transform2D> transforms = new ArrayList<>();
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
   * Makes a julia set {@link ChaosGameDescription} with a specific constant.
   *
   * @return the {@link ChaosGameDescription} instance with a specific constant for the Julia set
   */
  public static ChaosGameDescription getJuliaSetDescription1() {
    return makeJuliaSet(new Complex(-.74543, .11301));
  }

  /**
   * Makes a julia set {@link ChaosGameDescription} with a specific constant.
   *
   * @return the {@link ChaosGameDescription} instance with a specific constant for the Julia set
   */
  public static ChaosGameDescription getJuliaSetDescription2() {
    return makeJuliaSet(new Complex(-1.234, .11301));
  }

  /**
   * Makes a julia set {@link ChaosGameDescription} with a specific constant.
   *
   * @return the {@link ChaosGameDescription} instance with a specific constant for the Julia set
   */
  public static ChaosGameDescription getJuliaSetDescription3() {
    return makeJuliaSet(new Complex(-.1234, .81301));
  }

  /**
   * Makes a julia set {@link ChaosGameDescription} with a specific constant.
   *
   * @return the {@link ChaosGameDescription} instance with a specific constant for the Julia set
   */
  public static ChaosGameDescription getJuliaSetDescription4() {
    return makeJuliaSet(new Complex(-.2234, 1.11301));
  }

  /**
   * A static method that creates a Julia set chaos game description with predefined min and
   * max coords. The complex number used in the Julia set formula is passed as a parameter.
   *
   * @param c the complex number to be used in the Julia set formula
   * @return a {@link ChaosGameDescription} instance
   */
  public static ChaosGameDescription makeJuliaSet(Complex c) {
    List<Transform2D> transforms = new ArrayList<>();
    Vector2D min = new Vector2D(-1.6, -1);
    Vector2D max = new Vector2D(1.6, 1);

    Transform2D julia1 = new JuliaTransform(c, 1);
    Transform2D julia2 = new JuliaTransform(c, -1);

    transforms.add(julia1);
    transforms.add(julia2);
    return new ChaosGameDescription(min, max, transforms);
  }
}
