package edu.ntnu.stud.models.chaosgamehandling;

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
    @Test
    void testInvalidCanvasWidthInConstructor() {
      assertThrows(IllegalArgumentException.class, () -> new ChaosCanvas(-1, 1, point, point));
      assertThrows(IllegalArgumentException.class, () -> new ChaosCanvas(0, 1, point, point));
    }

    @Test
    void testInvalidCanvasHeightInConstructor() {
      assertThrows(IllegalArgumentException.class, () -> new ChaosCanvas(1, -1, point, point));
      assertThrows(IllegalArgumentException.class, () -> new ChaosCanvas(1, 0, point, point));
    }

    @Test
    void testInvalidMinAndMaxCoordsInConstructor() {
      assertThrows(IllegalArgumentException.class, () -> new ChaosCanvas(1, 1, new Vector2D(1, 1), new Vector2D(1,1)));
      assertThrows(IllegalArgumentException.class, () -> new ChaosCanvas(1, 1, new Vector2D(1, 1), new Vector2D(0,1)));
      assertThrows(IllegalArgumentException.class, () -> new ChaosCanvas(1, 1, new Vector2D(1, 1), new Vector2D(1,0)));
      assertThrows(IllegalArgumentException.class, () -> new ChaosCanvas(1, 1, new Vector2D(1, 1), new Vector2D(0, 0)));
    }

    @Test
    void testInvalidPointInGetPixel() {
      assertThrows(IllegalArgumentException.class, () -> canvas.putPixel(new Vector2D(-1, -1)));
      assertThrows(IllegalArgumentException.class, () -> canvas.putPixel(new Vector2D(0, -1)));
      assertThrows(IllegalArgumentException.class, () -> canvas.putPixel(new Vector2D(-1, -1)));
      assertThrows(IllegalArgumentException.class, () -> canvas.putPixel(new Vector2D(2, 0)));
      assertThrows(IllegalArgumentException.class, () -> canvas.putPixel(new Vector2D(0, 2)));
      assertThrows(IllegalArgumentException.class, () -> canvas.putPixel(new Vector2D(2, 2)));
      assertThrows(IllegalArgumentException.class, () -> canvas.putPixel(new Vector2D(1, -1)));
      assertThrows(IllegalArgumentException.class, () -> canvas.putPixel(new Vector2D(-1, 1)));
    }
  }
}
