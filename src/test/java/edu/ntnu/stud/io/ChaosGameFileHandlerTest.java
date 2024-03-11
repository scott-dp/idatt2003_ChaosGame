package edu.ntnu.stud.io;

import edu.ntnu.stud.config.ChaosGameDescription;
import edu.ntnu.stud.models.AffineTransform2D;
import edu.ntnu.stud.models.Matrix2x2;
import edu.ntnu.stud.models.Transform2D;
import edu.ntnu.stud.models.Vector2D;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChaosGameFileHandlerTest {
  ChaosGameFileHandler fileHandler;
  String path;

  @BeforeEach
  void setUp() {
    fileHandler = new ChaosGameFileHandler();
    path = "src/main/resources/Affine.txt";
  }

  @Nested
  class PositiveTests {
    @Test
    void testReadFromFile() throws FileNotFoundException {
      List<Transform2D> transform2DList = new ArrayList<>();
      AffineTransform2D transform1 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0, 0));
      AffineTransform2D transform2 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D( 0.25, 0.5));
      transform2DList.add(transform1);
      transform2DList.add(transform2);
      ChaosGameDescription description = new ChaosGameDescription(new Vector2D(0, 0), new Vector2D(1, 1), transform2DList);
      assertEquals(fileHandler.readFromFile(path), description);
    }
  }
}
