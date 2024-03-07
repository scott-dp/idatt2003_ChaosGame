package edu.ntnu.stud.io;

import edu.ntnu.stud.config.ChaosGameDescription;
import edu.ntnu.stud.models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChaosGameFileHandler {

  public ChaosGameDescription readFromFile(String path) throws FileNotFoundException {
    //Any method that uses result from this method can throw NullPointerException if file is empty
    ChaosGameDescription newDescription = null;
    File file = new File(path);
    try (Scanner scanner = new Scanner(file)) {
      //Regex to tokenize file
      scanner.useDelimiter(",|\\s+|#.*\\n");

      while (scanner.hasNext()) {
        String transformType = scanner.next();

        Vector2D min = new Vector2D(scanner.nextDouble(), scanner.nextDouble());
        Vector2D max = new Vector2D(scanner.nextDouble(), scanner.nextDouble());

        // own method call for each different transform type
        if ("Affine2D".equals(transformType)) {
          newDescription = new ChaosGameDescription(min, max, parseAffineFile(scanner));
        } else if ("Julia".equals(transformType)) {
          newDescription = new ChaosGameDescription(min, max, parseJuliaFile(scanner));
        } else {
          throw new IllegalArgumentException("No transform type found");
        }
      }
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("No file found in path: " + path);
    }
    return newDescription;
  }

  public void writeToFile(ChaosGameDescription description, String path) {
    // Write to file using BufferedWriter
  }

  public List<Transform2D> parseAffineFile(Scanner scanner) {
    List<Transform2D> affineTransformList = new ArrayList<>();
    while (scanner.hasNext()) {
      Matrix2x2 matrix = new Matrix2x2(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble());
      Vector2D vector = new Vector2D(scanner.nextDouble(), scanner.nextDouble());
      affineTransformList.add(new AffineTransform2D(matrix, vector));
    }
    return affineTransformList;
  }

  public List<Transform2D> parseJuliaFile(Scanner scanner) {
    List<Transform2D> juliaTransformList = new ArrayList<>();
    while (scanner.hasNext()) {
      Vector2D complexPoint = new Complex(scanner.nextDouble(), scanner.nextDouble());
      juliaTransformList.add(new JuliaTransform(complexPoint, ));
    }
    return juliaTransformList;
  }
}
