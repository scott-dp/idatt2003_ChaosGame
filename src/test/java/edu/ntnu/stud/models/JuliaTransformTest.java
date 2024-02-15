package edu.ntnu.stud.models;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JuliaTransformTest {
  private JuliaTransform juliaTransform;
  private Complex complex;
  private Vector2D vector2D;

  @BeforeEach
  public void setUp() {
    complex = new Complex(2, 1);
    juliaTransform = new JuliaTransform(complex, -1);
    vector2D = new Vector2D(1, 3);
  }

  @Nested
  class positiveTests {
    @Test
    void testTransform() {
      Vector2D transformedPoint = juliaTransform.transform(vector2D);

      assertEquals(new Complex(-0.786, -1.272), transformedPoint);

    }
  }
}
