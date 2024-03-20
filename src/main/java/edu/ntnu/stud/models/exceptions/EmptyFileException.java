package edu.ntnu.stud.models.exceptions;

/**
 * Thrown when the {@link edu.ntnu.stud.models.chaosgamehandling.ChaosGameFileHandler}
 * tries to read from an empty file.
 */
public class EmptyFileException extends RuntimeException {
  public EmptyFileException() {
    super("The file is empty and cannot be processed.");
  }

  public EmptyFileException(String message) {
    super(message);
  }
}
