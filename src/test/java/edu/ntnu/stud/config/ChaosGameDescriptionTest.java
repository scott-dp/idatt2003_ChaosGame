package edu.ntnu.stud.config;

import edu.ntnu.stud.models.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

public class ChaosGameDescriptionTest {
  static ChaosGameDescription description1;
  static ChaosGameDescription description2;
  static Vector2D min;
  static Vector2D max;
  static Transform2D affineTransform1;
  static Transform2D affineTransform2;
  static Transform2D juliaTransform1;
  static Transform2D juliaTransform2;
  @BeforeAll
  static void setUpAffine() {
    affineTransform1 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0, 0));
    affineTransform2 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0.5, 0));
    min = (new Vector2D(1, 1));
    max = new Vector2D(2, 2);

    List<Transform2D> transform2DList = new ArrayList<>();
    transform2DList.add(affineTransform1);
    transform2DList.add(affineTransform2);

    description1 = new ChaosGameDescription(min, max, transform2DList);
  }

  @BeforeAll
  static void setUpJulia() {
    juliaTransform1 = new JuliaTransform(new Complex(1, 3), -1);
    juliaTransform2 = new JuliaTransform(new Complex(1, 3), 1);

    List<Transform2D> transform2DList = new ArrayList<>();
    transform2DList.add(juliaTransform1);
    transform2DList.add(juliaTransform2);

    description2 = new ChaosGameDescription(min, max, transform2DList);
  }

  @Nested
  class PositiveTests {
    @Test
    void testToStringMethodForAffineTransform() {
      StringBuilder sb = new StringBuilder();
      sb.append("Affine2D\n").append(min.toString()).append("\n").append(max.toString()).append("\n");
      sb.append(affineTransform1.toString()).append("\n");
      sb.append(affineTransform2.toString()).append("\n");
      assertEquals(description1.toString(), sb.toString());
    }

    @Test
    void testToStringMethodForJuliaTransform() {
      StringBuilder sb = new StringBuilder();
      sb.append("Julia\n").append(min.toString()).append("\n").append(max.toString()).append("\n");
      sb.append(juliaTransform1.toString());
      assertEquals(description2.toString(), sb.toString());
    }
  }
}
