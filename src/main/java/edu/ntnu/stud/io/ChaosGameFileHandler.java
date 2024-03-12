package edu.ntnu.stud.io;

import edu.ntnu.stud.config.ChaosGameDescription;
import edu.ntnu.stud.models.AffineTransform2D;
import edu.ntnu.stud.models.Complex;
import edu.ntnu.stud.models.JuliaTransform;
import edu.ntnu.stud.models.Matrix2x2;
import edu.ntnu.stud.models.Transform2D;
import edu.ntnu.stud.models.Vector2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The ChaosGameFileHandler class provides methods,
 * for reading and writing chaos game descriptions to and from files.
 *<p>
 *The class utilizes a Scanner object to read from files,
 *  and a BufferedWriter object to write to files.
 *</p>
 *
 * @version 1.0
 * @author Scott du Plessis, Stanislovas Mockus
 * @see ChaosGameDescription
 */
public class ChaosGameFileHandler {

  /**
   * Reads a text file and constructs a ChaosGameDescription object from the file contents.
   * <p>
   * The file should contain a description of the chaos game,
   * including the coordinate range and the set of transformations.
   * </p>
   *
   * @param path The path to the file to be read.
   * @return A ChaosGameDescription object constructed from the file contents.
   * @throws FileNotFoundException If the file does not exist or is not accessible.
   */
  public ChaosGameDescription readFromFile(String path) throws FileNotFoundException {
    //Any method that uses result from this method can throw NullPointerException if file is empty
    ChaosGameDescription newDescription = null;
    File file = new File(path);
    try (Scanner scanner = new Scanner(file)) {
      scanner.useDelimiter(",|#.*");

      while (scanner.hasNext()) {
        String transformType = scanner.next().trim();

        //parseDouble() kan parse feks "\n0.0" til double 0.0, men nextDouble() kan ikke det (nevn i rapporten)
        Vector2D min = new Vector2D(Double.parseDouble(scanner.next()), Double.parseDouble(scanner.next()));
        Vector2D max = new Vector2D(Double.parseDouble(scanner.next()), Double.parseDouble(scanner.next()));

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

  /**
   * If the file contains info about an affine transformation,
   * this method will parse the file and return a list of AffineTransform2D objects.
   *
   * @param scanner The scanner object used to read the file,
   *               which is initially created in {@link #readFromFile(String)}
   * @return A list of AffineTransform2D objects parsed from the file.
   */
  public List<Transform2D> parseAffineFile(Scanner scanner) {
    List<Transform2D> affineTransformList = new ArrayList<>();
    while (scanner.hasNext()) {
      Matrix2x2 matrix = new Matrix2x2(Double.parseDouble(scanner.next()), Double.parseDouble(scanner.next()),
          Double.parseDouble(scanner.next()), Double.parseDouble(scanner.next()));
      Vector2D vector = new Vector2D(Double.parseDouble(scanner.next()), Double.parseDouble(scanner.next()));
      affineTransformList.add(new AffineTransform2D(matrix, vector));
    }
    return affineTransformList;
  }

  /**
   * If the file contains info about a Julia transformation,
   * this method will parse the file and return a list of JuliaTransform objects.
   *
   * @param scanner The scanner object used to read the file,
   *               which is initially created in {@link #readFromFile(String)}
   * @return A list of JuliaTransform objects parsed from the file.
   */
  public List<Transform2D> parseJuliaFile(Scanner scanner) {
    List<Transform2D> juliaTransformList = new ArrayList<>();
    while (scanner.hasNext()) {
      Complex complexPoint = new Complex(Double.parseDouble(scanner.next()), Double.parseDouble(scanner.next()));
      juliaTransformList.add(new JuliaTransform(complexPoint, -1));
      juliaTransformList.add(new JuliaTransform(complexPoint, 1));
    }
    return juliaTransformList;
  }

  /**
   * Uses a BufferedWriter object to write a ChaosGameDescription object to a text file.
   *
   * @param description The ChaosGameDescription object to be written to the file.
   * @param path The path to the file to be written.
   * @throws IOException If the file cannot be written to.
   */
  public void writeToFile(ChaosGameDescription description, String path) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
      writer.write(description.toString());
    } catch (IOException e) {
      throw new IOException("Could not write to file: " + path);
    } catch (NullPointerException e) {
      throw new NullPointerException("No description found");
    }
  }
}
