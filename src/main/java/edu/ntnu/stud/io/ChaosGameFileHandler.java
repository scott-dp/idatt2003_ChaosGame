package edu.ntnu.stud.io;

import edu.ntnu.stud.config.ChaosGameDescription;

import java.io.*;
import java.util.Scanner;

public class ChaosGameFileHandler {

  public ChaosGameDescription readFromFile(String path) throws FileNotFoundException {
    //TODO validate path/file
    File file = new File(path);
    Scanner scanner = new Scanner(file);
    scanner.useDelimiter("#");
  }

  public void writeToFile(ChaosGameDescription description, String path) {
    // Write to file using BufferedWriter
  }
}
