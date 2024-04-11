package edu.ntnu.stud.models;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class Vector2DTest {
  //TODO test toString
  private Vector2D vector2D1;
  private Vector2D vector2D2;
  private Vector2D vector2D3;
  private Vector2D complex;



  @BeforeEach
  public void setUp() {
    vector2D1 = new Vector2D(10,4);
    vector2D2 = new Vector2D(1,8);
    vector2D3 = new Vector2D(1,8);
    complex = new Complex(1,8);
  }

  @Nested
  class PositiveTests {
    @Test
    void testAdd() {
      Vector2D addedVector = vector2D1.add(vector2D2);
      assertEquals(addedVector, new Vector2D(11,12));
    }

    @Test
    void testSubtract() {
      Vector2D subtractedVector = vector2D1.subtract(vector2D2);

      assertEquals(subtractedVector, new Vector2D(9, -4));
    }

    @Test
    void testEquals() {
      assertNotEquals(null, vector2D1);
      assertNotEquals(vector2D2, complex);
      assertEquals(vector2D2, vector2D3);
      assertEquals(vector2D1, vector2D1);
    }
  }
}
