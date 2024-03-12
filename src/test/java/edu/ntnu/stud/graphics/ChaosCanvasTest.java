package edu.ntnu.stud.graphics;

import edu.ntnu.stud.models.Vector2D;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class ChaosCanvasTest {
  ChaosCanvas canvas;
  Vector2D point;

  @BeforeEach
  void setUp() {
    canvas = new ChaosCanvas(100, 100, new Vector2D(0,0), new Vector2D(1, 1));
    point = new Vector2D(0.5, 0.5);
  }

  @Nested
  class PositiveTests {
    @Test
    void testPutAndGetPixel() {
      canvas.putPixel(point);
      assertEquals(canvas.getPixel(point), 1);
      assertEquals(canvas.getPixel(new Vector2D(0.4, 0.6)), 0);
    }

    @Test
    void testClearCanvas() {
      canvas.putPixel(point);
      canvas.clear();
      assertEquals(canvas.getPixel(point), 0);
    }

  }
  @Nested
  class NegativeTests {
    //TODO make negative tests
  }
}
