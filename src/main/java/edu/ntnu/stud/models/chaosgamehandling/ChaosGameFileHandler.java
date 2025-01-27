package edu.ntnu.stud.models.chaosgamehandling;

import edu.ntnu.stud.models.exceptions.EmptyFileException;
import edu.ntnu.stud.models.mathematics.Complex;
import edu.ntnu.stud.models.mathematics.Matrix2x2;
import edu.ntnu.stud.models.mathematics.Vector2D;
import edu.ntnu.stud.models.transform.AffineTransform2D;
import edu.ntnu.stud.models.transform.JuliaTransform;
import edu.ntnu.stud.models.transform.Transform2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
  public ChaosGameDescription readFromFile(String path)
      throws FileNotFoundException, EmptyFileException, NoSuchElementException {
    ChaosGameDescription newDescription = null;
    File file = new File(path);
    try (Scanner scanner = new Scanner(file)) {
      //Regex to tokenize file
      scanner.useDelimiter(",|\n|#.*");

      while (scanner.hasNext()) {
        String transformType = scanner.next().trim();

        Vector2D min = new Vector2D(parseNextDouble(scanner), parseNextDouble(scanner));
        Vector2D max = new Vector2D(parseNextDouble(scanner), parseNextDouble(scanner));

        // own method call for each different transform type
        if ("Affine2D".equals(transformType)) {
          newDescription = new ChaosGameDescription(min, max, parseAffineFile(scanner));
        } else if ("Julia".equals(transformType)) {
          newDescription = new ChaosGameDescription(min, max, parseJuliaFile(scanner));
        } else {
          throw new IllegalArgumentException("No valid transform type found. Found: "
              + transformType);
        }
      }
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("No file found in path: " + path);
    } catch (NoSuchElementException e) {
      throw new NoSuchElementException("File in incorrect format. File: " + path);
    }
    if (newDescription == null) {
      throw new EmptyFileException("Given file is empty. File: " + path);
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
      Matrix2x2 matrix = new Matrix2x2(parseNextDouble(scanner), parseNextDouble(scanner),
            parseNextDouble(scanner), parseNextDouble(scanner));
      Vector2D vector = new Vector2D(parseNextDouble(scanner), parseNextDouble(scanner));
      affineTransformList.add(new AffineTransform2D(matrix, vector));
    }
    return affineTransformList;
  }

  /**
   * Makes sure the next value parsed is a double value and not an empty/blank String
   * which can come from the file because of the delimiter.
   *
   * @param scanner the scanner that goes through the file
   * @return the next double number that can be parsed
   */
  public double parseNextDouble(Scanner scanner) {
    String nextToken = scanner.next();
    double nextDouble;

    if (nextToken.isBlank()) {
      nextDouble = parseNextDouble(scanner);
      //recursive call so that a blank/empty string is never parsed
    } else {
      nextDouble = Double.parseDouble(nextToken);
    }

    return nextDouble;
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
      Complex complexPoint = new Complex(parseNextDouble(scanner), parseNextDouble(scanner));
      juliaTransformList.add(new JuliaTransform(complexPoint, -1));
      juliaTransformList.add(new JuliaTransform(complexPoint, 1));
    }
    return juliaTransformList;
  }

  /**
   * Attempts to read an integer on the first line of a document using {@link Scanner}.
   *
   * @param path the path to the document where the integer is being read
   * @return the integer which is found and parsed
   * @throws FileNotFoundException If a file with the given path is not found
   * @throws NumberFormatException If no integer can be parsed from the first line of the doc
   */
  public int readIntOnFirstLine(String path) throws FileNotFoundException, NumberFormatException {
    int firstLineInt = 0;
    try {
      Scanner scanner = new Scanner(new File(path));
      firstLineInt = Integer.parseInt(scanner.nextLine());
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("Couldn't find file in path: " + path);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Couldn't parse steps amount from file, " + e.getMessage());
    }
    return firstLineInt;
  }

  /**
   * Writes a {@link ChaosGameDescription} to a file by calling the
   * {@link #writeStringToFile(String, String)} method and using the toString()
   * method of the ChaosGameDescription object.
   *
   * @param description The ChaosGameDescription object to be written to the file.
   * @param path The path to the file to be written.
   * @throws IOException If the file cannot be written to.
   */
  public void writeChaosGameToFile(ChaosGameDescription description, String path)
      throws IOException {
    writeStringToFile(path, description.toString());
  }

  /**
   * Uses a {@link java.io.BufferedWriter} to write a given string to a text file.
   *
   * @param path The path to write the file to
   * @param content the content which will be written to the file
   * @throws IOException if the BufferedWriter couldn't write to the file
   */
  public void writeStringToFile(String path, String content)
      throws IOException, NullPointerException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
      writer.write(content);
    } catch (IOException e) {
      throw new IOException("Could not write to file: " + path);
    }
  }
}