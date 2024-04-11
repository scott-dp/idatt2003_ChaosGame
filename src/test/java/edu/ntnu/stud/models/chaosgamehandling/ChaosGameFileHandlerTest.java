package edu.ntnu.stud.models.chaosgamehandling;

import edu.ntnu.stud.models.exceptions.EmptyFileException;
import edu.ntnu.stud.models.*;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ChaosGameFileHandlerTest {
  ChaosGameFileHandler fileHandler;
  String pathAffine;
  String pathJulia;

  @BeforeEach
  void setUp() {
    fileHandler = new ChaosGameFileHandler();
    pathAffine = "src/test/resources/testResources/Affine.txt";
    pathJulia = "src/test/resources/testResources/Julia.txt";
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
      String newPath = "src/test/resources/testResources/writeAffineTest.txt";
      ChaosGameDescription description = fileHandler.readFromFile(pathAffine);
      fileHandler.writeToFile(description, newPath);
      ChaosGameDescription newDescription = fileHandler.readFromFile(newPath);
      assertEquals(description.toString(), newDescription.toString());
    }

    @Test
    void testWriteJuliaTransformToFile() throws IOException {
      String newPath = "src/test/resources/testResources/writeJuliaTest.txt";
      ChaosGameDescription description = fileHandler.readFromFile(pathJulia);
      fileHandler.writeToFile(description, newPath);
      ChaosGameDescription newDescription = fileHandler.readFromFile(newPath);
      assertEquals(description.toString(), newDescription.toString());
    }
  }

  @Nested
  class NegativeTests {
    @Test
    void testNonValidPathToReadFromFile() {
      assertThrows(FileNotFoundException.class, () -> fileHandler.readFromFile("src/test/main"));
    }

    @Test
    void testReadEmptyFile() throws FileNotFoundException {
      assertThrows(EmptyFileException.class, () -> fileHandler.readFromFile("src/test/resources/testResources/empty.txt"));
    }

    @Test
    void testReadIncorrectFormatFile() {
      assertThrows(NoSuchElementException.class, () -> fileHandler.readFromFile("src/test/resources/testResources/incorrectFormat.txt"));
    }

    @Test
    void testWriteToNonExistentDirectory() throws IOException {
      ChaosGameDescription description = fileHandler.readFromFile(pathAffine);
      assertThrows(IOException.class, () -> fileHandler.writeToFile(description, "src/test/bla/bla"));
    }

    @Test
    void testWriteNullObjectToFile() {
      assertThrows(NullPointerException.class, () -> fileHandler.writeToFile(null, "src/test/resources/testResources/empty.txt"));
    }
  }
}
