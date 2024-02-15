package edu.ntnu.stud.models;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Matrix2x2Test {
  private Matrix2x2 matrix;
  private Vector2D vector;

  @BeforeEach
  public void setUp() {
    matrix = new Matrix2x2(1.3, 0.4, 5.9, 4);
    vector = new Vector2D(11, 4.7);
  }

  @Nested
  class positiveTests{

    @Test
    void testMatrixMultiply(){
      assertEquals(new Vector2D(1.3*11+0.4*4.7,5.9*11+4*4.7), matrix.multiply(vector));
    }
  }
}

