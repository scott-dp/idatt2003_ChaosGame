package edu.ntnu.stud.io;

import edu.ntnu.stud.config.ChaosGameDescription;
import edu.ntnu.stud.models.*;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChaosGameFileHandlerTest {
  ChaosGameFileHandler fileHandler;
  String pathAffine;
  String pathJulia;

  @BeforeEach
  void setUp() {
    fileHandler = new ChaosGameFileHandler();
    pathAffine = "src/main/resources/Affine.txt";
    pathJulia = "src/main/resources/Julia.txt";
  }

  @Nested
  class PositiveTests {
    @Test
    void testReadAffineTransformFromFile() throws FileNotFoundException {
      List<Transform2D> transform2DList = new ArrayList<>();
      AffineTransform2D transform1 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0, 0));
      AffineTransform2D transform2 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D( 0.25, 0.5));
      transform2DList.add(transform1);
      transform2DList.add(transform2);
      ChaosGameDescription description = new ChaosGameDescription(new Vector2D(0, 0), new Vector2D(1, 1), transform2DList);
      assertEquals(fileHandler.readFromFile(pathAffine).toString(), description.toString());
    }

    @Test
    void testReadJuliaTransformFromFile() throws FileNotFoundException {
      List<Transform2D> transform2DList = new ArrayList<>();
      JuliaTransform transform1 = new JuliaTransform(new Complex(-.74543, .11301), 1);
      JuliaTransform transform2 = new JuliaTransform(new Complex(-.74543, .11301), -1);
      transform2DList.add(transform1);
      transform2DList.add(transform2);
      ChaosGameDescription description = new ChaosGameDescription(new Vector2D(-1.6, -1), new Vector2D(1.6, 1), transform2DList);
      assertEquals(fileHandler.readFromFile(pathJulia).toString(), description.toString());
    }

    @Test
    void testWriteAffineTransformToFile() throws IOException {
      String newPath = "src/main/resources/writeAffineTest.txt";
      ChaosGameDescription description = fileHandler.readFromFile(pathAffine);
      fileHandler.writeToFile(description, newPath);
      ChaosGameDescription newDescription = fileHandler.readFromFile(newPath);
      assertEquals(description.toString(), newDescription.toString());
    }

    @Test
    void testWriteJuliaTransformToFile() throws IOException {
      String newPath = "src/main/resources/writeJuliaTest.txt";
      ChaosGameDescription description = fileHandler.readFromFile(pathJulia);
      fileHandler.writeToFile(description, newPath);
      ChaosGameDescription newDescription = fileHandler.readFromFile(newPath);
      assertEquals(description.toString(), newDescription.toString());
    }
  }
}
