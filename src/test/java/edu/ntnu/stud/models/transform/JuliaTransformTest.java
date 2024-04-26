package edu.ntnu.stud.models.transform;

import edu.ntnu.stud.models.mathematics.Complex;
import edu.ntnu.stud.models.mathematics.Vector2D;
import edu.ntnu.stud.models.transform.JuliaTransform;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class JuliaTransformTest {
  //TODO test toString
  private JuliaTransform juliaTransform1;
  private JuliaTransform juliaTransform2;
  private JuliaTransform juliaTransform3;
  private Complex complex1;
  private Complex complex2;
  private Vector2D vector2D;

  @BeforeEach
  public void setUp() {
    complex1 = new Complex(2, 1);
    complex2 = new Complex(-1, 3);
    juliaTransform1 = new JuliaTransform(complex1, -1);
    juliaTransform2 = new JuliaTransform(complex1, -1);
    juliaTransform3 = new JuliaTransform(complex2, 1);
    vector2D = new Vector2D(1, 3);
  }

  @Nested
  class PositiveTests {
    @Test
    void testTransform() {
      Vector2D transformedPoint = juliaTransform1.transform(vector2D);

      assertEquals(new Complex(-0.786, -1.272), transformedPoint);
    }

    @Test
    void testEquals() {
      assertEquals(juliaTransform1, juliaTransform1);
      assertNotEquals(null, juliaTransform1);
      assertNotEquals(juliaTransform1, complex1);
      assertEquals(juliaTransform1, juliaTransform2);
      assertNotEquals(juliaTransform1, juliaTransform3);
    }
  }

  @Nested
  class NegativeTests {
    @Test
    void testValidationOfSignField() {
      assertThrows(IllegalArgumentException.class, () -> new JuliaTransform(new Complex(1, 1), -2));
      assertThrows(IllegalArgumentException.class, () -> new JuliaTransform(new Complex(1, 1), 3));
      assertThrows(IllegalArgumentException.class, () -> new JuliaTransform(new Complex(1, 1), 0));
    }
  }
}
